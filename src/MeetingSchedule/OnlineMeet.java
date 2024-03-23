package MeetingSchedule;

import java.time.LocalDate;
import java.util.Date;

public class OnlineMeet extends Meeting
{
    public OnlineMeet(LocalDate date, int StartTime, int Duration, int capacity, String RoomID){
        super(date,StartTime,Duration,capacity,RoomID);
        this.capacity = getNumberOfMembers();
        this.RoomID = RoomID + " online";
    }
}
