package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StockView {

    public static VBox create() {
        VBox box = new VBox(10);
        box.getChildren().add(new Label("Gestion du stock"));
        return box;
    }
}
