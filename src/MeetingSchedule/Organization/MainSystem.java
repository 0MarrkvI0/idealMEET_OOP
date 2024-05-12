package MeetingSchedule.Organization;

import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Organization.State.GlobalSystem;
import MeetingSchedule.Organization.State.Town.TimeTableBoard;

import java.io.Serializable;
import java.util.*;

public class MainSystem implements Serializable {
    // name of organization
    private String name;
    private ArrayList<GlobalSystem> listOfStates;

    public MainSystem(String name) {
        this.name = name;
        listOfStates = new ArrayList<GlobalSystem>();
    }

    public String getName() {
        return this.name;
    }
    public GlobalSystem getMyStateByIndex(int index) {
        return this.listOfStates.get(index);
    }
    public ArrayList<GlobalSystem> getListOfStates() {
        return this.listOfStates;
    }

    public void sortStatesByName() {
        listOfStates.sort(Comparator.comparing(GlobalSystem::getStateName));
    }


    public GlobalSystem findStateByName(String MyState) {
        for (GlobalSystem state : this.listOfStates) {
            if (MyState.equals(state.getStateName())) {
                return state;
            }
        }
        return null;
    }

    public void addNewState(String StateName) throws DuplicateError {
        if (findStateByName(StateName) == null) {
            this.listOfStates.add(new GlobalSystem(StateName));
            sortStatesByName();
        } else {
            throw new DuplicateError(STR."\{StateName} has been already added.\n-----\n");
        }
    }

    public void deleteState(String StateName) throws NotFound {
        GlobalSystem MyState = findStateByName(StateName);
        if (MyState != null && listOfStates.contains(MyState)) {
            this.listOfStates.remove(MyState);
            sortStatesByName();
        } else {
            throw new NotFound(STR."\{StateName} is not in the list.\n-----\n");
        }
    }

    public TimeTableBoard findTownByName(String name) {
        for (GlobalSystem state : this.getListOfStates()) {
            for (TimeTableBoard town : state.getListOfTowns()) {
                if (name.equals(town.getTown())) {
                    return town;
                }
            }
        }
        return null;
    }

    private boolean hasTown(String townName, GlobalSystem state) {
        for (TimeTableBoard town : state.getListOfTowns()) {
            if (town.getTown().equals(townName)) {
                return true;
            }
        }
        return false;
    }

    public GlobalSystem stateContainingTown(String townName) {
        for (GlobalSystem state : this.getListOfStates()) {
            if (hasTown(townName, state)) {
                return state;
            }
        }
        return null;
    }
}