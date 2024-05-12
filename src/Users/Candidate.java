package Users;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;

public class Candidate extends User {
    public Candidate(String name, String surname, int age, String gender, TimeTableBoard workspace) {
        super(name, surname, age, gender, workspace, false, "CANDIDATE");
    }
    @Override
    public void setVote(TimeTableBoard Workspace) {
        for (int i = 0; i < (int) Math.ceil((Workspace.getNumOfDays() * Workspace.getNumOfMeets()) / 3.0); i++) {
            vote(this, Workspace);
        }
        this.setHasVoted();
    }
}
