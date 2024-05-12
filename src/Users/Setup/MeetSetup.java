package Users.Setup;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;

public interface MeetSetup {
    void setup(TimeTableBoard workspace, int numberOfUsers);

    void remove(TimeTableBoard workspace, int numUsers);
}
