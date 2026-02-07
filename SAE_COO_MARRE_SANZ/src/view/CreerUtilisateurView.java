package view;

import model.Utilisateur;
import repository.AuthentificationRepository;
import repository.UtilisateurRepository;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.collections.ObservableList;

import java.io.IOException;

public class CreerUtilisateurView {

    public static void show(Stage stage, ObservableList<Utilisateur> users) {

        Label titre = new Label("Créer un utilisateur");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // ---- Formulaire ----
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfNom = new TextField();
        TextField tfPrenom = new TextField();
        TextField tfLogin = new TextField();
        TextField tfniveau = new TextField();
        CheckBox cbActif = new CheckBox("Actif");
        cbActif.setSelected(true);

        grid.addRow(0, new Label("Nom :"), tfNom);
        grid.addRow(1, new Label("Prénom :"), tfPrenom);
        grid.addRow(2, new Label("Login :"), tfLogin);
        grid.addRow(3, new Label("Niveau d'autorisation : "), tfniveau);
        grid.addRow(4, new Label(""), cbActif);

        // ---- Boutons ----
        Button btnCreer = new Button("Créer");
        Button btnAnnuler = new Button("Annuler");

        HBox actions = new HBox(10, btnCreer, btnAnnuler);

        VBox root = new VBox(15, titre, grid, actions);
        root.setPadding(new Insets(20));

        // ---- Actions ----
        btnAnnuler.setOnAction(e -> stage.close());

        btnCreer.setOnAction(e -> {

            if (tfNom.getText().trim().isEmpty()
                    || tfPrenom.getText().trim().isEmpty()
                    || tfLogin.getText().trim().isEmpty()
                    || tfniveau.getText().trim().isEmpty()) {

                new Alert(Alert.AlertType.WARNING,
                        "Tous les champs sont obligatoires.")
                        .showAndWait();
                return;
            }

            int niveau;
            try {
                niveau = Integer.parseInt(tfniveau.getText().trim());
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.WARNING,
                        "Le niveau d'autorisation doit être un nombre.")
                        .showAndWait();
                return;
            }

            try {
                UtilisateurRepository repo = new UtilisateurRepository();

                int nextId = repo.getNextId(); 
                
                if (repo.loginExiste(tfLogin.getText().trim(), nextId)) {
                    new Alert(Alert.AlertType.WARNING,
                            "Ce login est déjà utilisé.")
                            .showAndWait();
                    return;
                }

                Utilisateur utilisateur = new Utilisateur(
                        nextId,
                        tfNom.getText().trim(),
                        tfPrenom.getText().trim(),
                        tfLogin.getText().trim(),
                        niveau,
                        cbActif.isSelected(),
                        java.time.LocalDate.now()
                );

                repo.save(utilisateur);
                
                new AuthentificationRepository()
                .createForUser(utilisateur.getId(), utilisateur.getLogin());
                
                users.add(utilisateur);

                new Alert(Alert.AlertType.INFORMATION,
                        "Utilisateur créé avec succès !")
                        .showAndWait();

                stage.close();

            } catch (IOException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                        "Erreur lors de la création de l'utilisateur.")
                        .showAndWait();
            }
        });

        stage.setScene(new Scene(root));
        stage.setResizable(false);
    }
}
