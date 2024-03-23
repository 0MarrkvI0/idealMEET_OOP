package MeetingSchedule;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import Users.User;

public class TimeTableBoard implements TimeTable
{
    protected List<User> users;
    public List<Meeting> idealMEETs;
    //asik pomeninm na private
    private Date StartMeet;
    protected Date EndMeet;
    protected int duration;
    protected int capacity;
    protected int DeadLine;
    public String Name;
    public String Town;
    public String State;

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
    @Override
    public void initializeTimeTable(String name, String town, String state, Date startMeet, Date endMeet, int duration, int capacity, int deadline) {
        //this.users = users;
        this.Name = name;
        this.Town = town;
        this.State = state;
        this.StartMeet = startMeet;
        this.EndMeet = endMeet;
        this.duration = duration;
        this.capacity = capacity;
        this.DeadLine = deadline;
    }
    public void addUser(User user){
        users.add(user);
    }

    private void generateIdealMeet(Date startMeet, Date endMeet){
        final int start = 8;
        final int end = 22;
        int counterID = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startMeet); // Set the calendar to startMeet

        // Loop through each day between startMeet and endMeet
        while (!calendar.getTime().after(endMeet)) {
            Date currentDate = calendar.getTime();
            System.out.println(currentDate); // Print each day

            for (int i = start; i <= end; i+=getDuration())
            {
                Meeting meeting = new Meeting(currentDate,i,getDuration(),this.getCapacity(),Integer.toString(counterID));
                idealMEETs.add(meeting);
                counterID++;
            }
            calendar.add(Calendar.DATE, 1); // Move to the next day
        }
    }
    public int getNumberOfMembers(List<User> userList) {
        return userList.size();
    }
}
