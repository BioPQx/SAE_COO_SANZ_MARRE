package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SessionUtilisateur;
import model.Utilisateur;
import repository.AuthentificationRepository;
import utils.PasswordUtils;

import java.io.IOException;

public class ModifierMotDePasseView {

    public static void show(Stage stage, Utilisateur user) {
        Label titre = new Label("Modifier le mot de passe");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        PasswordField pfOld = new PasswordField();
        pfOld.setPromptText("Mot de passe actuel");

        PasswordField pfNew = new PasswordField();
        pfNew.setPromptText("Nouveau mot de passe");

        PasswordField pfConfirm = new PasswordField();
        pfConfirm.setPromptText("Confirmer le nouveau mot de passe");

        Button btnEnregistrer = new Button("Enregistrer");

        VBox root = new VBox(10, titre, pfOld, pfNew, pfConfirm, btnEnregistrer);
        root.setPadding(new Insets(20));

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

        stage.setScene(new Scene(root, 350, 250));
        stage.setResizable(false);
        stage.show();
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }

    private static void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
