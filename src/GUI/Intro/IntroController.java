package GUI.Intro;


import GUI.Observer.ButtonClickListener;
import GUI.MVC.ProgramController;
import GUI.MVC.ProgramModel;
import GUI.MVC.ProgramView;
import javafx.stage.Stage;

public class IntroController implements ButtonClickListener {

    private ProgramModel model;
    private IntroView view;
    // launch preferences (T-new,F-continue)
    private boolean FreshStart = true;

    public IntroController(ProgramModel model, IntroView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void update() {
        if (view.getButtonName().equals("Continue")) {
            //load serialized object
            model.loadSerializeObject();
            this.FreshStart = false;
            showMainWindow();
        } else {
            //create new window
            showMainWindow();
        }
    }

    private void showMainWindow() {
        ProgramView mainView = new ProgramView(new Stage(), this.FreshStart);
        if (!FreshStart) {
            mainView.report(STR."Welcome back \{model.getMyOrganizationName()}\n-----\n");
            mainView.printStateAndTowns(model.getMyOrganization());
            mainView.printDaysAndMeets(model.getMyOrganizationName(), model.getMyOrganization());
            model.setFormatter();
        }
        ProgramController mainController = new ProgramController(model, mainView, this.FreshStart);
        mainView.registerObserver(mainController);
    }
}
