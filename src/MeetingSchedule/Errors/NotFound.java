package MeetingSchedule.Errors;

public class NotFound extends Exception {
        private String errorMessage;

        public NotFound(String message) {
            this.errorMessage = message;
        }

        @Override
        public String toString() {
            return STR."NotFoundError: \{errorMessage}";
        }
    }
