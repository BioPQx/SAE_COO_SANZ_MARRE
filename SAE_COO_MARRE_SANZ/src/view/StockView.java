package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
import model.Stock;
import repository.StockRepository;
import utils.CsvExporter;
import utils.CsvImporter;
import java.util.List;
import java.util.stream.Collectors;

public class StockView {

    public static VBox create() {

        VBox root = new VBox(16);
        root.setPadding(new Insets(28, 32, 28, 32));
        root.setStyle("-fx-background-color: #f3f4f6;");

        // ---- Header ----
        Label titre = new Label("Gestion des stocks");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Consultez, ajoutez et gérez les ressources disponibles");
        sousTitre.setFont(Font.font("Segoe UI", 13));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        // ---- Boutons d'action ----
        Button btnAjouter  = actionButton("＋  Ajouter",      true);
        Button btnExporter = actionButton("↑  Exporter CSV",  false);
        Button btnImporter = actionButton("↓  Importer CSV",  false);

        HBox actions = new HBox(8, btnAjouter, btnExporter, btnImporter);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Region spacerHeader = new Region();
        HBox.setHgrow(spacerHeader, Priority.ALWAYS);

        HBox topBar = new HBox(spacerHeader, actions);
        topBar.setAlignment(Pos.CENTER_LEFT);

        VBox headerBlock = new VBox(10, titleBlock, topBar);

        // ---- Barre de recherche ----
        TextField tfRecherche = new TextField();
        tfRecherche.setPromptText("Rechercher une ressource...");
        tfRecherche.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        tfRecherche.focusedProperty().addListener((obs, o, newVal) -> tfRecherche.setStyle(
            "-fx-background-color: " + (newVal ? "#ffffff" : "#f9fafb") + ";" +
            "-fx-border-color: "     + (newVal ? "#4f46e5" : "#e5e7eb") + ";" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        ));
        HBox.setHgrow(tfRecherche, Priority.ALWAYS);

        Button btnRecherche = new Button("Rechercher");
        btnRecherche.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 18;" +
            "-fx-cursor: hand;"
        );
        btnRecherche.setOnMouseEntered(e -> btnRecherche.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 18;" +
            "-fx-cursor: hand;"
        ));
        btnRecherche.setOnMouseExited(e -> btnRecherche.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 18;" +
            "-fx-cursor: hand;"
        ));

        HBox searchBar = new HBox(10, tfRecherche, btnRecherche);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(16, 20, 16, 20));
        searchBar.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;"
        );

        // ---- TableView ----
        TableView<Stock> table = new TableView<>();
        table.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;"
        );
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Stock, String> colId = new TableColumn<>("ID matériel");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colId.setMaxWidth(90);

        TableColumn<Stock, String> colRessource = new TableColumn<>("Ressource");
        colRessource.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRessource()));

        TableColumn<Stock, String> colEtat = new TableColumn<>("État");
        colEtat.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEtat()));

        TableColumn<Stock, String> colQuantite = new TableColumn<>("Quantité");
        colQuantite.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQuantiteDisponible())));
        colQuantite.setMaxWidth(90);

        TableColumn<Stock, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatut()));
        colStatut.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                String color = switch (item.toLowerCase()) {
                    case "disponible"        -> "-fx-text-fill: #16a34a; -fx-font-weight: bold;";
                    case "rupture",
                         "indisponible"      -> "-fx-text-fill: #dc2626; -fx-font-weight: bold;";
                    case "commande en cours",
                         "maintenance"       -> "-fx-text-fill: #d97706; -fx-font-weight: bold;";
                    default                  -> "-fx-text-fill: #374151;";
                };
                setStyle(color);
            }
        });

        TableColumn<Stock, Void> colAction = new TableColumn<>("Actions");
        colAction.setMinWidth(160);
        colAction.setMaxWidth(160);
        colAction.setCellFactory(tc -> new TableCell<>() {
            private final Button btnModifier  = inlineButton("Modifier",   true);
            private final Button btnSupprimer = inlineButton("Supprimer",  false);
            private final HBox box = new HBox(6, btnModifier, btnSupprimer);
            {
                box.setAlignment(Pos.CENTER);
                btnModifier.setOnAction(e -> {
                    Stock stock = getTableView().getItems().get(getIndex());
                    Stage stage = new Stage();
                    stage.setTitle("Modifier ressource");
                    ModifierStockView.show(stage, stock, table.getItems());
                    stage.show();
                });
                btnSupprimer.setOnAction(e -> {
                    Stock stock = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Confirmation suppression");
                    confirm.setHeaderText("Supprimer la ressource : " + stock.getRessource());
                    confirm.setContentText("Cette action est irréversible.");
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                new StockRepository().delete(stock.getId());
                                getTableView().getItems().remove(stock);
                                new Alert(Alert.AlertType.INFORMATION, "Ressource supprimée").showAndWait();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression").showAndWait();
                            }
                        }
                    });
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().addAll(colId, colRessource, colEtat, colQuantite, colStatut, colAction);

        table.setRowFactory(tv -> {
            TableRow<Stock> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isSelected) ->
                row.setStyle(isSelected ? "-fx-background-color: #eef2ff;" : "-fx-background-color: #ffffff;")
            );
            return row;
        });

        StockRepository repo = new StockRepository();
        ObservableList<Stock> stocks = FXCollections.observableArrayList(repo.findAll());
        table.setItems(stocks);

        // ---- Recherche ----
        btnRecherche.setOnAction(e -> {
            List<Stock> filtered = repo.findAll().stream()
                    .filter(s -> s.getRessource().toLowerCase().contains(tfRecherche.getText().toLowerCase()))
                    .collect(Collectors.toList());
            table.setItems(FXCollections.observableArrayList(filtered));
        });

        // ---- Ajouter ----
        btnAjouter.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Ajouter une ressource");
            CreerStockView.show(stage, table.getItems());
            stage.show();
        });

        // ---- Export CSV ----
        btnExporter.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Exporter les stocks en CSV");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
                fileChooser.setInitialFileName("stocks_export.csv");
                File file = fileChooser.showSaveDialog(null);
                if (file == null) return;
                List<String[]> rows = table.getItems().stream()
                        .map(s -> new String[]{
                                String.valueOf(s.getId()), s.getRessource(), s.getDescription(),
                                s.getEtat(), String.valueOf(s.getNombre()),
                                String.valueOf(s.getQuantiteDisponible()),
                                String.valueOf(s.getSeuilAlerte()),
                                String.valueOf(s.isActif()), s.getStatut()
                        }).toList();
                CsvExporter.export(file.getAbsolutePath(),
                        "id;ressource;description;etat;nombre;quantite_disponible;seuil_alerte;actif;statut", rows);
                new Alert(Alert.AlertType.INFORMATION, "Export CSV réussi !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'export").showAndWait();
            }
        });

        // ---- Import CSV ----
        btnImporter.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Importer un fichier CSV");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
                File file = fileChooser.showOpenDialog(null);
                if (file == null) return;
                List<String[]> importedRows = CsvImporter.readCsv(file.getAbsolutePath());
                List<Stock> importedStocks = StockRepository.fromCsv(importedRows);
                repo.saveAll(importedStocks);
                stocks.setAll(importedStocks);
                new Alert(Alert.AlertType.INFORMATION, "Import CSV réussi !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'import CSV").showAndWait();
            }
        });

        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().addAll(headerBlock, searchBar, table);
        return root;
    }

    private static Button actionButton(String text, boolean primary) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        String base = primary
            ? "-fx-background-color: #4f46e5; -fx-text-fill: white; -fx-border-color: transparent;"
            : "-fx-background-color: #ffffff; -fx-text-fill: #374151; -fx-border-color: #d1d5db;";
        String hover = primary
            ? "-fx-background-color: #4338ca; -fx-text-fill: white; -fx-border-color: transparent;"
            : "-fx-background-color: #f9fafb; -fx-text-fill: #111827; -fx-border-color: #9ca3af;";
        String common =
            "-fx-background-radius: 6;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 8 16;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;";
        btn.setStyle(base + common);
        btn.setOnMouseEntered(e -> btn.setStyle(hover + common));
        btn.setOnMouseExited(e  -> btn.setStyle(base  + common));
        return btn;
    }

    private static Button inlineButton(String text, boolean primary) {
        Button btn = new Button(text);
        String base = primary
            ? "-fx-background-color: #eef2ff; -fx-text-fill: #4f46e5; -fx-border-color: transparent;"
            : "-fx-background-color: #fef2f2; -fx-text-fill: #dc2626; -fx-border-color: transparent;";
        String hover = primary
            ? "-fx-background-color: #e0e7ff; -fx-text-fill: #4338ca; -fx-border-color: transparent;"
            : "-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-border-color: transparent;";
        String common =
            "-fx-background-radius: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 5 10;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;";
        btn.setStyle(base + common);
        btn.setOnMouseEntered(e -> btn.setStyle(hover + common));
        btn.setOnMouseExited(e  -> btn.setStyle(base  + common));
        return btn;
    }
}