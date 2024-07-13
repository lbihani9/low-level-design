## Requirements
1. There should be N number of meeting rooms.

2.  Each meeting room should have a specific capacity to accommodate the desired number of people.

3. If not reserved already, each meeting room should have the ability to be booked, along with setting an interval, a start time, and an end time for the meeting.

4. A notification should be sent to all the people invited to the meeting.

5. Users will receive an invitation regardless of whether they are available at the interval or not. Users can respond to the invitation by either accepting or rejecting the invite.

6. Each user should have access to a calendar that is used to track the date and time, as well as to schedule or cancel meetings.

## Assumptions
1. Meeting time cannot be updated. If timing needs to be updated, user has to cancel and book again.
2. Every room has one calendar associated with it and schedules of a room is maintained in its respective calendar.
3. If size of the meeting room specified by the organizer is not available, system automatically tries to schedule next bigger room and ultimately gives up if no room is available.
4. A participant can be removed from the meeting. In this case, removed participant is notified.
5. There's no limit on meeting duration. However, a meeting can only be started and ended on the same day.

## Flow
1. User adds number of participants, meeting agenda and meeting interval (startsAt and endsAt).
2. System searches for available room.
   1. If no room is available, scheduling request is canceled with appropriate message.
   2. If room is available, it's booked.
3. As soon as the room is booked, the user is asked to add participant emails for invitation.
4. After adding participant emails, system sends invites to all of them.
5. The user can also choose to cancel the meeting, in which case cancellation notification may or may not be sent depending on whether the meeting was canceled before or after adding participants.
6. When participants accept or reject invite, it doesn't trigger new notification.

## Key objects
1. MeetingRoom
```
{
   id,
   size,
   calendar
}   
```
2. RoomCalendar
```
{
   id,
   schedules: map[datetime]list
}
```
3. Notification
4. Interval
```
{
    start,
    end
}
```
5. Meeting
```
{
   id,
   users[],
   title,
   organizer,
   interval,
   roomId
}
```
6. User
```
{
    id,
    email,
    meetingInvites: map[meetingId]InvitationState
}
```
7. MeetingScheduler
8. MeetingStates {
      SCHEDULED,
      CANCELLED
   }
9. InvitationState {
        PENDING,
        ACCEPTED,
        REJECTED
    }

## Methods required
1. scheduleMeeting()
2. getBestRoom()
3. acceptInvite()
4. rejectInvite()
5. cancelMeeting()
6. addParticipant()
7. notify()