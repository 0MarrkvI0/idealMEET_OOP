package MeetingSchedule;

import Users.User;
import MeetingSchedule.TimeTableBoard;
import java.util.Date;
import java.util.List;

public class Meeting implements TimeTable {
    protected String RoomID;
    protected int capacity;
    protected List<User> Votes;
    protected Date date;
    protected int StartTime;
    protected int Duration;
    protected Place place;


    enum Place {
        OFFICE,
        DISCORD
    }
    public Meeting( Date date, int StartTime, int Duration,int capacity,String RoomID) {
        this.RoomID = RoomID;
        this.capacity = capacity;
        this.date = date;
        this.StartTime = StartTime;
        this.Duration = Duration;
    }

    public int getNumberOfMembers() {
        return Votes.size();
    }
    @Override
    public void addUser(User user) {
        Votes.add(user);
    }
}
