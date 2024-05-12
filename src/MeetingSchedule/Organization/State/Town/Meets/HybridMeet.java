package MeetingSchedule.Organization.State.Town.Meets;

import MeetingSchedule.Organization.State.Town.Meeting;

import java.util.ArrayList;

public class HybridMeet extends Meeting {
    public HybridMeet(int startTime, String roomID, int capacity, int duration,ArrayList<String> votes) {
        super(startTime, STR."\{roomID} hybrid",capacity, duration,votes);
    }

    //-- Polymorphism
    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        while (this.capacity < this.getNumberOfMembers())
        {
            this.capacity*=10;
        }
        this.EndTime = this.StartTime + this.capacity;
    }
}
