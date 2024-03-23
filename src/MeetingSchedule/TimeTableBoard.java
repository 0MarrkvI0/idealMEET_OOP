package MeetingSchedule;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.time.LocalDate;

import Users.User;

public class TimeTableBoard implements TimeTable
{
    public List<User> users;
    public List<Meeting> idealMEETs;
    protected LocalDate StartMeet;
    protected LocalDate EndMeet;
    protected int duration;
    protected int capacity;
    protected int DeadLine;
    public String Name;
    public String Town;
    public String State;

    public TimeTableBoard(String name, String town, String state) {
        this.Name = name;
        this.Town = town;
        this.State = state;
    }

    public void setDates(String Start, String End) {
        LocalDate currentDate = LocalDate.now(); // Get the current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        this.StartMeet = LocalDate.parse(Start, formatter);
        this.EndMeet = LocalDate.parse(End, formatter);

        if (StartMeet.isBefore(currentDate) || EndMeet.isBefore(currentDate)){
            System.out.println("Wrong date insert. Try again.");
            setDates("23.3.2024","30.3.2024");
        }
        generateIdealMeet(StartMeet,EndMeet);
    }
    public void setInformation(int duration, int capacity, int deadline){
        this.duration = duration;
        this.capacity = capacity;
        this.DeadLine = deadline;
        this.users = new ArrayList<>();
        this.idealMEETs = new ArrayList<>();
    }
    // Getter methods
    @Override
    public int getDuration() {
        return duration;
    }
    @Override
    public int getCapacity() {
        return capacity;
    }
    @Override
    public int getDeadline() {
        return DeadLine;
    }

    public void addUser(User user){
        users.add(user);
    }

    private void generateIdealMeet(LocalDate startMeet, LocalDate endMeet){
        final int start = 8;
        final int end = 22;
        int counterID = 1;

        LocalDate currentDate = StartMeet;
        while (!currentDate.isAfter(endMeet)) {
            System.out.println(currentDate);
            for (int i = start; i <= end; i+=getDuration())
            {
                Meeting meeting = new Meeting(currentDate,i,this.getDuration(),this.getCapacity(),Integer.toString(counterID));
                idealMEETs.add(meeting);
                System.out.println(i);
                counterID++;
            }
            currentDate = currentDate.plusDays(1); // Increment date by one day
        }
    }
    public int getNumberOfMembers(List<User> userList) {
        return userList.size();
    }
}
