package view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class AccueilView {

    public static StackPane create() {
        StackPane pane = new StackPane();
        pane.getChildren().add(new Label("Bienvenue dans l'application"));
        return pane;
    }
}
