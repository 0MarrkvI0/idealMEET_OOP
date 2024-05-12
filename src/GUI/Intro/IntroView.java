package GUI.Intro;

import GUI.Observer.ButtonClickListener;
import GUI.Observer.ButtonHasClicked;
import MeetingSchedule.Errors.DuplicateError;
import MeetingSchedule.Errors.NotFound;
import MeetingSchedule.Errors.WrongDateException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class IntroView implements ButtonHasClicked {
    private final Button startButton = new Button("New");
    private final Button continueButton = new Button("Continue");
    private final VBox layout;
    private final Stage introWindow;

    private List<ButtonClickListener> listOfObservers = new ArrayList<ButtonClickListener>();
    private String buttonName;

    public String getButtonName() {
        return this.buttonName;
    }


    public IntroView() {
        layout = new VBox(10);

        Label greetingLabel = new Label("Hello, welcome to idealMEET");

        startButton.setPrefSize(80, 25);
        continueButton.setPrefSize(80, 25);

        startButton.setOnAction(e -> handleButtonPress(startButton.getText()));
        continueButton.setOnAction(e -> handleButtonPress(continueButton.getText()));

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(greetingLabel, startButton, continueButton);

        introWindow = new Stage();
        introWindow.setTitle("Introduction");
        introWindow.setScene(new Scene(layout, 600, 400));
        introWindow.show();
    }

    private void handleButtonPress(String buttonName) {
        this.buttonName = buttonName;
        // notify observer that button has been pressed
        notifyObservers();
        introWindow.close();
    }

    @Override
    public void registerObserver(ButtonClickListener observer) {
        listOfObservers.add(observer);
    }

    @Override
    public void removeObserver(ButtonClickListener observer) {
        listOfObservers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (ButtonClickListener buttonClickListener : listOfObservers) {
            try {
                buttonClickListener.update();
            } catch (DuplicateError | NotFound | WrongDateException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
