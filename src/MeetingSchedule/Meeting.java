package MeetingSchedule;

import Users.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Meeting implements TimeTable {
    protected String RoomID;
    protected int capacity;
    protected List<User> Votes;
    protected LocalDate date;
    protected int StartTime;
    protected int Duration;
    public enum Place {
        OFFICE(0),
        DISCORD(1);

        private int order;

        Place(int i) {
            this.order = i;
        }

        public int getOrder() {
            return order;
        }

        public void setPlace(int i) {
            this.order = i;
        }
    }


    public Meeting(LocalDate date, int StartTime, int Duration, int capacity, String RoomID) {
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
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public int getDeadline() {
        return 0;
    }

    @Override
    public int getNumberOfMembers(List<User> userList) {
        return 0;
    }

    @Override
    public void addUser(User user) {
        Votes.add(user);
    }
}
