package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Reservation;
import repository.ReservationRepository;

public class ModifierEmpruntView {

    public static void show(Stage stage, Reservation reservation, ObservableList<Reservation> tableData) {

        Label titre = new Label("Modifier une réservation");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Modifiez les informations de l'emprunt sélectionné");
        sousTitre.setFont(Font.font("Segoe UI", 12));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(4, 0, 4, 0));

        TextField tfDomaine     = styledField(reservation.getDomaine());
        TextField tfRessource   = styledField(reservation.getRessource());
        TextField tfDescription = styledField(reservation.getDescription());
        TextField tfHeure       = styledField(reservation.getHeureDuree());

        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("Reservation", "Emprunt");
        cbType.setValue(reservation.getType());
        cbType.setMaxWidth(Double.MAX_VALUE);
        cbType.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;"
        );

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(12);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        grid.addRow(0, fieldLabel("Domaine"),      tfDomaine);
        grid.addRow(1, fieldLabel("Ressource"),    tfRessource);
        grid.addRow(2, fieldLabel("Description"),  tfDescription);
        grid.addRow(3, fieldLabel("Heure - Durée"), tfHeure);
        grid.addRow(4, fieldLabel("Type"),         cbType);

        Button btnSave    = new Button("Enregistrer");
        Button btnAnnuler = new Button("Annuler");

        btnSave.setPrefWidth(120);
        btnSave.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        );
        btnSave.setOnMouseEntered(e -> btnSave.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        ));
        btnSave.setOnMouseExited(e -> btnSave.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        ));

        btnAnnuler.setPrefWidth(110);
        btnAnnuler.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-text-fill: #374151;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #d1d5db;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        );
        btnAnnuler.setOnMouseEntered(e -> btnAnnuler.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-text-fill: #111827;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #9ca3af;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        ));
        btnAnnuler.setOnMouseExited(e -> btnAnnuler.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-text-fill: #374151;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #d1d5db;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        ));

        btnAnnuler.setOnAction(e -> stage.close());

        HBox actions = new HBox(10, btnSave, btnAnnuler);
        actions.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(16, titleBlock, sep, grid, actions);
        card.setPadding(new Insets(32, 36, 32, 36));
        card.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);"
        );

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #f3f4f6;");
        root.setPadding(new Insets(32));

        btnSave.setOnAction(e -> {
            try {
                reservation.setDomaine(tfDomaine.getText());
                reservation.setRessource(tfRessource.getText());
                reservation.setDescription(tfDescription.getText());
                reservation.setHeureDuree(tfHeure.getText());
                reservation.setType(cbType.getValue());

                ReservationRepository repo = new ReservationRepository();
                repo.update(reservation);
                tableData.setAll(repo.findAll());

                new Alert(Alert.AlertType.INFORMATION, "Réservation modifiée avec succès").showAndWait();
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification").showAndWait();
            }
        });

        stage.setScene(new Scene(root, 480, 420));
        stage.setTitle("Modifier la réservation");
        stage.setResizable(false);
    }

    private static TextField styledField(String value) {
        TextField tf = new TextField(value);
        tf.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> tf.setStyle(
            "-fx-background-color: " + (newVal ? "#ffffff" : "#f9fafb") + ";" +
            "-fx-border-color: "     + (newVal ? "#4f46e5" : "#e5e7eb") + ";" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        ));
        return tf;
    }

    private static Label fieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        lbl.setTextFill(Color.web("#374151"));
        lbl.setAlignment(Pos.CENTER_LEFT);
        return lbl;
    }
}