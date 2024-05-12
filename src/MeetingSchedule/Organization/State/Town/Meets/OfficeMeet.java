package MeetingSchedule.Organization.State.Town.Meets;

import MeetingSchedule.Organization.State.Town.Meeting;

import java.util.ArrayList;

public class OfficeMeet extends Meeting {
    public OfficeMeet(int startTime, String roomID, int capacity, int duration, ArrayList<String> votes) {
        super(startTime, STR."\{roomID} office", capacity, duration, votes);
    }
}

