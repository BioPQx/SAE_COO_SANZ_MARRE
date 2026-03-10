package view;

import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.geometry.Insets;

import model.Stock;
import repository.StockRepository;

import javafx.collections.ObservableList;

public class CreerStockView {

    public static void show(Stage stage, ObservableList<Stock> stocks) {

        StockRepository repo = new StockRepository();

        int nextId = repo.getNextId();

        Label titre = new Label("Créer une ressource");
        titre.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblId = new Label(String.valueOf(nextId));

        TextField tfRessource = new TextField();
        TextField tfDescription = new TextField();

        ComboBox<String> cbEtat = new ComboBox<>();
        cbEtat.getItems().addAll(
                "Neuf",
                "Bon état",
                "Usé",
                "En réparation"
        );

        TextField tfNombre = new TextField();
        TextField tfQuantite = new TextField();
        TextField tfSeuil = new TextField();

        CheckBox cbActif = new CheckBox("Actif");
        cbActif.setSelected(true);

        ComboBox<String> cbStatut = new ComboBox<>();
        cbStatut.getItems().addAll(
                "Disponible",
                "Rupture",
                "Commande en cours"
        );

        grid.addRow(0,new Label("ID"),lblId);
        grid.addRow(1,new Label("Ressource"),tfRessource);
        grid.addRow(2,new Label("Description"),tfDescription);
        grid.addRow(3,new Label("Etat"),cbEtat);
        grid.addRow(4,new Label("Nombre"),tfNombre);
        grid.addRow(5,new Label("Quantité disponible"),tfQuantite);
        grid.addRow(6,new Label("Seuil alerte"),tfSeuil);
        grid.addRow(7,new Label("Actif"),cbActif);
        grid.addRow(8,new Label("Statut"),cbStatut);

        Button btnCreer = new Button("Créer");
        Button btnAnnuler = new Button("Annuler");

        HBox actions = new HBox(10, btnCreer, btnAnnuler);

        VBox root = new VBox(15, titre, grid, actions);
        root.setPadding(new Insets(20));

        btnAnnuler.setOnAction(e -> stage.close());

        btnCreer.setOnAction(e -> {

            try {

                Stock stock = new Stock(
                        nextId,
                        tfRessource.getText(),
                        tfDescription.getText(),
                        cbEtat.getValue(),
                        Integer.parseInt(tfNombre.getText()),
                        Integer.parseInt(tfQuantite.getText()),
                        Integer.parseInt(tfSeuil.getText()),
                        cbActif.isSelected(),
                        cbStatut.getValue()
                );

                repo.save(stock);

                stocks.add(stock);

                new Alert(Alert.AlertType.INFORMATION,
                        "Stock ajouté avec succès !")
                        .showAndWait();

                stage.close();

            } catch (Exception ex) {

                new Alert(Alert.AlertType.ERROR,
                        "Erreur lors de la création du stock.")
                        .showAndWait();
            }

        });

        stage.setScene(new Scene(root));
        stage.setResizable(false);
    }
}