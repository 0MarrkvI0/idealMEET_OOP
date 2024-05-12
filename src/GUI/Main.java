package GUI;

import GUI.Intro.IntroController;
import GUI.Intro.IntroView;
import GUI.MVC.ProgramModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage mainWindow) {

        //-- MVC pattern
        ProgramModel model = new ProgramModel();
        IntroView view = new IntroView();
        IntroController controller = new IntroController(model, view);
        view.registerObserver(controller);
    }
}
