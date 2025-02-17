import java.time.Instant;
import java.time.Duration;

import ratelimiter.RateLimiter;
import ratelimiter.algorithms.RateLimitingStrategy;
import ratelimiter.settings.Settings;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Service {
    private RateLimiter rateLimiter;
    private Instant testStartTime;

    public Service() {
        this.rateLimiter = new RateLimiter();
    }

    public Service(RateLimitingStrategy algorithm) {
        this.rateLimiter = new RateLimiter(algorithm);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Service service = new Service();
        long startTime = System.nanoTime();
//        service.testRateLimitingAtLoad();
        service.testRateLimitingAtWindowEdge();
        long endTime = System.nanoTime();
        long durationNs = endTime - startTime;
        double durationMs = durationNs / 1_000_000.0;

        System.out.println("Execution Time: " + durationMs + " ms");
        System.exit(0);
    }

    private void testRateLimitingAtWindowEdge() throws InterruptedException, ExecutionException {
        testStartTime = Instant.now();
        final Settings settings = rateLimiter.getRateLimitSettings();
        System.out.println(settings);

        final Instant startTime = Instant.now();
        final long windowLength = settings.getTicks();
        final TimeUnit timeUnit = settings.getTimeUnit();

        final long halfWindowLength = timeUnit.toMillis(windowLength) / 2;
        final long startOfFirstWindow = startTime.toEpochMilli();
        final long endOfFirstHalfOfFirstWindow = startOfFirstWindow + halfWindowLength;
        final long startOfSecondWindow = endOfFirstHalfOfFirstWindow + halfWindowLength;
        final long endOfFirstHalfOfSecondWindow = startOfSecondWindow + halfWindowLength;

        System.out.printf("Window details:%n" +
                        "Start time: %s%n" +
                        "First window: [%s, %s, %s]%n" +
                        "Second half window: [%s, %s]%n",
                formatTime(startOfFirstWindow),
                formatTime(startOfFirstWindow),
                formatTime(endOfFirstHalfOfFirstWindow),
                formatTime(startOfSecondWindow),
                formatTime(startOfSecondWindow),
                formatTime(endOfFirstHalfOfSecondWindow));

        String userId = "user1";
        AtomicInteger successCount = new AtomicInteger();

        // Sleep until first half window ends
        Thread.sleep(Math.max(0, endOfFirstHalfOfFirstWindow - Instant.now().toEpochMilli()));

        final long sleepInterval = halfWindowLength / (settings.getRequestLimit() + 1);
        for (long currentTime = endOfFirstHalfOfFirstWindow; currentTime <= endOfFirstHalfOfSecondWindow; currentTime += sleepInterval) {
            if (rateLimiter.canAccess(userId)) {
                System.out.printf("[%s]: Access granted. (%d)%n", formatTime(currentTime), successCount.incrementAndGet());
            } else {
                System.out.printf("[%s]: Request blocked.%n", formatTime(currentTime));
            }
            Thread.sleep(sleepInterval);
        }
    }

    private void testRateLimitingAtLoad() throws InterruptedException {
        testStartTime = Instant.now();

        final Settings settings = rateLimiter.getRateLimitSettings();
        System.out.println(settings);

        final int maxAllowedThreads = 8;
        final int numberOfUsers = 10000;
        final long requestsPerUser = 101;
        ExecutorService executor = Executors.newFixedThreadPool(maxAllowedThreads);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failureCount = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        ConcurrentHashMap<String, AtomicInteger> perUserSuccess = new ConcurrentHashMap<>();

        for (int i = 0; i < numberOfUsers; i++) {
            String userId = "user" + i;
            perUserSuccess.put(userId, new AtomicInteger(0));
            executor.submit(() -> {
                try {
                    for (int j = 0; j < requestsPerUser; j++) {
                        try {
                            if (rateLimiter.canAccess(userId)) {
                                successCount.incrementAndGet();
                                perUserSuccess.get(userId).incrementAndGet();
                            } else {
                                failureCount.incrementAndGet();
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            System.err.println("Error processing request: " + e.getMessage());
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        Duration testDuration = Duration.between(testStartTime, Instant.now());

        System.out.printf("Load test completed in %d seconds%n", testDuration.getSeconds());
        System.out.printf("Total requests: %d%n", numberOfUsers * requestsPerUser);
        System.out.printf("Successful requests: %d%n", successCount.get());
        System.out.printf("Failed requests: %d%n", failureCount.get());
        System.out.printf("Average success rate: %.2f%%%n",
                (successCount.get() * 100.0) / (numberOfUsers * requestsPerUser));

        perUserSuccess.forEach((userId, count) ->
                assertTrue(count.get() <= settings.getRequestLimit(), String.format("User %s exceeded limit", userId)));
    }

    private String formatTime(long epochMillis) {
        Duration duration = Duration.between(testStartTime, Instant.ofEpochMilli(epochMillis));
        long totalMillis = duration.toMillis();

        long seconds = (totalMillis / 1000) % 60;
        long millis = totalMillis % 1000;

        return String.format("%02d.%03d", seconds, millis);
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
