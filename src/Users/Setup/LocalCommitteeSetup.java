package Users.Setup;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.User;

import java.io.Serializable;
import java.util.ListIterator;

public class LocalCommitteeSetup implements MeetSetup, Serializable {
    public void setup(TimeTableBoard workspace, int numUsers) {

        int reminder = numUsers;

        if (workspace.getUsers().isEmpty()) {
            User president = PersonGenerator.generateUser(5, workspace);
            User vice = PersonGenerator.generateUser(4, workspace);
            workspace.addUser(president);
            workspace.addUser(vice);
            reminder -= 2;
        }

        double leadersPercentage = 30; // 10%
        double membersPercentage = 40; // 70%

        // Calculate number of individuals for each group
        int leadersCount = (int) Math.ceil(reminder * (leadersPercentage / 100));
        int membersCount = (int) Math.ceil(reminder * (membersPercentage / 100));
        int candidatesCount = reminder - membersCount - leadersCount;


        for (int i = 0; i < leadersCount; i++) {
            User leader = PersonGenerator.generateUser(3, workspace);
            workspace.addUser(leader);
        }

        for (int i = 0; i < membersCount; i++) {
            User member = PersonGenerator.generateUser(2, workspace);
            workspace.addUser(member);
        }

        for (int i = 0; i < candidatesCount; i++) {
            User candidate = PersonGenerator.generateUser(1, workspace);
            workspace.addUser(candidate);
        }
    }

    public void remove(TimeTableBoard workspace, int numUsers) {
        ListIterator<User> iterator = workspace.getUsers().listIterator(workspace.getUsers().size());
        for (int i = 0; i < numUsers && iterator.hasPrevious(); i++) {
            iterator.previous();
            iterator.remove();
        }
    }

}
