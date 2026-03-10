package view;

import model.Reservation;
import repository.ReservationRepository;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreerReservationView {

    public static void show(Stage stage, ObservableList<Reservation> reservations) {

        Label titre = new Label("Créer une réservation");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // ---- Formulaire ----
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfId = new TextField();
        TextField tfDomaine = new TextField();
        TextField tfRessource = new TextField();
        TextField tfDescription = new TextField();

        // ComboBox pour Type
        ComboBox<String> cbType = new ComboBox<>();
        cbType.getItems().addAll("En cours", "Emprunt");

        grid.addRow(0, new Label("Id :"), tfId);
        grid.addRow(1, new Label("Domaine :"), tfDomaine);
        grid.addRow(2, new Label("Ressource :"), tfRessource);
        grid.addRow(3, new Label("Description :"), tfDescription);
        grid.addRow(4, new Label("Type :"), cbType);

        // ---- Boutons ----
        Button btnCreer = new Button("Créer");
        Button btnAnnuler = new Button("Annuler");

        HBox actions = new HBox(10, btnCreer, btnAnnuler);

        VBox root = new VBox(15, titre, grid, actions);
        root.setPadding(new Insets(20));

        btnAnnuler.setOnAction(e -> stage.close());

        btnCreer.setOnAction(e -> {

            if (tfId.getText().trim().isEmpty()
                    || tfDomaine.getText().trim().isEmpty()
                    || tfRessource.getText().trim().isEmpty()
                    || tfDescription.getText().trim().isEmpty()
                    || cbType.getValue() == null) {

                new Alert(Alert.AlertType.WARNING,
                        "Tous les champs sont obligatoires.")
                        .showAndWait();
                return;
            }

            int id;

            try {
                id = Integer.parseInt(tfId.getText().trim());
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.WARNING,
                        "L'ID doit être un nombre.")
                        .showAndWait();
                return;
            }

            try {

                ReservationRepository repo = new ReservationRepository();

                // date du jour
                String today = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Reservation reservation = new Reservation(
                        id,
                        tfDomaine.getText().trim(),
                        tfRessource.getText().trim(),
                        tfDescription.getText().trim(),
                        today,                // HeureDurée = date du jour
                        cbType.getValue(),    // Type depuis ComboBox
                        today                 // Dernière MAJ = date du jour
                );

                repo.save(reservation);

                reservations.add(reservation);

                new Alert(Alert.AlertType.INFORMATION,
                        "Réservation créée avec succès !")
                        .showAndWait();

                stage.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                        "Erreur lors de la création de la réservation.")
                        .showAndWait();
            }
        });

        stage.setScene(new Scene(root));
        stage.setResizable(false);
    }
}