package MeetingSchedule.Organization.State.Town;

import MeetingSchedule.Errors.WrongDateException;
import MeetingSchedule.Organization.State.Town.Meets.OfficeMeet;
import MeetingSchedule.Organization.State.Town.Meets.OnlineMeet;
import MeetingSchedule.Organization.State.Town.Meets.HybridMeet;
import Users.*;
import Users.Setup.LocalCommitteeSetup;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeTableBoard implements TimeTable, Serializable {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> ActiveVoters = new ArrayList<>();
    private ArrayList<Day> idealDay = new ArrayList<>();
    private LocalDate StartMeet;
    private LocalDate EndMeet;
    private int duration;
    private int capacity;
    public String Town;
    protected int max_index = Integer.MAX_VALUE;
    LocalCommitteeSetup committee = new LocalCommitteeSetup();
    transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    public TimeTableBoard(String town) {
        this.Town = town;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    public Day getDayByIndex(int index) {
        return this.idealDay.get(index);
    }

    public String getTown() {
        return this.Town;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    public int getNumOfDays() {
        return this.idealDay.size();
    }

    public int getNumOfMeets() {
        return this.idealDay.getFirst().getNumOfMeets();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public int getNumberOfMembers() {
        return users.size();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public ArrayList<Day> getIdealDay() {
        return this.idealDay;
    }

    public ArrayList<String> getActiveVoters() {
        return this.ActiveVoters;
    }

    public void setFormatter(){this.formatter = DateTimeFormatter.ofPattern("d.M.yyyy");}


    public void setupTimeTableB(String date1, String date2, int duration, int capacity) throws WrongDateException {
        setDates(date1, date2);
        setInformation(duration, capacity);
        generateIdealMeet(StartMeet, EndMeet);
    }

    public void setDates(String Start, String End) throws WrongDateException {
        LocalDate currentDate = LocalDate.now();

        this.StartMeet = LocalDate.parse(Start, formatter);
        this.EndMeet = LocalDate.parse(End, formatter);

        if (StartMeet.isBefore(currentDate) || EndMeet.isBefore(currentDate)) {
            throw new WrongDateException("Wrong date insert.");
        }
    }

    public void setInformation(int duration, int capacity) {
        this.duration = duration;
        this.capacity = capacity;
    }


    public static void removeStrings(Meeting meeting, ArrayList<String> strArray) {
        for (String str : strArray) {
            meeting.Votes.remove(str);
        }
    }

    public void deleteAllDays() {
        this.idealDay.clear();
    }

    public void AddUsers(String input, TimeTableBoard town) {
        int NewUsers = Integer.parseInt(input);
        committee.setup(town, NewUsers);
    }

    public void DeleteUsers(String input, TimeTableBoard town) {
        int RemoveUsers = Integer.parseInt(input);
        committee.remove(town, RemoveUsers);

    }
    // for each day create meeting from 8 - 22h
    private void generateIdealMeet(LocalDate startMeet, LocalDate endMeet) {
        final int start = 8;
        final int end = 22;
        int counterID_day = 1;
        LocalDate currentDate = StartMeet;
        while (!currentDate.isAfter(endMeet)) {
            Day newDay = new Day(currentDate, Integer.toString(counterID_day++), getCapacity(), getDuration());
            int counterID_meets = 1;
            for (int i = start; i < end; i += getDuration()) {
                Meeting idealMEET = new Meeting(i, Integer.toString(counterID_meets), getCapacity(), getDuration());
                newDay.idealMEETs.add(idealMEET);
                counterID_meets++;
            }
            currentDate = currentDate.plusDays(1);
            idealDay.add(newDay);
        }
    }
    // change meeting type
    public void updateStatusMeeting() {
        int NumMembers = this.getNumberOfMembers();
        for (Day day : idealDay) {
            for (int i = 0; i < day.idealMEETs.size(); i++) {
                Meeting meeting = day.idealMEETs.get(i);
                if (meeting.Votes.size() > meeting.capacity) {
                    // when there are more users than capacity - create Online Meet
                    day.idealMEETs.set(i, new OnlineMeet(meeting.getStartTime(), meeting.getID(), meeting.getDefault_capacity(), meeting.getDuration(), meeting.getvotedUsers()));
                } else if (Math.abs(meeting.VoteForOffline - meeting.VoteForOnline) <= 3) {
                    // when votes for online and offline are fairly equal - create Hybrid Meet
                    day.idealMEETs.set(i, new HybridMeet(meeting.getStartTime(), meeting.getID(), meeting.getDefault_capacity(), meeting.getDuration(), meeting.getvotedUsers()));
                } else if (meeting.VoteForOffline == 0 && meeting.VoteForOnline == 0) {
                    // if no one has voted for that meet - delete
                    day.idealMEETs.remove(i);
                    i--;
                } else {
                    // create Office Meet
                    day.idealMEETs.set(i, new OfficeMeet(meeting.getStartTime(), meeting.getID(), meeting.getDefault_capacity(), meeting.getDuration(), meeting.getvotedUsers()));
                }
            }
        }
        for (Day day : idealDay) {
            //-- RTTI
            for (Meeting meeting : day.getIdealMEETs()) {
                if (meeting instanceof HybridMeet || meeting instanceof OnlineMeet) {
                    //-- Polymorphism
                    // increase capacity
                    meeting.setCapacity(meeting.getCapacity());
                }
            }

        }
    }

    public void deleteIdealMeet(double deleteIndex) {
        int NumMembers = this.getNumberOfMembers();
        for (Day day : idealDay) {
            ArrayList<Meeting> meetingsToRemove = new ArrayList<>();
            for (Meeting meeting : day.idealMEETs) {
                if (meeting.Votes.size() < NumMembers * deleteIndex) {
                    meetingsToRemove.add(meeting);
                    continue;
                }
                if (!meeting.findImportantPerson()) {
                    meetingsToRemove.add(meeting);
                }
            }
            day.idealMEETs.removeAll(meetingsToRemove);
        }
    }

    // find meet with the most votes
    public int findMaxArraySizeIndex(int maximum) {
        int minIndex = -1, DAY = -1, TIME = -1, i = 0, j = 0;

        for (Day day : this.idealDay) {
            for (Meeting meeting : day.idealMEETs) {
                int curr_size = meeting.Votes.size();
                if ((curr_size <= maximum) && (minIndex <= curr_size)) {
                    minIndex = curr_size;
                    DAY = i;
                    TIME = j;
                }
                j++;
            }
            i++;
        }
        return DAY * 1000 + TIME;
    }

    // delete meets with small number of votes and choose one with most
    public void chooseIdealMeet(double startIndex) {
        this.updateStatusMeeting();
        this.deleteIdealMeet(startIndex);
        int meet_index = this.findMaxArraySizeIndex(max_index);
        int time = meet_index % 1000;
        int day = (meet_index - time) / 1000;
        Meeting meeting = this.idealDay.get(day).idealMEETs.get(time);
        this.max_index = meeting.Votes.size();
        this.ActiveVoters.addAll(meeting.Votes);
        meeting.IsChoosen = true;
        for (Day day1 : this.idealDay) {
            for (Meeting meeting1 : day1.idealMEETs) {
                if (!meeting1.IsChoosen) removeStrings(meeting1, this.ActiveVoters);
            }
        }
        this.deleteIdealMeet(startIndex / 2);
    }

    // delete all meets
    public void endElection() {
        this.idealDay = new ArrayList<Day>();
        this.ActiveVoters = new ArrayList<String>();
        this.capacity = 0;
        this.duration = 0;
        this.StartMeet = null;
        this.EndMeet = null;
        for (User user : this.users) {
            user.HasVoted = false;
        }
    }
}
