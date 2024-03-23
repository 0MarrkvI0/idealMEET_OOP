package Users;

public class User {
    String Name;
    String SurName;
    String PersonID;
    enum Sex {
        MALE,
        FEMALE
    }
    enum Position {
        PRESIDENT,
        VICEPRESIDENT,
        LEADER,
        MEMEBER,
        CANDIDATE
    }
    int Age;
    float PersonalIndex;
    boolean IsAdmin;

}
