package MeetingSchedule.Organization.State.Town;

import java.util.ArrayList;
import java.util.Random;

public class Meeting extends Day {
    private String RoomID;
    protected ArrayList<String> Votes = new ArrayList<>();
    protected int StartTime;
    protected int EndTime;
    protected int default_capacity ;
    int VoteForOnline;
    int VoteForOffline;
    boolean IsChoosen = false;

    public void add(String personID) {
        Votes.add(personID);
    }

    public int getStartTime() {
        return this.StartTime;
    }
    public ArrayList<String> getvotedUsers() {
        return this.Votes;
    }
    public int getNumberOfMembers() {
        return Votes.size();
    }
    public int getDefault_capacity(){return this.default_capacity;}
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public Meeting(int startTime, String roomID, int capacity, int duration) {
        super(capacity, duration);
        this.default_capacity = capacity;
        this.StartTime = startTime;
        this.RoomID = roomID;
        this.EndTime = startTime + duration;
    }

    public Meeting(int startTime, String roomID, int duration, int capacity, ArrayList<String> votes) {
        super(capacity, duration);
        this.StartTime = startTime;
        this.RoomID = roomID;
        this.EndTime = startTime + duration;
        this.Votes = votes;
    }

    // user can vote for online/offline meet
    public void VoteForType() {
        boolean Online, Offline;
        Random random = new Random();
        do {
            Online = random.nextBoolean();
            Offline = random.nextBoolean();
        } while (!Online && !Offline);
        if (Online)
            this.VoteForOnline++;
        if (Offline)
            this.VoteForOffline++;
    }

    // meet must contain one President or Vice or at least 3 leaders to be considered as valid
    public boolean findImportantPerson() {
        int leader = 0;
        for (String userID : this.Votes) {
            char lastChar = userID.charAt(userID.length() - 1);
            if (lastChar == 'P' || lastChar == 'V') {
                return true;
            }
            if (lastChar == 'L') {
                leader++;
                if (leader == 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
