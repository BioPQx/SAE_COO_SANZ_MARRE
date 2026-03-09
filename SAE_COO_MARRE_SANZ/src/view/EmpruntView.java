package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import model.Reservation;
import repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

public class EmpruntView {

    public static VBox create() {

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titre = new Label("Rechercher un emprunt");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnCreer = new Button("Nouvel Emprunt");

        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(titre, spacer, btnCreer);

        // ---- Champs de recherche ----

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfId = new TextField();
        TextField tfDomaine = new TextField();
        TextField tfRessource = new TextField();
        TextField tfDescription = new TextField();
        TextField tfDur = new TextField();
        TextField tfType = new TextField();
        TextField tfMaj = new TextField();

        grid.addRow(0, new Label("ID :"), tfId);
        grid.addRow(1, new Label("Domaine :"), tfDomaine);
        grid.addRow(2, new Label("Ressource :"), tfRessource);
        grid.addRow(3, new Label("Description :"), tfDescription);
        grid.addRow(4, new Label("Heure - Durée :"), tfDur);
        grid.addRow(5, new Label("Type :"), tfType);
        grid.addRow(6, new Label("Dernière mise à jour :"), tfMaj);

        Button btnRecherche = new Button("Rechercher");

        // ---- TableView ----

        TableView<Reservation> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Reservation, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Reservation, String> colDomaine = new TableColumn<>("Domaine");
        colDomaine.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDomaine()));

        TableColumn<Reservation, String> colRessource = new TableColumn<>("Ressource");
        colRessource.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRessource()));

        TableColumn<Reservation, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Reservation, String> colHeure = new TableColumn<>("Heure - Durée");
        colHeure.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getHeureDuree()));

        TableColumn<Reservation, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getType()));

        TableColumn<Reservation, String> colMaj = new TableColumn<>("Dernière mise à jour");
        colMaj.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDerniereMaj()));

        table.getColumns().addAll(
                colId,
                colDomaine,
                colRessource,
                colDescription,
                colHeure,
                colType,
                colMaj
        );

        // ---- Chargement des données ----

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
                            try {
                                match &= r.getId() == Integer.parseInt(tfId.getText().trim());
                            } catch (NumberFormatException ex) {
                                return false;
                            }
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
        
        // ---- Création d'un utilisateur ----
        btnCreer.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Nouvelle réservation");
            CreerReservationView.show(stage, table.getItems());
            stage.show();
        });

        root.getChildren().addAll(topBar, grid, btnRecherche, table);

        return root;
    }
}