package MeetingSchedule.Errors;

//-- own exception
public class DuplicateError extends Exception {
    private String errorMessage;

    public DuplicateError(String message) {
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        return STR."DuplicateError: \{errorMessage}";
    }
}

