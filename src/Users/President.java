package Users;

import MeetingSchedule.Organization.State.Town.Day;
import MeetingSchedule.Organization.State.Town.Meeting;
import MeetingSchedule.Organization.State.Town.TimeTableBoard;

public class President extends User {
    public President(String name, String surname, int age, String gender, TimeTableBoard workspace) {
        super(name, surname, age, gender, workspace, true, "PRESIDENT");
    }

    @Override
    public void vote(User user, TimeTableBoard Workspace) {
        for (Day day : Workspace.getIdealDay()) {
            for (Meeting meeting : day.getIdealMEETs()) {
                meeting.add(this.getUserID());
                meeting.setCapacity(meeting.getCapacity() - 1);
                meeting.VoteForType();
            }
        }
    }

    @Override
    public void setVote(TimeTableBoard Workspace) {
        vote(this, Workspace);
        this.setHasVoted();
    }
}

