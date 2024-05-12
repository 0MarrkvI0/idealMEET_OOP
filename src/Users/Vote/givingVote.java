package Users.Vote;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.User;

public interface givingVote {
    public void vote(User user, TimeTableBoard Workspace) ;
    public void setVote(TimeTableBoard Workspace);
}
