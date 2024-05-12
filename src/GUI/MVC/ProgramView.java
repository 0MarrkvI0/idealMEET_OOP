package GUI.MVC;

import GUI.Observer.ButtonClickListener;
import GUI.Observer.ButtonHasClicked;
import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Errors.WrongDateException;
import MeetingSchedule.Organization.MainSystem;
import MeetingSchedule.Organization.State.GlobalSystem;
import MeetingSchedule.Organization.State.Town.Day;
import MeetingSchedule.Organization.State.Town.Meeting;
import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramView implements ButtonHasClicked, IO_Function {
    private List<ButtonClickListener> listOfObservers = new ArrayList<ButtonClickListener>();
    private Stage mainWindow;

    private MyRadioButtons myRadioButtons = new MyRadioButtons();
    private RadioButton[] MainRadioButtons = myRadioButtons.getRadioButtons();

    //--- KEY buttons
    private MyButtons myButtons = new MyButtons();

    public class MyButtons {
        String clickedButton;
        private ArrayList<Button> systemButtons;

        public MyButtons() {
            this.systemButtons = new ArrayList<>();
            initializeButtons();
        }

        public void initializeButtons() {
            systemButtons.add(new Button("Create Organization"));
            systemButtons.add(new Button("State"));
            systemButtons.add(new Button("Town"));
            systemButtons.add(new Button("iMeet"));
            systemButtons.add(new Button("User"));
            systemButtons.add(new Button("Vote"));
            systemButtons.add(new Button("Vote all"));
        }

        public ArrayList<Button> getButtons() {
            return this.systemButtons;
        }

        //-- event handlers
        public void buttonActivation() {
            for (Button button : systemButtons) {
                button.setOnAction(event -> {
                    handleButtonPress(button, myRadioButtons);
                    try {
                        notifyObservers();
                    } catch (DuplicateError | NotFound | WrongDateException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        public void setAllButtonsActive() {
            for (Button button : systemButtons) {
                button.setDisable(false);
            }
        }

        public void setAllButtonsInActive() {
            for (Button button : systemButtons) {
                button.setDisable(true);
            }
        }

        public void deleteMainButton() {
            this.systemButtons.removeFirst();
            initializeGUI();
        }
    }

    private Button enterButton = new Button("Enter");

    public class MyRadioButtons {
        String ClickedBtn;
        private ToggleGroup toggleGroup = new ToggleGroup();
        private final RadioButton[] radioButtons = {
                new RadioButton("Add"),
                new RadioButton("Delete"),
                new RadioButton("None")
        };

        public RadioButton[] getRadioButtons() {
            return this.radioButtons;
        }

        public void setupRadioButtons() {
            for (RadioButton radioButton : radioButtons) {
                radioButton.setToggleGroup(toggleGroup);
            }
            radioButtons[2].setSelected(true);
        }
    }

    private TextField Input = new TextField();
    private ArrayList<String> InputArray = new ArrayList<>();

    private TextArea generalOutput = new TextArea();
    private TextArea branchOutput = new TextArea();
    protected TextArea meetingOutput = new TextArea();


    private FlowPane pane = new FlowPane();
    private ScrollPane scroll = new ScrollPane();
    private VBox mainContainer;
    private HBox outputsContainer;
    FlowPane inputContainer;

    private String buttonName;
    private String CheckBoxName;


    public ProgramView(Stage mainStage, boolean StartMethod) {
        this.mainWindow = mainStage;
        myRadioButtons.setupRadioButtons();
        initializeGUI();
        setupGUI();

        if (!StartMethod) {
            deleteMainButton();
        }
        enterButton.setOnAction(event -> {
            handleButtonPress(enterButton, myRadioButtons);
            try {
                notifyObservers();
            } catch (DuplicateError | NotFound | WrongDateException e) {
                throw new RuntimeException(e);
            }
        });
        mainStage.setOnCloseRequest(event -> {
            buttonName = "X";
            try {
                notifyObservers();
            } catch (DuplicateError | NotFound | WrongDateException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Window is closing...");
        });
        myButtons.buttonActivation();
        setEnterButtonInActive();
    }


    public ArrayList<Button> getButtons() {
        ArrayList<Button> allButtons = new ArrayList<>();
        allButtons.add(enterButton);
        allButtons.addAll(myButtons.systemButtons);
        return allButtons;
    }

    public ArrayList<RadioButton> getRadioButtons() {
        ArrayList<RadioButton> allRadioButtons = new ArrayList<>(Arrays.asList(MainRadioButtons));
        return allRadioButtons;
    }

    //--- create GUI

    private void initializeGUI() {
        mainContainer = createMainContainer();
        outputsContainer = createOutputsContainer();
        inputContainer = createInputContainer();

        mainContainer.getChildren().addAll(createButtonsPane(), createRadioPane(), outputsContainer, inputContainer);

        scroll.setContent(mainContainer);
    }

    private VBox createMainContainer() {
        mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(10);
        mainContainer.setPrefWidth(598);
        mainContainer.setPadding(new Insets(10));
        return mainContainer;
    }

    private FlowPane createButtonsPane() {
        FlowPane buttonsPane = new FlowPane();
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setHgap(5);
        buttonsPane.setPadding(new Insets(10));
        buttonsPane.getChildren().addAll(myButtons.getButtons());
        return buttonsPane;
    }

    private FlowPane createRadioPane() {
        FlowPane radioPane = new FlowPane();
        radioPane.setAlignment(Pos.CENTER);
        radioPane.setHgap(10);
        radioPane.setPadding(new Insets(0, 0, 10, 0));
        radioPane.getChildren().addAll(myRadioButtons.getRadioButtons());
        return radioPane;
    }

    private HBox createOutputsContainer() {
        outputsContainer = new HBox();
        outputsContainer.setAlignment(Pos.CENTER);
        outputsContainer.setSpacing(10);

        generalOutput.setPrefWidth(285);
        generalOutput.setPrefHeight(200);
        branchOutput.setPrefWidth(285);
        branchOutput.setPrefHeight(200);
        branchOutput.setWrapText(true);
        generalOutput.setWrapText(true);

        outputsContainer.getChildren().addAll(generalOutput, branchOutput);
        addNewTab();
        return outputsContainer;
    }

    private FlowPane createInputContainer() {
        inputContainer = new FlowPane();
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setPrefWidth(200);
        inputContainer.setPadding(new Insets(5));
        inputContainer.getChildren().addAll(Input, enterButton);
        return inputContainer;
    }

    //--- setup GUI

    private void setupGUI() {
        mainWindow.setTitle("idealMeet");
        mainWindow.setScene(new Scene(scroll, 600, 400));
        mainWindow.show();
    }

    public void addNewTab() {
        generalOutput.setPrefWidth(200);
        branchOutput.setPrefWidth(150);
        meetingOutput.setPrefWidth(200);
        meetingOutput.setWrapText(true);
        outputsContainer.getChildren().add(meetingOutput);
    }
    //--- buttons set-up

    public void setMyButtonsActive() {
        this.myButtons.setAllButtonsActive();
    }

    public void setMyButtonsInActive() {
        this.myButtons.setAllButtonsInActive();
    }

    //--- enter set-up
    public void setEnterButtonActive() {
        this.enterButton.setDisable(false);
    }

    public void setEnterButtonInActive() {
        this.enterButton.setDisable(true);
    }

    //--- key activation

    void disableKeyButtons() {
        setMyButtonsInActive();
        setEnterButtonActive();
    }

    void activateKeyButtons() {
        setMyButtonsActive();
        setEnterButtonInActive();
    }

    //--- button handling

    public String getButtonName() {
        return this.buttonName;
    }

    public String getCheckBoxName() {
        return this.CheckBoxName;
    }

    // when button is pressed, set button and checkbox name
    private void handleButtonPress(Button button, MyRadioButtons myRadioButtons) {
        this.buttonName = button.getText();
        for (RadioButton radioButton : this.getRadioButtons()) {
            if (radioButton.isSelected()) {
                this.CheckBoxName = radioButton.getText();
            }
        }
    }

    // --- observer pattern

    @Override
    public void registerObserver(ButtonClickListener observer) {
        listOfObservers.add(observer);
    }

    @Override
    public void removeObserver(ButtonClickListener observer) {
        listOfObservers.remove(observer);
    }

    @Override
    public void notifyObservers() throws DuplicateError, NotFound, WrongDateException {
        for (ButtonClickListener buttonClickListener : listOfObservers) {
            buttonClickListener.update();

        }
    }

    //--- intro report

    public void selectOrganizationGUI() {
        this.setMessage(generalOutput, "Please enter your organization name:\n");
        this.setMessageToInput(Input, "organization name");
    }

    public void selectStateGUI() {
        this.setMessage(generalOutput, "Please enter state:\n");
        this.setMessageToInput(Input, "state");
    }

    public void selectTownGUI() {
        this.setMessage(generalOutput, "Please enter town:\n");
        this.setMessageToInput(Input, "town");
    }

    public void selectUsersGUI() {
        this.setMessage(generalOutput, "Please enter number of all voters:\n");
        this.setMessageToInput(Input, "users");
    }

    //--- general report

    public void report(String message) {
        this.setMessage(generalOutput, message);
    }

    //--- iMeet input messages

    public void setStartDateGUI() {
        this.setMessage(generalOutput, "Please enter START DATE:\n");
        this.setMessageToInput(Input, "start date");
    }

    public void setEndDateGUI() {
        this.setMessage(generalOutput, "Please enter END DATE:\n");
        this.setMessageToInput(Input, "end date");
    }

    public void setDurationGUI() {
        this.setMessage(generalOutput, "Please enter DURATION of meeting:\n");
        this.setMessageToInput(Input, "duration");
    }

    public void setCapacityGUI() {
        this.setMessage(generalOutput, "Please enter CAPACITY of meeting:\n-----\n");
        this.setMessageToInput(Input, "capacity");
    }

    //--- input handling

    public ArrayList<String> scanInput() {
        return this.getMessage(Input, InputArray);
    }

    public void emptyInput() {
        this.InputArray.clear();
    }

    // action performed alerts

    void OrganizationCreatedAlert() {
        this.setMessage(generalOutput, STR."System \{InputArray.getFirst()} was successfully created.\n-----\n");
        this.setMessage(branchOutput, STR."\{InputArray.getFirst()}\n-----\n");
        deleteMainButton();
        emptyInput();
        activateKeyButtons();
    }

    public void deleteMainButton() {
        myButtons.deleteMainButton();
    }


    public void printStateAndTowns(MainSystem organization) {
        int index = 0;
        this.setMessage(branchOutput, organization.getName() + '\n');
        for (GlobalSystem state : organization.getListOfStates()) {
            int index2 = 0;
            this.setMessage(branchOutput, STR."\{index}: \{state.getStateName()}\n");
            for (TimeTableBoard town : state.getListOfTowns()) {
                if (town == null) break;
                this.setMessage(branchOutput, STR."- \{index2}: \{town.getTown()}\n");
                int user_index = 1;
                for (User user : town.getUsers()) {
                    this.setMessage(branchOutput, STR."\{user_index}-\{user.getUserID()}\{'\n'}");
                    user_index++;
                }
                index2++;
            }
            index++;
        }
        this.setMessage(branchOutput, "-----\n");
    }

    public void printDaysAndMeets(String output, MainSystem organization) {
        this.setMessage(meetingOutput, organization.getName() + '\n');
        for (GlobalSystem state : organization.getListOfStates())
            if (state.getListOfTowns() != null) {
                for (TimeTableBoard town : state.getListOfTowns()) {
                    if (town.getIdealDay() != null) {
                        this.setMessage(meetingOutput, STR."\{state.getStateName()}:\n");
                        this.setMessage(meetingOutput, STR."\{town.getTown()}\n");
                        for (Day day : town.getIdealDay()) {
                            this.setMessage(meetingOutput, day.getDate() + '\n');
                            for (Meeting meeting : day.getIdealMEETs()) {
                                int endTime = meeting.getStartTime() + meeting.getDuration();
                                this.setMessage(meetingOutput, STR."-> \{meeting.getStartTime()}-\{endTime} \{meeting.getCapacity()}" + '\n');
                            }
                        }
                    }

                }
            }
        this.setMessage(meetingOutput, "-----\n");
    }


    public void printAllUsers(TimeTableBoard town) {
        int user_index = 1;
        for (User user : town.getUsers()) {
            this.setMessage(branchOutput, STR."\{user_index}-\{user.getUserID()}\{'\n'}");
            user_index++;
        }
        this.setMessage(branchOutput, "-----\n");
    }

    public void printVotes(TimeTableBoard town) {
        for (Day day : town.getIdealDay()) {
            int index = 1;
            this.setMessage(meetingOutput, day.getDate()+'\n');
            for (Meeting meeting : day.getIdealMEETs()) {
                int EndTime = meeting.getStartTime() + meeting.getDuration();
                this.setMessage(meetingOutput, STR."\{index}. \{meeting.getStartTime()}-\{EndTime}\{' '}\{meeting.getCapacity()} YES(\{meeting.getNumberOfMembers()})\n");
                for (String user : meeting.getvotedUsers()) {
                    this.setMessage(meetingOutput, user + '\n');
                }
                index++;
            }
        }
    }

    public void printAllVotes(MainSystem organization) {
        for (GlobalSystem state : organization.getListOfStates()) {
            this.setMessage(meetingOutput,state.getStateName()+'\n');
            for (TimeTableBoard town : state.getListOfTowns()) {
                this.setMessage(meetingOutput,town.getTown()+'\n');
                printVotes(town);
                this.setMessage(meetingOutput,"\n-----\n");
            }
        }
    }
}
