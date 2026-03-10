package view;

import model.Utilisateur;
import repository.AuthentificationRepository;
import repository.UtilisateurRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import utils.CsvExporter;
import utils.CsvImporter;

public class UtilisateurView {

    public static VBox create() {

        VBox root = new VBox(16);
        root.setPadding(new Insets(28, 32, 28, 32));
        root.setStyle("-fx-background-color: #f3f4f6;");

        // ---- Header ----
        Label titre = new Label("Gestion des utilisateurs");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Recherchez, créez et administrez les comptes utilisateurs");
        sousTitre.setFont(Font.font("Segoe UI", 13));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        // ---- Boutons d'action ----
        Button btnCreer    = actionButton("＋  Nouvel utilisateur", true);
        Button btnExporter = actionButton("↑  Exporter CSV",        false);
        Button btnImporter = actionButton("↓  Importer CSV",        false);

        HBox actions = new HBox(8, btnCreer, btnExporter, btnImporter);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Region spacerHeader = new Region();
        HBox.setHgrow(spacerHeader, Priority.ALWAYS);

        HBox topBar = new HBox(spacerHeader, actions);
        topBar.setAlignment(Pos.CENTER_LEFT);

        VBox headerBlock = new VBox(10, titleBlock, topBar);

        // ---- Carte de recherche ----
        Label searchTitle = new Label("Filtres de recherche");
        searchTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        searchTitle.setTextFill(Color.web("#374151"));

        TextField tfId     = styledField("ID");
        TextField tfNom    = styledField("Nom");
        TextField tfPrenom = styledField("Prénom");
        TextField tfLogin  = styledField("Login");
        TextField tfNiveau = styledField("Niveau");
        TextField tfActif  = styledField("oui / non");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        ColumnConstraints lc1 = new ColumnConstraints(130);
        ColumnConstraints fc1 = new ColumnConstraints();
        fc1.setHgrow(Priority.ALWAYS);
        ColumnConstraints lc2 = new ColumnConstraints(130);
        ColumnConstraints fc2 = new ColumnConstraints();
        fc2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(lc1, fc1, lc2, fc2);

        grid.addRow(0, fieldLabel("ID"),      tfId,     fieldLabel("Nom"),    tfNom);
        grid.addRow(1, fieldLabel("Prénom"),  tfPrenom, fieldLabel("Login"),  tfLogin);
        grid.addRow(2, fieldLabel("Niveau"),  tfNiveau, fieldLabel("Actif"),  tfActif);

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
        TableView<Utilisateur> table = new TableView<>();
        table.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;"
        );
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Utilisateur, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMaxWidth(60);

        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Utilisateur, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Utilisateur, String> colLogin = new TableColumn<>("Login");
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Utilisateur, String> colNiveauCol = new TableColumn<>("Niveau");
        colNiveauCol.setCellValueFactory(new PropertyValueFactory<>("niveauAutorisation"));
        colNiveauCol.setMaxWidth(80);

        TableColumn<Utilisateur, Boolean> colActif = new TableColumn<>("Actif");
        colActif.setCellValueFactory(new PropertyValueFactory<>("actif"));
        colActif.setMaxWidth(70);
        colActif.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item ? "✓" : "✗");
                setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;" +
                        (item ? "-fx-text-fill: #16a34a;" : "-fx-text-fill: #dc2626;"));
            }
        });

        TableColumn<Utilisateur, String> colDate = new TableColumn<>("Date création");
        colDate.setCellValueFactory(u ->
            javafx.beans.binding.Bindings.createStringBinding(() ->
                u.getValue().getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        );

        TableColumn<Utilisateur, Void> colAction = new TableColumn<>("Actions");
        colAction.setMinWidth(160);
        colAction.setMaxWidth(160);
        colAction.setCellFactory(tc -> new TableCell<>() {
            private final Button btnModifier  = inlineButton("Modifier",   true);
            private final Button btnSupprimer = inlineButton("Supprimer",  false);
            private final HBox box = new HBox(6, btnModifier, btnSupprimer);
            {
                box.setAlignment(Pos.CENTER);
                btnModifier.setOnAction(e -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    Stage stage = new Stage();
                    stage.setTitle("Modifier utilisateur");
                    ModifierUtilisateurView.show(stage, user, table.getItems());
                    stage.show();
                });
                btnSupprimer.setOnAction(e -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Confirmation suppression");
                    confirm.setHeaderText("Supprimer " + user.getNom() + " " + user.getPrenom() + " ?");
                    confirm.setContentText("Cette action est irréversible.");
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                new UtilisateurRepository().delete(user.getId());
                                new AuthentificationRepository().deleteByUserId(user.getId());
                                getTableView().getItems().remove(user);
                                new Alert(Alert.AlertType.INFORMATION, "Utilisateur supprimé !").showAndWait();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression !").showAndWait();
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

        table.getColumns().addAll(colId, colNom, colPrenom, colLogin, colNiveauCol, colActif, colDate, colAction);

        table.setRowFactory(tv -> {
            TableRow<Utilisateur> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isSelected) ->
                row.setStyle(isSelected ? "-fx-background-color: #eef2ff;" : "-fx-background-color: #ffffff;")
            );
            return row;
        });

        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().addAll(headerBlock, searchCard, table);

        // ---- Recherche ----
        btnRecherche.setOnAction(e -> {
            try {
                List<Utilisateur> allUsers = new UtilisateurRepository().findAll();
                ObservableList<Utilisateur> filtered = FXCollections.observableArrayList(
                    allUsers.stream().filter(u -> {
                        boolean match = true;
                        if (!tfId.getText().trim().isEmpty()) {
                            try { match &= u.getId() == Integer.parseInt(tfId.getText().trim()); }
                            catch (NumberFormatException ex) { return false; }
                        }
                        if (!tfNom.getText().trim().isEmpty())
                            match &= u.getNom().toLowerCase().contains(tfNom.getText().trim().toLowerCase());
                        if (!tfPrenom.getText().trim().isEmpty())
                            match &= u.getPrenom().toLowerCase().contains(tfPrenom.getText().trim().toLowerCase());
                        if (!tfLogin.getText().trim().isEmpty())
                            match &= u.getLogin().toLowerCase().contains(tfLogin.getText().trim().toLowerCase());
                        if (!tfActif.getText().trim().isEmpty()) {
                            String actif = tfActif.getText().trim().toLowerCase();
                            if (actif.equals("oui") || actif.equals("true"))       match &= u.isActif();
                            else if (actif.equals("non") || actif.equals("false")) match &= !u.isActif();
                            else return false;
                        }
                        if (!tfNiveau.getText().trim().isEmpty()) {
                            try { match &= u.getNiveauAutorisation() == Integer.parseInt(tfNiveau.getText().trim()); }
                            catch (NumberFormatException ex) { return false; }
                        }
                        return match;
                    }).collect(Collectors.toList())
                );
                table.setItems(filtered);
            } catch (IOException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la lecture des utilisateurs !").showAndWait();
            }
        });

        // ---- Création ----
        btnCreer.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Créer un utilisateur");
            CreerUtilisateurView.show(stage, table.getItems());
            stage.show();
        });

        // ---- Export CSV ----
        btnExporter.setOnAction(e -> {
            try {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Exporter les utilisateurs");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
                chooser.setInitialFileName("utilisateurs_export.csv");
                File file = chooser.showSaveDialog(null);
                if (file == null) return;
                List<String[]> rows = table.getItems().stream()
                        .map(u -> new String[]{
                                String.valueOf(u.getId()), u.getNom(), u.getPrenom(), u.getLogin(),
                                String.valueOf(u.getNiveauAutorisation()), String.valueOf(u.isActif()),
                                u.getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        }).toList();
                CsvExporter.export(file.getAbsolutePath(),
                        "id;nom;prenom;login;niveau;actif;date_creation", rows);
                new Alert(Alert.AlertType.INFORMATION, "Export CSV réalisé avec succès !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'export CSV").showAndWait();
            }
        });

        // ---- Import CSV ----
        btnImporter.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Importer des utilisateurs depuis CSV");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
            File file = chooser.showOpenDialog(null);
            if (file == null) return;
            try {
                List<String[]> importedRows = CsvImporter.readCsv(file.getAbsolutePath());
                List<Utilisateur> importedUsers = UtilisateurRepository.fromCsv(importedRows);
                UtilisateurRepository repo = new UtilisateurRepository();
                repo.saveAll(importedUsers);
                table.setItems(FXCollections.observableArrayList(importedUsers));
                new Alert(Alert.AlertType.INFORMATION, "Import CSV réussi !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'import CSV").showAndWait();
            }
        });

        btnRecherche.fire();
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