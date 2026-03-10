package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView {

    public static void show(Stage stage) {

        BorderPane root = new BorderPane();

        // Bandeau haut
        root.setTop(TopBarView.create(stage)); 

        // Menu gauche
        root.setLeft(MenuView.createMenu(root));

        // Accueil par défaut
        root.setCenter(AccueilView.create());

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("Application");
        stage.show();
    }
}
