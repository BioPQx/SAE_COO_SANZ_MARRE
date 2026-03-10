package view;

import model.Reservation;
import repository.ReservationRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreerEmpruntView {

    public static void show(Stage stage, ObservableList<Reservation> reservations) {

        Label titre = new Label("Créer une réservation");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Remplissez les champs pour enregistrer une nouvelle réservation");
        sousTitre.setFont(Font.font("Segoe UI", 12));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(4, 0, 4, 0));

        TextField tfId          = styledField("Identifiant numérique");
        TextField tfDomaine     = styledField("Domaine");
        TextField tfRessource   = styledField("Ressource");
        TextField tfDescription = styledField("Description");

        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("En cours", "Emprunt");
        cbType.setPromptText("Sélectionner un type");
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

        grid.addRow(0, fieldLabel("ID"),          tfId);
        grid.addRow(1, fieldLabel("Domaine"),      tfDomaine);
        grid.addRow(2, fieldLabel("Ressource"),    tfRessource);
        grid.addRow(3, fieldLabel("Description"),  tfDescription);
        grid.addRow(4, fieldLabel("Type"),         cbType);

        ColumnConstraints col1 = new ColumnConstraints(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        Button btnCreer   = new Button("Créer");
        Button btnAnnuler = new Button("Annuler");

        btnCreer.setPrefWidth(110);
        btnCreer.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        );
        btnCreer.setOnMouseEntered(e -> btnCreer.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        ));
        btnCreer.setOnMouseExited(e -> btnCreer.setStyle(
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

        HBox actions = new HBox(10, btnCreer, btnAnnuler);
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

        btnAnnuler.setOnAction(e -> stage.close());

        btnCreer.setOnAction(e -> {
            if (tfId.getText().trim().isEmpty()
                    || tfDomaine.getText().trim().isEmpty()
                    || tfRessource.getText().trim().isEmpty()
                    || tfDescription.getText().trim().isEmpty()
                    || cbType.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Tous les champs sont obligatoires.").showAndWait();
                return;
            }
            int id;
            try {
                id = Integer.parseInt(tfId.getText().trim());
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.WARNING, "L'ID doit être un nombre.").showAndWait();
                return;
            }
            try {
                ReservationRepository repo = new ReservationRepository();
                String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Reservation reservation = new Reservation(
                        id,
                        tfDomaine.getText().trim(),
                        tfRessource.getText().trim(),
                        tfDescription.getText().trim(),
                        today,
                        cbType.getValue(),
                        today
                );
                repo.save(reservation);
                reservations.add(reservation);
                new Alert(Alert.AlertType.INFORMATION, "Réservation créée avec succès !").showAndWait();
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la création de la réservation.").showAndWait();
            }
        });

        stage.setScene(new Scene(root, 480, 420));
        stage.setTitle("Nouvelle réservation");
        stage.setResizable(false);
    }

    private static TextField styledField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
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
            "-fx-border-color: " + (newVal ? "#4f46e5" : "#e5e7eb") + ";" +
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