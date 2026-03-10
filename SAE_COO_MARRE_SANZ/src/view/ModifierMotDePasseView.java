package view;

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
import utils.PasswordUtils;
import java.io.IOException;

public class ModifierMotDePasseView {

    public static void show(Stage stage, Utilisateur user) {

        Label titre = new Label("Modifier le mot de passe");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Saisissez votre mot de passe actuel puis le nouveau");
        sousTitre.setFont(Font.font("Segoe UI", 12));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(4, 0, 4, 0));

        PasswordField pfOld     = styledPassword("Mot de passe actuel");
        PasswordField pfNew     = styledPassword("Nouveau mot de passe");
        PasswordField pfConfirm = styledPassword("Confirmer le nouveau mot de passe");

        VBox fields = new VBox(10,
            fieldBlock("Mot de passe actuel",    pfOld),
            fieldBlock("Nouveau mot de passe",   pfNew),
            fieldBlock("Confirmation",           pfConfirm)
        );

        Button btnEnregistrer = new Button("Enregistrer");
        Button btnAnnuler     = new Button("Annuler");

        btnEnregistrer.setPrefWidth(140);
        btnEnregistrer.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        );
        btnEnregistrer.setOnMouseEntered(e -> btnEnregistrer.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 0;" +
            "-fx-cursor: hand;"
        ));
        btnEnregistrer.setOnMouseExited(e -> btnEnregistrer.setStyle(
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

        HBox actions = new HBox(10, btnEnregistrer, btnAnnuler);
        actions.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(16, titleBlock, sep, fields, actions);
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
            try {
                AuthentificationRepository authRepo = new AuthentificationRepository();
                var auth = authRepo.findByLogin(user.getLogin());
                if (!PasswordUtils.checkPassword(pfOld.getText(), auth.getPasswordHash())) {
                    showError("Mot de passe actuel incorrect");
                    return;
                }
                if (!pfNew.getText().equals(pfConfirm.getText())) {
                    showError("Les nouveaux mots de passe ne correspondent pas");
                    return;
                }
                authRepo.updatePassword(user.getId(), pfNew.getText());
                showInfo("Mot de passe modifié avec succès");
                stage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                showError("Erreur lors de la modification du mot de passe");
            }
        });

        stage.setScene(new Scene(root, 460, 400));
        stage.setTitle("Modifier le mot de passe");
        stage.setResizable(false);
        stage.show();
    }

    private static VBox fieldBlock(String labelText, PasswordField pf) {
        Label lbl = new Label(labelText);
        lbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        lbl.setTextFill(Color.web("#374151"));
        return new VBox(5, lbl, pf);
    }

    private static PasswordField styledPassword(String prompt) {
        PasswordField pf = new PasswordField();
        pf.setPromptText(prompt);
        pf.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        pf.focusedProperty().addListener((obs, oldVal, newVal) -> pf.setStyle(
            "-fx-background-color: " + (newVal ? "#ffffff" : "#f9fafb") + ";" +
            "-fx-border-color: "     + (newVal ? "#4f46e5" : "#e5e7eb") + ";" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        ));
        return pf;
    }

    private static void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private static void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}