package MeetingSchedule;

import Users.User;

import java.util.Date;
import java.util.List;


//toto pouzijem na take funkcie ze vypis set a tak
public interface TimeTable {
    int getDuration();
    int getCapacity();
    int getDeadline();
    void initializeTimeTable(String name, String town, String state, Date startMeet, Date endMeet, int duration, int capacity, int deadline);
    int getNumberOfMembers(List<User> userList);
    void addUser(User user);
}


