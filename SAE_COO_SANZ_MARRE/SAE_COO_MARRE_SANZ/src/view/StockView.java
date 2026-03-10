package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import model.Stock;
import repository.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

public class StockView {

    public static VBox create() {

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titre = new Label("Stocks");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField tfRecherche = new TextField();
        tfRecherche.setPromptText("Rechercher une ressource...");

        Button btnRecherche = new Button("Rechercher");
        Button btnAjouter = new Button("Ajouter");

        HBox topBar = new HBox(10, tfRecherche, btnRecherche, btnAjouter);

        TableView<Stock> table = new TableView<>();

        TableColumn<Stock,String> colId = new TableColumn<>("ID materiel");
        colId.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Stock,String> colRessource = new TableColumn<>("Ressource");
        colRessource.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRessource()));

        TableColumn<Stock,String> colEtat = new TableColumn<>("Etat");
        colEtat.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEtat()));

        TableColumn<Stock,String> colQuantite = new TableColumn<>("Quantité");
        colQuantite.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getQuantiteDisponible())));

        TableColumn<Stock,String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatut()));

        table.getColumns().addAll(colId,colRessource,colEtat,colQuantite,colStatut);

        StockRepository repo = new StockRepository();

        ObservableList<Stock> stocks =
                FXCollections.observableArrayList(repo.findAll());

        table.setItems(stocks);

        btnRecherche.setOnAction(e -> {

            List<Stock> filtered =
                    repo.findAll().stream()
                            .filter(s -> s.getRessource()
                            .toLowerCase()
                            .contains(tfRecherche.getText().toLowerCase()))
                            .collect(Collectors.toList());

            table.setItems(FXCollections.observableArrayList(filtered));

        });
        
        btnAjouter.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Ressource");
            CreerStockView.show(stage, table.getItems());
            stage.show();
        });

        root.getChildren().addAll(titre, topBar, table);

        return root;
    }
}