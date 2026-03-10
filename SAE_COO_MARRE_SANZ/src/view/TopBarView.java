package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.SessionUtilisateur;
import java.io.InputStream;

public class TopBarView {

    public static HBox create(Stage primaryStage) {

        HBox bar = new HBox();
        bar.setPadding(new Insets(0, 24, 0, 24));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setSpacing(16);
        bar.setPrefHeight(52);
        bar.setStyle(
            "-fx-background-color: #1a1a2e;" +
            "-fx-border-color: transparent transparent #2d2d4e transparent;" +
            "-fx-border-width: 0 0 1 0;"
        );

        // ---- Indicateur accent ----
        Region accent = new Region();
        accent.setPrefWidth(4);
        accent.setPrefHeight(24);
        accent.setStyle("-fx-background-color: #4f46e5; -fx-background-radius: 2;");

        // ---- Titre ----
        Label title = new Label("Application de gestion");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        title.setTextFill(Color.web("#f9fafb"));

        // ---- Spacer ----
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // ---- Infos session ----
        String login = SessionUtilisateur.get().getLogin();
        Label sessionLabel = new Label("Connecté en tant que");
        sessionLabel.setFont(Font.font("Segoe UI", 11));
        sessionLabel.setTextFill(Color.web("#6b7280"));

        Label loginLabel = new Label(login);
        loginLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        loginLabel.setTextFill(Color.web("#e5e7eb"));

        VBox sessionInfo = new VBox(1, sessionLabel, loginLabel);
        sessionInfo.setAlignment(Pos.CENTER_RIGHT);

        // ---- Séparateur vertical ----
        Separator vertSep = new Separator(javafx.geometry.Orientation.VERTICAL);
        vertSep.setStyle("-fx-background-color: #2d2d4e;");
        vertSep.setPrefHeight(24);

        // ---- Icône utilisateur ----
        InputStream iconStream = TopBarView.class.getResourceAsStream("/images/User_Icon.png");
        ImageView userIcon = null;
        if (iconStream != null) {
            userIcon = new ImageView(new Image(iconStream));
            userIcon.setFitHeight(18);
            userIcon.setFitWidth(18);
        }

        // ---- Menu utilisateur ----
        MenuButton userMenu = new MenuButton(login, userIcon);
        userMenu.setStyle(
            "-fx-background-color: #2d2d4e;" +
            "-fx-text-fill: #e5e7eb;" +
            "-fx-background-radius: 7;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 6 14;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        userMenu.setOnMouseEntered(e -> userMenu.setStyle(
            "-fx-background-color: #3d3d5e;" +
            "-fx-text-fill: #ffffff;" +
            "-fx-background-radius: 7;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 6 14;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        ));
        userMenu.setOnMouseExited(e -> userMenu.setStyle(
            "-fx-background-color: #2d2d4e;" +
            "-fx-text-fill: #e5e7eb;" +
            "-fx-background-radius: 7;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 6 14;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        ));

        MenuItem changePwd = new MenuItem("🔑  Modifier mot de passe");
        MenuItem logout    = new MenuItem("🚪  Déconnexion");

        changePwd.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px;");
        logout.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-text-fill: #dc2626;");

        userMenu.getItems().addAll(changePwd, logout);

        changePwd.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Modifier mot de passe");
            ModifierMotDePasseView.show(stage, SessionUtilisateur.get());
            stage.show();
        });

        logout.setOnAction(e -> {
            SessionUtilisateur.deconnecter();
            LoginView.show(primaryStage);
        });

        bar.getChildren().addAll(accent, title, spacer, sessionInfo, vertSep, userMenu);
        return bar;
    }
}