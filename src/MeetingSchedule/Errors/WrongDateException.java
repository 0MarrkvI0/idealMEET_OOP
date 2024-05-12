package MeetingSchedule.Errors;

public class WrongDateException extends Exception {
    private String errorMessage;

    public WrongDateException(String message) {
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        return STR."WrongDateError: \{errorMessage}";
    }
}
