package Users;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;

public class Leader extends User {
    public Leader(String name, String surname, int age, String gender, TimeTableBoard workspace) {
        super(name, surname, age, gender, workspace, false, "LEADER");
    }
    @Override
    public void setVote(TimeTableBoard Workspace) {
        for (int i = 0; i < (int) Math.ceil((Workspace.getNumOfDays() * Workspace.getNumOfMeets()) / 2.0); i++) {
            vote(this, Workspace);
        }
        this.setHasVoted();
    }
}

