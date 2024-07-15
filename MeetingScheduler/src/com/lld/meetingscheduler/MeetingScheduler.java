package com.lld.meetingscheduler;

import com.lld.user.User;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The core classes of Meeting scheduler system have been kept in `meetingscheduler` package because I wanted the `User`
 *  class to interact with the scheduler system using the object of the `MeetingScheduler` only.
 */
public class MeetingScheduler {
    private String id;
    private TreeMap<Integer, List<MeetingRoom>> rooms;

    private static MeetingScheduler meetingScheduler;

    private MeetingScheduler() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.rooms = new TreeMap<>();
    }

    public void createRoom(String roomName, int capacity) {
        MeetingRoom room = new MeetingRoom(roomName, capacity);
        if (!rooms.containsKey(capacity)) {
            rooms.put(capacity, new ArrayList<>());
        }
        rooms.get(capacity).add(room);
    }

    public static synchronized MeetingScheduler getInstance() {
        if (meetingScheduler == null) {
            meetingScheduler = new MeetingScheduler();
        }
        return meetingScheduler;
    }

    public Meeting scheduleMeeting(int participantsCount, Interval interval, String title, User organizer) {
        if (rooms.ceilingKey(participantsCount) == null) {
            return null;
        }

        Meeting meeting = new Meeting(title, organizer, interval, participantsCount);
        Map<Integer, List<MeetingRoom>> subRooms = rooms.tailMap(participantsCount);

        // One by one looks for the first room where the meeting can be scheduled.
        for (Integer key : subRooms.keySet()) {
            List<MeetingRoom> roomList = subRooms.get(key);

            if (roomList != null && !roomList.isEmpty()) {
                ExecutorService executor = Executors.newFixedThreadPool(roomList.size());
                List<Callable<Boolean>> tasks = new ArrayList<>();

                for (MeetingRoom room : roomList) {
                    tasks.add(() -> room.addToCalendarIfAvailable(meeting, interval));
                }

                try {
                    List<Future<Boolean>> results = executor.invokeAll(tasks);
                    for (Future<Boolean> result : results) {
                        if (result.get()) {
                            executor.shutdownNow(); // Cancel remaining tasks
                            return meeting;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    executor.shutdown();
                }
            }
        }
        return null;
    }

    public void cancelMeeting(Meeting meeting) {
        MeetingRoom room = meeting.getAssignedRoom();
        if (room != null) {
            this.releaseRoom(room, meeting.getInterval());
        }
    }

    public void addParticipant(Meeting meeting, User participant) {
        meeting.addParticipant(participant);
    }

    public void removeParticipant(Meeting meeting, User participant) {
        meeting.removeParticipant(participant);
    }

    private void releaseRoom(MeetingRoom room, Interval interval) {
        room.removeInterval(interval);
    }
}
