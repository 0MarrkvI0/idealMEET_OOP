package Users;

import MeetingSchedule.Organization.State.Town.Day;
import MeetingSchedule.Organization.State.Town.Meeting;
import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.Vote.givingVote;

import java.io.Serializable;
import java.util.Random;

public class User implements givingVote, Serializable {
    private String Name;
    private String SurName;
    private String PersonID;
    private Gender Gender;

    //-- nested class
    public static class Gender implements Serializable {
        private String genderType;

        public Gender(String genderType) {
            this.genderType = genderType;
        }

        public String getGenderType() {
            return genderType;
        }

        public void setGenderType(String genderType) {
            this.genderType = genderType;
        }

        public char getGenderByChar() {
            if (genderType.equals("MALE")) {
                return 'X';
            } else {
                return 'Y';
            }
        }
    }

    private int Age;
    boolean IsAdmin;
    public boolean HasVoted = false;
    private TimeTableBoard MyWork;
    String Position;

    public void setHasVoted() {
        this.HasVoted = true;
    }

    public Gender getGender() {
        return Gender;
    }

    public String getUserID() {
        return this.PersonID;
    }

    public User(String name, String surname, int age, String gender, TimeTableBoard workspace, boolean admin, String position) {
        this.Name = name;
        this.SurName = surname;
        this.Age = age;
        this.Gender = new Gender(gender);
        this.MyWork = workspace;
        this.IsAdmin = admin;
        this.Position = position;
        this.PersonID = generatePersonID(this);
    }

    // generate user ID like code hash from his/her personal info
    protected String generatePersonID(User user) {
        String firstNameChars = createNewString(user.Name).toUpperCase();
        String lastNameChars = createNewString(user.SurName).toUpperCase();
        String ageString = String.valueOf(user.Age);
        String workspaceChars = getFirstThreeLetters(user.MyWork.Town);
        return user.getGender().getGenderByChar() + firstNameChars + lastNameChars + ageString + workspaceChars + user.Position.charAt(0);
    }

    // each user add his ID to meeting as vote
    @Override
    public void vote(User user, TimeTableBoard Workspace) {
        Random random = new Random();
        int dayIndex = random.nextInt(Workspace.getNumOfDays());
        int timeIndex = random.nextInt(Workspace.getNumOfMeets());

        if (dayIndex < Workspace.getNumOfDays()) {
            Day day = Workspace.getDayByIndex(dayIndex);
            if (timeIndex < day.getNumOfMeets()) {
                Meeting meeting = day.getMeetingByIndex(timeIndex);
                boolean found = false;
                for (String userID : meeting.getvotedUsers()) {
                    if (userID.equals(user.getUserID())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    meeting.add(user.getUserID());
                    meeting.setCapacity(meeting.getCapacity() - 1);
                    meeting.VoteForType();
                    return;
                }else {
                    vote(this, Workspace);
                }
            }
        }
    }

    // this method dictates how many votes has each user
    @Override
    public void setVote(TimeTableBoard Workspace) {
        Random random = new Random();
        int numberOfVotes = random.nextInt(Workspace.getNumOfMeets() * Workspace.getNumOfDays()) + 1;
        for (int i = 0; i < numberOfVotes; i++) {
            vote(this, Workspace);
        }
        this.setHasVoted();
    }


    public static User createUser(int position, String name, String surname, int age, String gender, TimeTableBoard workspace) {
        return switch (position) {
            case 5 -> new President(name, surname, age, gender, workspace);
            case 4 -> new Vice(name, surname, age, gender, workspace);
            case 3 -> new Leader(name, surname, age, gender, workspace);
            case 2 -> new Member(name, surname, age, gender, workspace);
            case 1 -> new Candidate(name, surname, age, gender, workspace);
            default -> null;
        };
    }

    public String createNewString(String original) {
        if (original != null && original.length() >= 2) {
            char firstChar = original.charAt(0);
            char lastChar = original.charAt(original.length() - 1);
            return Character.toString(firstChar) + Character.toString(lastChar);
        } else {
            return "";
        }
    }

    public String getFirstThreeLetters(String original) {
        if (original != null && original.length() >= 3) {
            return original.substring(0, 3);
        } else {
            return original;
        }
    }
}







