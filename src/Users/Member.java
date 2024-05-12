package Users;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;

public class Member extends User {
    public Member(String name, String surname, int age, String gender, TimeTableBoard workspace) {
        super(name, surname, age, gender, workspace, false, "MEMBER");
    }

    @Override
    public void setVote(TimeTableBoard Workspace) {
        for (int i = 0; i < (int) Math.ceil((Workspace.getNumOfDays() * Workspace.getNumOfMeets()) / 2.5); i++) {
            vote(this, Workspace);
        }
        this.setHasVoted();
    }
}
