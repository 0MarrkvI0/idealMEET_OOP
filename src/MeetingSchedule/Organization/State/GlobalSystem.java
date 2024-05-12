package MeetingSchedule.Organization.State;

import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Organization.State.Town.TimeTableBoard;

import java.io.Serializable;
import java.util.*;

public class GlobalSystem implements Serializable {
    private ArrayList<TimeTableBoard> listOfTowns;
    private String State;

    public GlobalSystem(String location) {
        listOfTowns = new ArrayList<TimeTableBoard>();
        this.State = location;
    }

    public String getStateName() {
        return this.State;
    }

    public ArrayList<TimeTableBoard> getListOfTowns() {
        return this.listOfTowns;
    }

    public static GlobalSystem createState(String location) {
        return new GlobalSystem(location);
    }

    public void sortTownsByName() {
        listOfTowns.sort(Comparator.comparing(TimeTableBoard::getTown));
    }


    public TimeTableBoard findTownByName(String MyTown) {
        for (TimeTableBoard town : this.listOfTowns) {
            if (MyTown.equals(town.getTown()))
                return town;
        }
        return null;
    }

    public void addNewTown(String TownName) throws DuplicateError {
        if (findTownByName(TownName) == null) {
            this.listOfTowns.add(new TimeTableBoard(TownName));
            sortTownsByName();
        } else {
            throw new DuplicateError(STR."\{TownName}has been already added.\n-----\n");
        }
    }

    public void deleteTown(String TownName) throws NotFound {
        TimeTableBoard MyTown = findTownByName(TownName);
        if (MyTown != null && listOfTowns.contains(MyTown)) {
            this.listOfTowns.remove(MyTown);
            sortTownsByName();
        } else {
            throw new NotFound(STR."\{MyTown} is not in the list.\n-----\n");
        }
    }
}



