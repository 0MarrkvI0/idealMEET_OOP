package GUI.Observer;

import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Errors.WrongDateException;

public interface ButtonHasClicked {
    public void registerObserver (ButtonClickListener observer);
    public void removeObserver (ButtonClickListener observer);
    public void notifyObservers () throws DuplicateError, NotFound, WrongDateException;
}
