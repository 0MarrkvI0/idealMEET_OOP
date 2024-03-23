package MeetingSchedule;

import java.time.LocalDate;
import java.util.Date;
public class NormMeeting extends Meeting {
    public NormMeeting(LocalDate date, int StartTime, int Duration, int capacity, String RoomID) {
        super(date, StartTime, Duration, capacity, RoomID);
    }
}
