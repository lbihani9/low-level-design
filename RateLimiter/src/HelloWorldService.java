import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ratelimiter.RateLimiter;
import ratelimiter.algorithms.RateLimitingStrategy;

public class HelloWorldService {
    RateLimiter rateLimiter;
    private final int MAX_ALLOWED_TEST_THREADS = 8;

    public HelloWorldService() {
        this.rateLimiter = new RateLimiter();
    }

    public HelloWorldService(RateLimitingStrategy algorithm) {
        this.rateLimiter = new RateLimiter(algorithm);
    }

    public void execute(String userId) {
        if (rateLimiter.canAccess(userId)) {
            System.out.println("Hello World! from user: " + userId);
        } else {
            System.out.println("429: Rate limit reached for user: " + userId);
        }
    }

    public static void main(String[] args) {
        HelloWorldService service = new HelloWorldService();
        service.testRateLimiting();
    }

    public void testRateLimiting() {
        int numberOfUsers = 1000;
        int requestsPerUser = 101;
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(MAX_ALLOWED_TEST_THREADS, numberOfUsers));

        for (int i = 0; i < numberOfUsers; i++) {
            String userId = "user" + i;
            executor.submit(() -> {
                for (int j = 0; j < requestsPerUser; j++) {
                    execute(userId);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
