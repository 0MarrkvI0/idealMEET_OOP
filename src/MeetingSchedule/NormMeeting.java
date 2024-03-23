package MeetingSchedule;

import java.util.Date;
public class NormMeeting extends OfficeMeet,OnlineMeet
{
    public NormMeeting(Date date, int StartTime, int Duration, int capacity, String RoomID){
        super(date,StartTime,Duration,capacity,RoomID);
    }
}
