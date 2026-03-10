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
import utils.CsvExporter;
import utils.CsvImporter;
import model.Reservation;
import repository.ReservationRepository;
import java.util.List;
import java.util.stream.Collectors;

public class EmpruntView {

    public static VBox create() {

        VBox root = new VBox(16);
        root.setPadding(new Insets(28, 32, 28, 32));
        root.setStyle("-fx-background-color: #f3f4f6;");

        // ---- Header ----
        Label titre = new Label("Gestion des emprunts");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Recherchez, créez et gérez les emprunts en cours");
        sousTitre.setFont(Font.font("Segoe UI", 13));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        // ---- Boutons d'action ----
        Button btnCreer    = actionButton("＋  Nouvel emprunt", true);
        Button btnExporter = actionButton("↑  Exporter CSV", false);
        Button btnImporter = actionButton("↓  Importer CSV", false);

        HBox actions = new HBox(8, btnCreer, btnExporter, btnImporter);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(spacer, actions);
        topBar.setAlignment(Pos.CENTER_LEFT);

        VBox headerBlock = new VBox(10, titleBlock, topBar);

        // ---- Carte de recherche ----
        Label searchTitle = new Label("Filtres de recherche");
        searchTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        searchTitle.setTextFill(Color.web("#374151"));

        TextField tfId          = styledField("ID");
        TextField tfDomaine     = styledField("Domaine");
        TextField tfRessource   = styledField("Ressource");
        TextField tfDescription = styledField("Description");
        TextField tfDur         = styledField("Heure - Durée");
        TextField tfType        = styledField("Type");
        TextField tfMaj         = styledField("Dernière mise à jour");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        ColumnConstraints lCol = new ColumnConstraints(150);
        ColumnConstraints fCol = new ColumnConstraints();
        fCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints lCol2 = new ColumnConstraints(150);
        ColumnConstraints fCol2 = new ColumnConstraints();
        fCol2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(lCol, fCol, lCol2, fCol2);

        grid.addRow(0, fieldLabel("ID"),            tfId,          fieldLabel("Domaine"),             tfDomaine);
        grid.addRow(1, fieldLabel("Ressource"),      tfRessource,   fieldLabel("Description"),         tfDescription);
        grid.addRow(2, fieldLabel("Heure - Durée"),  tfDur,         fieldLabel("Type"),                tfType);
        grid.addRow(3, fieldLabel("Dernière MAJ"),   tfMaj,         new Label(""), new Label(""));

        Button btnRecherche = new Button("Rechercher");
        btnRecherche.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 20;" +
            "-fx-cursor: hand;"
        );
        btnRecherche.setOnMouseEntered(e -> btnRecherche.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 20;" +
            "-fx-cursor: hand;"
        ));
        btnRecherche.setOnMouseExited(e -> btnRecherche.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 20;" +
            "-fx-cursor: hand;"
        ));

        HBox searchActions = new HBox(btnRecherche);
        searchActions.setAlignment(Pos.CENTER_RIGHT);

        VBox searchCard = new VBox(12, searchTitle, grid, searchActions);
        searchCard.setPadding(new Insets(20, 24, 20, 24));
        searchCard.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;"
        );

        // ---- TableView ----
        TableView<Reservation> table = new TableView<>();
        table.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;"
        );
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Reservation, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colId.setMaxWidth(60);

        TableColumn<Reservation, String> colDomaine = new TableColumn<>("Domaine");
        colDomaine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDomaine()));

        TableColumn<Reservation, String> colRessource = new TableColumn<>("Ressource");
        colRessource.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRessource()));

        TableColumn<Reservation, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Reservation, String> colHeure = new TableColumn<>("Heure - Durée");
        colHeure.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHeureDuree()));

        TableColumn<Reservation, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));

        TableColumn<Reservation, String> colMaj = new TableColumn<>("Dernière MAJ");
        colMaj.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDerniereMaj()));

        TableColumn<Reservation, Void> colAction = new TableColumn<>("Actions");
        colAction.setMinWidth(160);
        colAction.setMaxWidth(160);
        colAction.setCellFactory(tc -> new TableCell<>() {
            private final Button btnModifier  = inlineButton("Modifier",  true);
            private final Button btnSupprimer = inlineButton("Retourner", false);
            private final HBox box = new HBox(6, btnModifier, btnSupprimer);
            {
                box.setAlignment(Pos.CENTER);
                btnModifier.setOnAction(e -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    Stage stage = new Stage();
                    stage.setTitle("Modifier emprunt");
                    ModifierEmpruntView.show(stage, reservation, table.getItems());
                    stage.show();
                });
                btnSupprimer.setOnAction(e -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Confirmation du retour");
                    confirm.setHeaderText("Retourner l'emprunt ID : " + reservation.getId());
                    confirm.setContentText("Valider le retour ?");
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                new ReservationRepository().delete(reservation.getId());
                                getTableView().getItems().remove(reservation);
                                new Alert(Alert.AlertType.INFORMATION, "Emprunt retourné").showAndWait();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                new Alert(Alert.AlertType.ERROR, "Erreur lors du retour").showAndWait();
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

        table.getColumns().addAll(colId, colDomaine, colRessource, colDescription, colHeure, colType, colMaj, colAction);

        table.setRowFactory(tv -> {
            TableRow<Reservation> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isSelected) ->
                row.setStyle(isSelected ? "-fx-background-color: #eef2ff;" : "-fx-background-color: #ffffff;")
            );
            return row;
        });

        // ---- Données ----
        ReservationRepository repo = new ReservationRepository();
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(
                repo.findAll().stream()
                        .filter(r -> r.getType().trim().equalsIgnoreCase("Emprunt"))
                        .toList()
        );
        table.setItems(reservations);

        // ---- Recherche ----
        btnRecherche.setOnAction(e -> {
            List<Reservation> filtered = reservations.stream()
                    .filter(r -> {
                        boolean match = true;
                        if (!tfId.getText().trim().isEmpty()) {
                            try { match &= r.getId() == Integer.parseInt(tfId.getText().trim()); }
                            catch (NumberFormatException ex) { return false; }
                        }
                        if (!tfDomaine.getText().trim().isEmpty())
                            match &= r.getDomaine().toLowerCase().contains(tfDomaine.getText().toLowerCase());
                        if (!tfRessource.getText().trim().isEmpty())
                            match &= r.getRessource().toLowerCase().contains(tfRessource.getText().toLowerCase());
                        if (!tfDescription.getText().trim().isEmpty())
                            match &= r.getDescription().toLowerCase().contains(tfDescription.getText().toLowerCase());
                        if (!tfDur.getText().trim().isEmpty())
                            match &= r.getHeureDuree().toLowerCase().contains(tfDur.getText().toLowerCase());
                        if (!tfType.getText().trim().isEmpty())
                            match &= r.getType().toLowerCase().contains(tfType.getText().toLowerCase());
                        if (!tfMaj.getText().trim().isEmpty())
                            match &= r.getDerniereMaj().toLowerCase().contains(tfMaj.getText().toLowerCase());
                        return match;
                    })
                    .collect(Collectors.toList());
            table.setItems(FXCollections.observableArrayList(filtered));
        });

        // ---- Nouvel emprunt ----
        btnCreer.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Nouvel emprunt");
            CreerEmpruntView.show(stage, table.getItems());
            stage.show();
        });

        // ---- Export CSV ----
        btnExporter.setOnAction(e -> {
            try {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Exporter les emprunts");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
                chooser.setInitialFileName("emprunts_export.csv");
                File file = chooser.showSaveDialog(null);
                if (file == null) return;
                List<String[]> rows = table.getItems().stream()
                        .map(r -> new String[]{
                                String.valueOf(r.getId()), r.getDomaine(), r.getRessource(),
                                r.getDescription(), r.getHeureDuree(), r.getType(), r.getDerniereMaj()
                        }).toList();
                CsvExporter.export(file.getAbsolutePath(),
                        "id;domaine;ressource;description;heure_duree;type;derniere_maj", rows);
                new Alert(Alert.AlertType.INFORMATION, "Export CSV réussi !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'export CSV").showAndWait();
            }
        });

        // ---- Import CSV ----
        btnImporter.setOnAction(e -> {
            try {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Importer un fichier CSV");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
                File file = chooser.showOpenDialog(null);
                if (file == null) return;
                List<String[]> rows = CsvImporter.readCsv(file.getAbsolutePath());
                ObservableList<Reservation> importedReservations = FXCollections.observableArrayList();
                for (String[] row : rows) {
                    int id = Integer.parseInt(row[0]);
                    Reservation r = new Reservation(id, row[1], row[2], row[3], row[4], row[5], row[6]);
                    importedReservations.add(r);
                }
                table.setItems(importedReservations);
                ReservationRepository repo1 = new ReservationRepository();
                repo1.saveAll(importedReservations);
                new Alert(Alert.AlertType.INFORMATION, "Import CSV réussi !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'import CSV").showAndWait();
            }
        });

        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().addAll(headerBlock, searchCard, table);
        return root;
    }

    private static TextField styledField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> tf.setStyle(
            "-fx-background-color: " + (newVal ? "#ffffff" : "#f9fafb") + ";" +
            "-fx-border-color: "     + (newVal ? "#4f46e5" : "#e5e7eb") + ";" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        ));
        return tf;
    }

    private static Label fieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        lbl.setTextFill(Color.web("#374151"));
        return lbl;
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