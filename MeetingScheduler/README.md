## Requirements
1. There should be N number of meeting rooms.

2. Each meeting room should have a specific capacity to accommodate the desired number of people.

3. If not reserved already, each meeting room should have the ability to be booked, along with setting an interval, a start time, and an end time for the meeting.

4. A notification should be sent to all the people invited to the meeting.

5. Users will receive an invitation regardless of whether they are available at the interval or not.

## Assumptions
1. Meeting time cannot be updated. If timing needs to be updated, user has to cancel and book again.
2. Every room has one calendar associated with it and schedules of a room is maintained in it.
3. If size of the meeting room specified by the organizer is not available, system automatically tries to schedule next bigger room and ultimately gives up if no room is available.
4. A participant can be removed from the meeting. In this case, removed participant is notified.
5. There's no limit on meeting duration. However, a meeting will start and end on the same day.

## Flow
1. User adds number of participants, meeting agenda and meeting interval (startsAt and endsAt).
2. System searches for available room.
   1. If no room is available, scheduling request is canceled with appropriate message.
   2. If room is available, it's booked.
3. As soon as the room is booked, the user is asked to add participants.
4. After adding participant(s), system sends invites to all of them.
5. The user can also choose to cancel the meeting, in which case cancellation notification is triggered