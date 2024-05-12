package MeetingSchedule.Organization.State.Town;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day implements Serializable {
    private LocalDate TodayDate;
    private DayOfWeek dayOfWeek;

    private String ID;
    protected int capacity;
    private int duration;
    protected List<Meeting> idealMEETs = new ArrayList<>();


    public LocalDate getTodayDate() {
        return TodayDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getID() {
        return ID;
    }

    public Meeting getMeetingByIndex(int index) {
        return this.idealMEETs.get(index);
    }

    public List<Meeting> getIdealMEETs() {
        return idealMEETs;
    }

    public String getDate() {
        return this.TodayDate.toString();
    }

    public int getDuration() {
        return this.duration;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getNumOfMeets() {
        return this.idealMEETs.size();
    }

    public Day(LocalDate date, String ID, int capacity, int duration) {
        this.TodayDate = date;
        this.dayOfWeek = TodayDate.getDayOfWeek();
        this.ID = ID;
        this.capacity = capacity;
        this.duration = duration;
    }

    public Day(int capacity, int duration) {
        this.capacity = capacity;
        this.duration = duration;
    }
}
