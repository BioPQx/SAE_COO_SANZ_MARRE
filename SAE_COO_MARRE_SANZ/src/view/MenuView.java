package view;

import model.SessionUtilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuView {

    public static VBox createMenu(BorderPane root) {

        VBox menu = new VBox(4);
        menu.setPadding(new Insets(20, 12, 20, 12));
        menu.setPrefWidth(200);
        menu.setStyle("-fx-background-color: #1a1a2e;");

        Label appLabel = new Label("GESTION");
        appLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        appLabel.setTextFill(Color.web("#4f46e5"));
        appLabel.setPadding(new Insets(0, 0, 4, 8));

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #2d2d4e;");
        VBox.setMargin(sep, new Insets(8, 0, 12, 0));

        int niveau = SessionUtilisateur.getNiveau();

        Button btnAccueil      = menuButton("🏠  Accueil");
        Button btnUtilisateurs = menuButton("👥  Utilisateurs");
        Button btnStock        = menuButton("📦  Stocks");
        Button btnEmprunts     = menuButton("📋  Réservations");
        Button btnStats        = menuButton("📊  Statistiques");
        Button btnParams       = menuButton("⚙️  Paramètres");

        btnAccueil.setOnAction(e      -> { setActive(menu, btnAccueil);      root.setCenter(AccueilView.create()); });
        btnUtilisateurs.setOnAction(e -> { setActive(menu, btnUtilisateurs); root.setCenter(UtilisateurView.create()); });
        btnStock.setOnAction(e        -> { setActive(menu, btnStock);        root.setCenter(StockView.create()); });
        btnEmprunts.setOnAction(e     -> { setActive(menu, btnEmprunts);     root.setCenter(EmpruntView.create()); });
        btnStats.setOnAction(e        -> { setActive(menu, btnStats);        root.setCenter(StatistiquesView.create()); });
        btnParams.setOnAction(e       -> { setActive(menu, btnParams);       root.setCenter(ParametresView.create()); });

        menu.getChildren().addAll(appLabel, sep);

        if (niveau >= 1) {
            menu.getChildren().addAll(btnAccueil, btnEmprunts, btnStats, btnParams);
        }
        if (niveau >= 2) {
            menu.getChildren().add(btnStock);
        }
        if (niveau >= 3) {
            menu.getChildren().add(btnUtilisateurs);
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        menu.getChildren().add(spacer);

        setActive(menu, btnAccueil);

        return menu;
    }

    private static Button menuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setFont(Font.font("Segoe UI", 13));
        applyInactiveStyle(btn);
        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains("#4f46e5") || btn.getStyle().contains("#eef2ff")) {
                btn.setStyle(
                    "-fx-background-color: #2d2d4e;" +
                    "-fx-text-fill: #e5e7eb;" +
                    "-fx-background-radius: 7;" +
                    "-fx-border-color: transparent;" +
                    "-fx-padding: 10 14;" +
                    "-fx-cursor: hand;" +
                    "-fx-font-family: 'Segoe UI';" +
                    "-fx-font-size: 13px;"
                );
            }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains("#ffffff") || btn.getStyle().contains("#4f46e5")) {
                if (!isActive(btn)) applyInactiveStyle(btn);
            }
        });
        return btn;
    }

    private static void setActive(VBox menu, Button active) {
        menu.getChildren().forEach(node -> {
            if (node instanceof Button btn) {
                if (btn == active) {
                    btn.setStyle(
                        "-fx-background-color: #4f46e5;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-background-radius: 7;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 10 14;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
                    );
                } else {
                    applyInactiveStyle(btn);
                }
            }
        });
    }

    private static void applyInactiveStyle(Button btn) {
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #9ca3af;" +
            "-fx-background-radius: 7;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 10 14;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;"
        );
    }

    private static boolean isActive(Button btn) {
        return btn.getStyle().contains("-fx-font-weight: bold");
    }
}