package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;
public class mainApp extends Application {

	@Override
	public void start(Stage stage) {
	    LoginView.show(stage);
	}


    public static void main(String[] args) {
        launch(args);
    }
}
