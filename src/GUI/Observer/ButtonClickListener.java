package GUI.Observer;

import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Errors.WrongDateException;

public interface ButtonClickListener {
    //-- observer pattern
    public void update() throws DuplicateError, NotFound, WrongDateException;
}
