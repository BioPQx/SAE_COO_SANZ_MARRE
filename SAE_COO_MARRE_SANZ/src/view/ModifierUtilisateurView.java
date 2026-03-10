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
import model.Utilisateur;
import repository.AuthentificationRepository;
import repository.UtilisateurRepository;
import java.io.IOException;

public class ModifierUtilisateurView {

    public static void show(Stage stage, Utilisateur utilisateur, ObservableList<Utilisateur> tableItems) {

        Label titre = new Label("Modifier un utilisateur");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Modifiez les informations du compte sélectionné");
        sousTitre.setFont(Font.font("Segoe UI", 12));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(4, 0, 4, 0));

        TextField tfId = styledField(String.valueOf(utilisateur.getId()));
        tfId.setDisable(true);
        tfId.setStyle(
            "-fx-background-color: #f3f4f6;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #9ca3af;"
        );

        TextField tfNom    = styledField(utilisateur.getNom());
        TextField tfPrenom = styledField(utilisateur.getPrenom());
        TextField tfLogin  = styledField(utilisateur.getLogin());
        TextField tfNiveau = styledField(String.valueOf(utilisateur.getNiveauAutorisation()));

        CheckBox cbActif = new CheckBox("Actif");
        cbActif.setSelected(utilisateur.isActif());
        cbActif.setFont(Font.font("Segoe UI", 13));
        cbActif.setTextFill(Color.web("#374151"));
        cbActif.setStyle("-fx-cursor: hand;");

        DatePicker dpDateCreation = new DatePicker(utilisateur.getDateCreation());
        dpDateCreation.setDisable(true);
        dpDateCreation.setMaxWidth(Double.MAX_VALUE);
        dpDateCreation.setStyle(
            "-fx-background-color: #f3f4f6;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;" +
            "-fx-opacity: 0.7;"
        );

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(12);

        ColumnConstraints col1 = new ColumnConstraints(160);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        grid.addRow(0, fieldLabel("ID (non modifiable)"),   tfId);
        grid.addRow(1, fieldLabel("Nom"),                   tfNom);
        grid.addRow(2, fieldLabel("Prénom"),                tfPrenom);
        grid.addRow(3, fieldLabel("Login"),                 tfLogin);
        grid.addRow(4, fieldLabel("Niveau d'autorisation"), tfNiveau);
        grid.addRow(5, fieldLabel("Statut"),                cbActif);
        grid.addRow(6, fieldLabel("Date de création"),      dpDateCreation);

        Button btnEnregistrer = new Button("Enregistrer");
        Button btnResetPwd    = new Button("Réinitialiser le mot de passe");
        Button btnAnnuler     = new Button("Annuler");

        btnEnregistrer.setPrefWidth(130);
        btnEnregistrer.setStyle(primaryBase());
        btnEnregistrer.setOnMouseEntered(e -> btnEnregistrer.setStyle(primaryHover()));
        btnEnregistrer.setOnMouseExited(e  -> btnEnregistrer.setStyle(primaryBase()));

        btnAnnuler.setPrefWidth(110);
        btnAnnuler.setStyle(secondaryBase());
        btnAnnuler.setOnMouseEntered(e -> btnAnnuler.setStyle(secondaryHover()));
        btnAnnuler.setOnMouseExited(e  -> btnAnnuler.setStyle(secondaryBase()));
        btnAnnuler.setOnAction(e -> stage.close());

        btnResetPwd.setStyle(
            "-fx-background-color: #fef2f2;" +
            "-fx-text-fill: #dc2626;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 9 16;" +
            "-fx-cursor: hand;"
        );
        btnResetPwd.setOnMouseEntered(e -> btnResetPwd.setStyle(
            "-fx-background-color: #fee2e2;" +
            "-fx-text-fill: #b91c1c;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 9 16;" +
            "-fx-cursor: hand;"
        ));
        btnResetPwd.setOnMouseExited(e -> btnResetPwd.setStyle(
            "-fx-background-color: #fef2f2;" +
            "-fx-text-fill: #dc2626;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 9 16;" +
            "-fx-cursor: hand;"
        ));

        HBox mainActions = new HBox(10, btnEnregistrer, btnAnnuler);
        mainActions.setAlignment(Pos.CENTER_RIGHT);

        Separator sepActions = new Separator();
        sepActions.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sepActions, new Insets(4, 0, 4, 0));

        HBox resetRow = new HBox(btnResetPwd);
        resetRow.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(16, titleBlock, sep, grid, mainActions, sepActions, resetRow);
        card.setPadding(new Insets(32, 36, 32, 36));
        card.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);"
        );

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #f3f4f6;");
        root.setPadding(new Insets(32));

        btnEnregistrer.setOnAction(e -> {
            utilisateur.setNom(tfNom.getText().trim());
            utilisateur.setPrenom(tfPrenom.getText().trim());
            utilisateur.setLogin(tfLogin.getText().trim());
            utilisateur.setNiveauAutorisation(Integer.parseInt(tfNiveau.getText().trim()));
            utilisateur.setActif(cbActif.isSelected());

            UtilisateurRepository repo = new UtilisateurRepository();
            try {
                if (repo.loginExiste(tfLogin.getText().trim(), Integer.parseInt(tfId.getText().trim()))) {
                    new Alert(Alert.AlertType.WARNING, "Ce login existe déjà !").showAndWait();
                    return;
                }
            } catch (NumberFormatException | IOException e1) {
                e1.printStackTrace();
            }
            try {
                new UtilisateurRepository().update(utilisateur);
                if (tableItems != null) {
                    int index = tableItems.indexOf(utilisateur);
                    if (index >= 0) tableItems.set(index, utilisateur);
                }
                stage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour !").showAndWait();
            }
        });

        btnResetPwd.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Réinitialiser le mot de passe");
            confirm.setHeaderText("Réinitialiser le mot de passe de " + utilisateur.getNom() + " ?");
            confirm.setContentText("Le mot de passe sera remis à 'Iut2026'.");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        new AuthentificationRepository().updatePassword(utilisateur.getId(), "Iut2026");
                        new Alert(Alert.AlertType.INFORMATION,
                                "Mot de passe réinitialisé pour " + utilisateur.getLogin()).showAndWait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        new Alert(Alert.AlertType.ERROR,
                                "Erreur lors de la réinitialisation du mot de passe !").showAndWait();
                    }
                }
            });
        });

        stage.setScene(new Scene(root, 500, 580));
        stage.setTitle("Modifier l'utilisateur");
        stage.setResizable(false);
        stage.show();
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

    private static String primaryBase() {
        return "-fx-background-color: #4f46e5;" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 13px;" +
               "-fx-font-weight: bold;" +
               "-fx-font-family: 'Segoe UI';" +
               "-fx-background-radius: 6;" +
               "-fx-padding: 9 0;" +
               "-fx-cursor: hand;";
    }

    private static String primaryHover() {
        return "-fx-background-color: #4338ca;" +
               "-fx-text-fill: white;" +
               "-fx-font-size: 13px;" +
               "-fx-font-weight: bold;" +
               "-fx-font-family: 'Segoe UI';" +
               "-fx-background-radius: 6;" +
               "-fx-padding: 9 0;" +
               "-fx-cursor: hand;";
    }

    private static String secondaryBase() {
        return "-fx-background-color: #ffffff;" +
               "-fx-text-fill: #374151;" +
               "-fx-font-size: 13px;" +
               "-fx-font-family: 'Segoe UI';" +
               "-fx-background-radius: 6;" +
               "-fx-border-color: #d1d5db;" +
               "-fx-border-radius: 6;" +
               "-fx-padding: 9 0;" +
               "-fx-cursor: hand;";
    }

    private static String secondaryHover() {
        return "-fx-background-color: #f9fafb;" +
               "-fx-text-fill: #111827;" +
               "-fx-font-size: 13px;" +
               "-fx-font-family: 'Segoe UI';" +
               "-fx-background-radius: 6;" +
               "-fx-border-color: #9ca3af;" +
               "-fx-border-radius: 6;" +
               "-fx-padding: 9 0;" +
               "-fx-cursor: hand;";
    }
}