package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.SessionUtilisateur;

import java.io.InputStream;

public class TopBarView {

    public static HBox create(Stage primaryStage) {

        // Titre de l'application à gauche
        HBox bar = new HBox();
        bar.setPadding(new Insets(10));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setSpacing(20);
        bar.setStyle("-fx-background-color: #34495e;");

        // Label titre
        javafx.scene.control.Label title = new javafx.scene.control.Label("Application de gestion");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Spacer pour pousser le menu utilisateur à droite
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Charger l'icône utilisateur depuis le dossier resources/images
        InputStream iconStream = TopBarView.class.getResourceAsStream("/images/User_Icon.png");
        ImageView userIcon = null;
        if(iconStream != null){
            userIcon = new ImageView(new Image(iconStream));
            userIcon.setFitHeight(20);
            userIcon.setFitWidth(20);
        }

        // Menu utilisateur à droite
        MenuButton userMenu = new MenuButton(SessionUtilisateur.get().getLogin(), userIcon);

        // Items du menu
        MenuItem changePwd = new MenuItem("Modifier mot de passe");
        MenuItem logout = new MenuItem("Déconnexion");
        userMenu.getItems().addAll(changePwd, logout);

        // Actions
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

        // Ajouter tous les éléments au HBox
        bar.getChildren().addAll(title, spacer, userMenu);

        return bar;
    }
}
