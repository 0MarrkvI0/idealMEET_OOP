package GUI;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.User;

public class ElectionThread extends Thread {
    private TimeTableBoard town;

    public ElectionThread(TimeTableBoard town) {
        this.town = town;
    }

    public void run() {
        makeElection(town);
    }

    private void makeElection(TimeTableBoard visitedTown) {
        // all user are voting
        for (User user : visitedTown.getUsers()) {
            user.setVote(visitedTown);
        }

        double index = 0.5;
        // finding top 3 suitable meets for all users
        while (visitedTown.getNumOfMeets() >= 3) {
            visitedTown.chooseIdealMeet(index);
            index /= 2;
        }
    }
}

