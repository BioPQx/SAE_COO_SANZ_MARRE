package view;

import model.SessionUtilisateur;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuView {

    public static VBox createMenu(BorderPane root) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10));
        menu.setStyle("-fx-background-color: #2c3e50;");
        
        int niveau = SessionUtilisateur.getNiveau();

        Button btnAccueil = new Button("Accueil");
        Button btnUtilisateurs = new Button("Utilisateurs");
        Button btnStock = new Button("Stocks / Ressources");
        Button btnEmprunts = new Button("Matériel / Réservations");
        Button btnStats = new Button("Statistiques");
        Button btnParams = new Button("Paramètres");
        

        btnUtilisateurs.setOnAction(e ->
                root.setCenter(UtilisateurView.create())
        );

        btnStock.setOnAction(e ->
                root.setCenter(StockView.create())
        );

        btnEmprunts.setOnAction(e ->
                root.setCenter(EmpruntView.create())
        );

        btnStats.setOnAction(e ->
                root.setCenter(StatistiquesView.create())
        );

        btnParams.setOnAction(e ->
                root.setCenter(ParametresView.create())
        );
        btnAccueil.setOnAction(e ->
        		root.setCenter(AccueilView.create())
        );



        if (niveau >= 1) {
            menu.getChildren().addAll(
                    btnAccueil,
                    btnEmprunts,
                    btnStats,
                    btnParams
                    
            );
        }

        if (niveau >= 2) {
            menu.getChildren().add(btnStock);
        }

        if (niveau >= 3) {
            menu.getChildren().add(btnUtilisateurs);
        }

        menu.getChildren().forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setMaxWidth(Double.MAX_VALUE);
            }
        });

        return menu;
    }
}
