package MeetingSchedule;

import java.util.List;

public class GlobalSystem {
    List<TimeTableBoard> organization;

    public GlobalSystem() {
        organization = new ArrayList<>();
    }
    public void addTimeTable(TimeTableBoard timeTable){
        organization.add(timeTable);
    }

}
