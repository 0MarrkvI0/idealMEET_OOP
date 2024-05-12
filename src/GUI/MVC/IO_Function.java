package GUI.MVC;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

interface IO_Function {

    //-- default method implementation
    default ArrayList<String> getMessage(TextField input, ArrayList<String> messages) {
        messages.add(input.getText());
        input.clear();
        return messages;
    }

    default void setMessage(TextArea output, String message) {
        output.appendText(message);
    }

    default void setMessageToInput(TextField input, String message) {
        input.setPromptText(message);
    }
}