package MeetingSchedule;

import java.util.Date;

public class OfficeMeet extends Meeting
{
    public OfficeMeet(Date date, int StartTime, int Duration, int capacity, String RoomID){
        super(date,StartTime,Duration,capacity,RoomID);
        this.RoomID = RoomID + " office";
        this.place = Place.OFFICE;
    }

}
