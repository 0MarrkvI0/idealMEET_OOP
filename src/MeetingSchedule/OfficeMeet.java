package MeetingSchedule;

import java.time.LocalDate;
import java.util.Date;



public class OfficeMeet extends Meeting
{
    public OfficeMeet(LocalDate date, int StartTime, int Duration, int capacity, String RoomID){
        super(date,StartTime,Duration,capacity,RoomID);
        this.RoomID = RoomID + " office";

    }

}
