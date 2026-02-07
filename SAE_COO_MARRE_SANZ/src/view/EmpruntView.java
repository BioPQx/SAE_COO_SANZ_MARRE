package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EmpruntView {

    public static VBox create() {
        VBox box = new VBox(10);
        box.getChildren().add(new Label("Gestion des emprunts"));
        return box;
    }
}
