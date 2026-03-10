package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Authentification;
import model.Utilisateur;
import model.SessionUtilisateur;
import repository.AuthentificationRepository;
import repository.UtilisateurRepository;
import utils.PasswordUtils;

import java.io.IOException;

public class LoginView {

    public static void show(Stage stage) {

        Label title = new Label("Connexion");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField tfLogin = new TextField();
        tfLogin.setPromptText("Login");

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Mot de passe");

        Button btnLogin = new Button("Se connecter");

        VBox root = new VBox(10, title, tfLogin, pfPassword, btnLogin);
        root.setPadding(new Insets(20));

        btnLogin.setOnAction(e -> {
            try {
                AuthentificationRepository authRepo = new AuthentificationRepository();
                UtilisateurRepository userRepo = new UtilisateurRepository();

                Authentification auth = authRepo.findByLogin(tfLogin.getText().trim());

                if (auth == null) {
                    showError("Login ou mot de passe incorrect");
                    return;
                }

                if (!PasswordUtils.checkPassword(
                        pfPassword.getText(),
                        auth.getPasswordHash())) {

                    showError("Login ou mot de passe incorrect");
                    return;
                }

                Utilisateur user = userRepo.findById(auth.getIdUtilisateur());

                if (user == null || !user.isActif()) {
                    showError("Compte désactivé");
                    return;
                }

                if (user.getNiveauAutorisation() <= 0) {
                    showError("Accès refusé");
                    return;
                }

                SessionUtilisateur.connecter(user);

                MainView.show(stage);

            } catch (IOException ex) {
                ex.printStackTrace();
                showError("Erreur lors de la connexion");
            }
        });

        stage.setScene(new Scene(root, 300, 220));
        stage.setTitle("Connexion");
        stage.show();
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}
