package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatistiquesView {

    public static VBox create() {
        VBox box = new VBox(10);
        box.getChildren().add(new Label("Statistiques"));
        return box;
    }
}
