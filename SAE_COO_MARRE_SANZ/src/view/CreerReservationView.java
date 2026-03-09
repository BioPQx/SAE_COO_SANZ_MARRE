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
        TextField tfHeureDuree = new TextField();
        TextField tfType = new TextField();
        TextField tfMaj = new TextField();

        grid.addRow(0, new Label("Id :"), tfId);
        grid.addRow(1, new Label("Domaine :"), tfDomaine);
        grid.addRow(2, new Label("Ressource :"), tfRessource);
        grid.addRow(3, new Label("Description :"), tfDescription);
        grid.addRow(4, new Label("Heure - Durée :"), tfHeureDuree);
        grid.addRow(5, new Label("Type :"), tfType);
        grid.addRow(6, new Label("Dernière mise à jour :"), tfMaj);

        // ---- Boutons ----
        Button btnCreer = new Button("Créer");
        Button btnAnnuler = new Button("Annuler");

        HBox actions = new HBox(10, btnCreer, btnAnnuler);

        VBox root = new VBox(15, titre, grid, actions);
        root.setPadding(new Insets(20));

        // ---- Actions ----
        btnAnnuler.setOnAction(e -> stage.close());

        btnCreer.setOnAction(e -> {

            // Vérifier que tous les champs sont remplis
            if (tfId.getText().trim().isEmpty()
            		|| tfDomaine.getText().trim().isEmpty()
                    || tfRessource.getText().trim().isEmpty()
                    || tfDescription.getText().trim().isEmpty()
                    || tfHeureDuree.getText().trim().isEmpty()
                    || tfType.getText().trim().isEmpty()
                    || tfMaj.getText().trim().isEmpty()) {

                new Alert(Alert.AlertType.WARNING,
                        "Tous les champs sont obligatoires.")
                        .showAndWait();
                return;
            }

            try {
                int id;

                try {
                    id = Integer.parseInt(tfId.getText().trim());
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.WARNING,
                            "L'ID doit être un nombre.")
                            .showAndWait();
                    return;
                }

                ReservationRepository repo = new ReservationRepository();

                Reservation reservation = new Reservation(
                        id,
                        tfDomaine.getText().trim(),
                        tfRessource.getText().trim(),
                        tfDescription.getText().trim(),
                        tfHeureDuree.getText().trim(),
                        tfType.getText().trim(),
                        tfMaj.getText().trim()
                );

                // Sauvegarder dans le CSV
                repo.save(reservation);

                // Ajouter à la liste observable pour mise à jour UI
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