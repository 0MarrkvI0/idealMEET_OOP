package MeetingSchedule;

import Users.User;

import java.util.Date;
import java.util.List;


//toto pouzijem na take funkcie ze vypis set a tak
public interface TimeTable {
    int getDuration();
    int getCapacity();
    int getDeadline();
    int getNumberOfMembers(List<User> userList);
    void addUser(User user);
}


