package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView {

    public static void show(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f3f4f6;");

        root.setTop(TopBarView.create(stage));
        root.setLeft(MenuView.createMenu(root));
        root.setCenter(AccueilView.create());

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("Application");
        stage.show();
    }
}