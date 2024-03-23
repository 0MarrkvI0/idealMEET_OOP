import MeetingSchedule.GlobalSystem;
import MeetingSchedule.TimeTableBoard;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        GlobalSystem ListOfOrganizations = new GlobalSystem();
        TimeTableBoard MyOrganization = new TimeTableBoard("IAESTE","Bratislava","Slovakia");
        MyOrganization.setInformation(2,20,3);
        MyOrganization.setDates("23.3.2024","30.3.2024");

    }
}