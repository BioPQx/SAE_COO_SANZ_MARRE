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
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        title.setTextFill(Color.web("#1a1a2e"));

        Label subtitle = new Label("Accès à l'espace de gestion");
        subtitle.setFont(Font.font("Segoe UI", 12));
        subtitle.setTextFill(Color.web("#6b7280"));

        TextField tfLogin = new TextField();
        tfLogin.setPromptText("Identifiant");
        tfLogin.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 10 14;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        tfLogin.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tfLogin.setStyle(
                    "-fx-background-color: #ffffff;" +
                    "-fx-border-color: #4f46e5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-background-radius: 6;" +
                    "-fx-padding: 10 14;" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-text-fill: #111827;"
                );
            } else {
                tfLogin.setStyle(
                    "-fx-background-color: #f9fafb;" +
                    "-fx-border-color: #e5e7eb;" +
                    "-fx-border-radius: 6;" +
                    "-fx-background-radius: 6;" +
                    "-fx-padding: 10 14;" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-text-fill: #111827;"
                );
            }
        });

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Mot de passe");
        pfPassword.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 10 14;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        pfPassword.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                pfPassword.setStyle(
                    "-fx-background-color: #ffffff;" +
                    "-fx-border-color: #4f46e5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-background-radius: 6;" +
                    "-fx-padding: 10 14;" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-text-fill: #111827;"
                );
            } else {
                pfPassword.setStyle(
                    "-fx-background-color: #f9fafb;" +
                    "-fx-border-color: #e5e7eb;" +
                    "-fx-border-radius: 6;" +
                    "-fx-background-radius: 6;" +
                    "-fx-padding: 10 14;" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-text-fill: #111827;"
                );
            }
        });

        Button btnLogin = new Button("Se connecter");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 11 0;" +
            "-fx-cursor: hand;"
        );
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 11 0;" +
            "-fx-cursor: hand;"
        ));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 11 0;" +
            "-fx-cursor: hand;"
        ));

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #e5e7eb;");

        VBox card = new VBox(16);
        card.setPadding(new Insets(36, 40, 36, 40));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);"
        );
        card.setMaxWidth(340);

        VBox titleBlock = new VBox(4, title, subtitle);
        card.getChildren().addAll(titleBlock, separator, tfLogin, pfPassword, btnLogin);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #f3f4f6;");
        root.setPadding(new Insets(40));

        btnLogin.setOnAction(e -> {
            try {
                AuthentificationRepository authRepo = new AuthentificationRepository();
                UtilisateurRepository userRepo = new UtilisateurRepository();
                Authentification auth = authRepo.findByLogin(tfLogin.getText().trim());
                if (auth == null) {
                    showError("Login ou mot de passe incorrect");
                    return;
                }
                if (!PasswordUtils.checkPassword(pfPassword.getText(), auth.getPasswordHash())) {
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

        Scene scene = new Scene(root, 460, 380);
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}