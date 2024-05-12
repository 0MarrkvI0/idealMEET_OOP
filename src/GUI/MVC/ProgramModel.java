package GUI.MVC;

import GUI.ElectionThread;
import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Errors.WrongDateException;
import MeetingSchedule.Organization.MainSystem;
import MeetingSchedule.Organization.State.GlobalSystem;
import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.Setup.LocalCommitteeSetup;
import Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramModel {
    // organization
    private MainSystem myOrganization = null;
    // current state
    private GlobalSystem visitedState = null;
    // current town
    private TimeTableBoard visitedTown = null;


    public void setMyOrganization(String name) {
        this.myOrganization = new MainSystem(name);
    }

    public MainSystem getMyOrganization() {
        return this.myOrganization;
    }

    public String getMyOrganizationName() {
        return this.myOrganization.getName();
    }

    public GlobalSystem getVisitedState() {
        return this.visitedState;
    }

    public TimeTableBoard getVisitedTown() {
        return this.visitedTown;
    }

    //-- state

    public String findState(String state) {
        visitedState = myOrganization.findStateByName(state);
        if (visitedState != null) {

            return STR."\{state} is in \{myOrganization.getName()}\n-----\n";
        } else {
            return STR."Failed: \{state} is not in \{myOrganization.getName()}\n-----\n";
        }
    }

    public String addNewState(String state) throws DuplicateError {
        try {
            myOrganization.addNewState(state);
            return STR."\{state} added successfully\n-----\n";
        } catch (DuplicateError duplicateError) {
            return duplicateError.toString();
        }
    }

    public String deleteState(String state) throws NotFound {
        try {
            myOrganization.deleteState(state);
            return STR."\{state} deleted successfully\n-----\n";
        } catch (NotFound notFound) {
            return notFound.toString();
        }
    }

    //-- town

    public String findTown(String town) {
        visitedTown = myOrganization.findTownByName(town);
       // Class<? extends TimeTableBoard> townClassToFind = visitedTown.getClass();
        visitedState = myOrganization.stateContainingTown(visitedTown.getTown());
        if (visitedTown != null) {

            return STR."\{town} is in \{visitedState.getStateName()}\n-----\n";
        } else {
            return STR."Failed: \{town} is not in \{myOrganization.getName()}\n-----\n";
        }
    }

    public String addNewTown(String town) throws DuplicateError {

        try {
            visitedState.addNewTown(town);
            return STR."\{town} added successfully\n-----\n";
        } catch (DuplicateError duplicateError) {
            return duplicateError.toString();
        }
    }

    public String deleteTown(String town) throws NotFound {
        try {
            visitedState.deleteTown(town);
            return STR."\{town} deleted successfully\n-----\n";
        } catch (NotFound notFound) {
            return notFound.toString();
        }
    }

    //-- meet

    public String createMeeting(ArrayList<String> input) throws WrongDateException {
        visitedTown = myOrganization.findTownByName(input.get(0));
        if (visitedTown != null) {
            if (!visitedTown.getIdealDay().isEmpty()) {
                return "iMeet has been already set.\n-----\n";
            }
            try {
                visitedTown.setupTimeTableB(input.get(1), input.get(2), Integer.parseInt(input.get(3)), Integer.parseInt(input.get(4)));
                return STR."iMeet in \{visitedTown.getTown()} was successfully created\n-----\n";
            } catch (WrongDateException wrongDateException) {
                return wrongDateException.toString();
            }
        } else {
            return STR."\{input.getFirst()} not found\n-----\n";
        }
    }

    public String deleteMeeting(String town) {
        visitedTown = myOrganization.findTownByName(town);
        if (visitedTown != null) {
            if (visitedTown.getIdealDay().isEmpty()) {
                return "iMeet has not been set.\n-----\n";
            }
            visitedTown.deleteAllDays();
            return STR."\{visitedTown.getTown()} has now no iMeets\n-----\n";
        }
        return STR."\{town} not found\n-----\n";
    }

    //-- user

    public String addUsers(String NumUsers) {
        this.visitedTown.AddUsers(NumUsers, visitedTown);
        return STR."\{NumUsers} users has been added in \{visitedTown.getTown()}\n-----\n";
    }

    public String deleteUsers(String NumUsers) {
        this.visitedTown.DeleteUsers(NumUsers, visitedTown);
        return STR."\{NumUsers} users has been deleted from \{visitedTown.getTown()}\n-----\n";
    }

    //-- election

    public String makeElection() {
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
        return STR."\{visitedTown.getTown()} has voted";
    }

    //-- multithreading
    public void electionForAllTowns() {
        List<Thread> threads = new ArrayList<>();

        for (GlobalSystem state : myOrganization.getListOfStates()) {
            for (TimeTableBoard town : state.getListOfTowns()) {
                if (!town.getIdealDay().isEmpty())
                {
                    // creating separate thread for each town so the elections can run parallel
                    ElectionThread electionThread = new ElectionThread(town);
                    threads.add(electionThread);
                    electionThread.start();
                }
            }
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //-- end election

    public void closeElection() {
        visitedTown.endElection();
    }

    public void closeAllELection() {
        for (GlobalSystem state : myOrganization.getListOfStates()) {
            for (TimeTableBoard town : state.getListOfTowns()) {
                town.endElection();
            }
        }
        visitedTown = null;
    }

    // set date format to d.M.yyyy
    public void setFormatter(){
        for (GlobalSystem state:myOrganization.getListOfStates())
        {
            for (TimeTableBoard town:state.getListOfTowns())
            {
                town.setFormatter();
            }
        }
    }

    //-- object serialization
    public void serializeObject() {
        try (FileOutputStream fos = new FileOutputStream("iMeet.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(myOrganization);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-- object deserialization
    public void loadSerializeObject() {
        try (FileInputStream fis = new FileInputStream("iMeet.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            myOrganization = (MainSystem) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}





