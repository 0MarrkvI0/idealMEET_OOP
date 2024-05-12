package Users;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;

public class Vice extends User {
    public Vice(String name, String surname, int age, String gender, TimeTableBoard workspace) {
        super(name, surname, age, gender, workspace, false, "VICEPRESIDENT");
    }

    @Override
    public void setVote(TimeTableBoard Workspace) {
        for (int i = 0; i < (int) Math.ceil(Workspace.getNumOfDays() * Workspace.getNumOfMeets() / 1.5); i++) {
            vote(this, Workspace);
        }
        this.setHasVoted();
    }
}
