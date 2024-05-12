package MeetingSchedule.Organization.State.Town;

import Users.User;

import java.util.Date;
import java.util.List;

public interface TimeTable {
    int getDuration();
    int getCapacity();
    int getNumberOfMembers();
    void addUser(User user);

}


