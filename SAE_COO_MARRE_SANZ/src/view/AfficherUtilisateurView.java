package view;

import model.Utilisateur;
import repository.UtilisateurRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.time.LocalDate;

public class AfficherUtilisateurView {

    public static BorderPane create() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f3f4f6;");

        Label titre = new Label("Gestion des utilisateurs");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Liste de tous les comptes enregistrés");
        sousTitre.setFont(Font.font("Segoe UI", 13));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox header = new VBox(4, titre, sousTitre);
        header.setPadding(new Insets(28, 32, 16, 32));

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
        colId.setMinWidth(40);
        styleColumn(colId);

        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        styleColumn(colNom);

        TableColumn<Utilisateur, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        styleColumn(colPrenom);

        TableColumn<Utilisateur, String> colLogin = new TableColumn<>("Login");
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        styleColumn(colLogin);

        TableColumn<Utilisateur, Integer> colNiveau = new TableColumn<>("Niveau");
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveauAutorisation"));
        colNiveau.setMaxWidth(90);
        colNiveau.setMinWidth(70);
        styleColumn(colNiveau);

        TableColumn<Utilisateur, Boolean> colActif = new TableColumn<>("Actif");
        colActif.setCellValueFactory(new PropertyValueFactory<>("actif"));
        colActif.setPrefWidth(70);
        colActif.setMaxWidth(80);
        colActif.setMinWidth(60);
        colActif.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "✓" : "✗");
                    setStyle(
                        "-fx-alignment: CENTER;" +
                        "-fx-font-weight: bold;" +
                        (item
                            ? "-fx-text-fill: #16a34a;"
                            : "-fx-text-fill: #dc2626;")
                    );
                }
            }
        });

        TableColumn<Utilisateur, LocalDate> colDate = new TableColumn<>("Date de création");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        styleColumn(colDate);

        table.getColumns().addAll(colId, colNom, colPrenom, colLogin, colNiveau, colActif, colDate);

        ObservableList<Utilisateur> data = FXCollections.observableArrayList();
        try {
            data.addAll(new UtilisateurRepository().findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(data);

        table.setRowFactory(tv -> {
            TableRow<Utilisateur> row = new TableRow<>();
            row.setStyle("-fx-background-color: #ffffff;");
            row.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    row.setStyle("-fx-background-color: #eef2ff;");
                } else {
                    row.setStyle("-fx-background-color: #ffffff;");
                }
            });
            return row;
        });

        VBox tableWrapper = new VBox(table);
        tableWrapper.setPadding(new Insets(0, 32, 28, 32));
        VBox.setVgrow(table, Priority.ALWAYS);

        VBox content = new VBox(header, tableWrapper);
        VBox.setVgrow(tableWrapper, Priority.ALWAYS);

        root.setCenter(content);
        return root;
    }

    private static <S, T> void styleColumn(TableColumn<S, T> col) {
        col.setStyle("-fx-alignment: CENTER-LEFT;");
    }
}