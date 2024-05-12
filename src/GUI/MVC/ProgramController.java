package GUI.MVC;

import GUI.Observer.ButtonClickListener;
import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Errors.WrongDateException;

import java.util.ArrayList;

public class ProgramController implements ButtonClickListener {
    private ProgramModel model;
    private ProgramView view;
    private boolean FreshStart;
    // how many inputs are expected
    private int numInput = 0;
    // indicates method
    private int indexInput;
    // input storage
    private ArrayList<String> input = new ArrayList<String>();


    public ProgramController(ProgramModel model, ProgramView view, boolean NewStart) {
        this.model = model;
        this.view = view;
        this.FreshStart = NewStart;

    }

    // when button is pressed this method choose behavior
    @Override
    public void update() throws DuplicateError, NotFound, WrongDateException {
        switch (view.getButtonName()) {

            case "X":
                model.serializeObject();
                break;

            case "Create Organization":
                view.disableKeyButtons();
                view.selectOrganizationGUI();
                numInput = 1;
                indexInput = 0;
                break;

            case "State":
                view.disableKeyButtons();
                view.selectStateGUI();
                numInput = 1;
                indexInput = 1;
                break;

            case "Town":
                view.disableKeyButtons();
                view.selectStateGUI();
                numInput = 2;
                indexInput = 2;
                break;

            case "iMeet":
                view.disableKeyButtons();
                view.selectTownGUI();
                indexInput = 3;
                numInput = 5;
                break;

            case "User":
                view.disableKeyButtons();
                view.selectTownGUI();
                numInput = 2;
                indexInput = 4;
                break;

            case "Vote":
                view.disableKeyButtons();
                view.selectTownGUI();
                numInput = 1;
                indexInput = 5;
                break;

            case "Vote all":
                //if (model.getVisitedTown() == null) break;
                model.electionForAllTowns();
                view.printAllVotes(model.getMyOrganization());
                model.closeAllELection();
                break;
            default:
                if (numInput != 0) {
                    this.input = view.scanInput();
                    switch (indexInput) {
                        case 0:
                            model.setMyOrganization(this.input.getFirst());
                            view.OrganizationCreatedAlert();
                            break;

                        case 1:
                            if (view.getCheckBoxName().equals("Add")) {
                                view.report(model.addNewState(input.getFirst()));
                            } else if (view.getCheckBoxName().equals("Delete")) {
                                view.report(model.deleteState(input.getFirst()));
                            }
                            view.printStateAndTowns(model.getMyOrganization());
                            view.emptyInput();
                            view.activateKeyButtons();
                            break;

                        case 2:
                            //---get state
                            if (numInput == 2) {
                                view.disableKeyButtons();
                                view.report(model.findState(input.getFirst()));
                                view.emptyInput();
                                if (model.getVisitedState() == null) {
                                    view.activateKeyButtons();
                                    break;
                                }
                                numInput--;
                                view.selectTownGUI();

                                //---get town
                            } else if (numInput < 2) {
                                this.input = view.scanInput();

                                if (view.getCheckBoxName().equals("Add")) {
                                    view.report(model.addNewTown(input.getFirst()));
                                } else if (view.getCheckBoxName().equals("Delete")) {
                                    view.report(model.deleteTown(input.getFirst()));
                                }
                                view.printStateAndTowns(model.getMyOrganization());
                                view.activateKeyButtons();
                            }
                            view.emptyInput();
                            break;
                        case 3:
                            view.disableKeyButtons();
                            switch (numInput) {
                                case 5:
                                    view.report(model.findTown(input.getFirst()));
                                    if (model.getVisitedState() == null) {
                                        view.activateKeyButtons();
                                        break;
                                    }
                                    if (view.getCheckBoxName().equals("Delete")) {
                                        view.printDaysAndMeets(model.deleteMeeting(input.getFirst()), model.getMyOrganization());
                                        view.emptyInput();
                                        view.activateKeyButtons();
                                        break;
                                    }
                                    view.setStartDateGUI();
                                    numInput--;
                                    break;
                                case 4:
                                    view.setEndDateGUI();
                                    numInput--;
                                    break;

                                case 3:
                                    view.setDurationGUI();
                                    numInput--;
                                    break;
                                case 2:
                                    view.setCapacityGUI();
                                    numInput--;
                                    break;
                                case 1:
                                    view.printDaysAndMeets(model.createMeeting(this.input), model.getMyOrganization());
                                    view.emptyInput();
                                    view.activateKeyButtons();
                                    break;
                            }
                            break;
                        case 4:
                            if (numInput == 2) {
                                view.report(model.findTown(input.getFirst()));
                                numInput--;
                                if (model.getVisitedTown() == null) {
                                    view.emptyInput();
                                    view.activateKeyButtons();
                                    break;
                                }
                                view.selectUsersGUI();
                            } else {
                                this.input = view.scanInput();
                                if (view.getCheckBoxName().equals("Add")) {
                                    view.report(model.addUsers(input.get(1)));
                                } else if (view.getCheckBoxName().equals("Delete")) {
                                    view.report(model.deleteUsers(input.get(1)));
                                }
                                view.printStateAndTowns(model.getMyOrganization());
                                view.emptyInput();
                                view.activateKeyButtons();
                            }
                            break;
                        case 5:
                            this.input = view.scanInput();
                            view.report(model.findTown(input.getFirst()));
                            view.report(model.makeElection());
                            view.printVotes(model.getVisitedTown());
                            model.closeElection();
                            view.emptyInput();
                            view.activateKeyButtons();
                            numInput--;
                            break;
                    }
                }
        }
    }
}

