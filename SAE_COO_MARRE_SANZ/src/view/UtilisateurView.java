package view;

import model.Utilisateur;
import repository.AuthentificationRepository;
import repository.UtilisateurRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;



import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UtilisateurView {

    public static VBox create() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titre = new Label("Rechercher");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Button btnCreer = new Button("Nouvel utilisateur");
        
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        topBar.getChildren().addAll(titre, spacer, btnCreer);



        // ---- Champs de recherche ----
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfId = new TextField();
        TextField tfNom = new TextField();
        TextField tfPrenom = new TextField();
        TextField tfLogin = new TextField();
        TextField tfniveau = new TextField();
        TextField tfActif = new TextField();


        grid.addRow(0, new Label("ID :"), tfId);
        grid.addRow(1, new Label("Nom :"), tfNom);
        grid.addRow(2, new Label("Prénom :"), tfPrenom);
        grid.addRow(3, new Label("Login :"), tfLogin);
        grid.addRow(4, new Label("Niveau :"), tfniveau);
        grid.addRow(5, new Label("Actif :"), tfActif);

        Button btnRecherche = new Button("Rechercher");

        root.getChildren().addAll(topBar, grid, btnRecherche);

        // ---- TableView pour les résultats ----
        TableView<Utilisateur> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Utilisateur, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMinWidth(20);
        colId.setMaxWidth(60);

        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Utilisateur, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Utilisateur, String> colLogin = new TableColumn<>("Login");
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        
        TableColumn<Utilisateur, String> colNiveau = new TableColumn<>("Niveau d'autorisation");
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveauAutorisation"));
        
        TableColumn<Utilisateur, String> colActif = new TableColumn<>("Actif");
        colActif.setCellValueFactory(new PropertyValueFactory<>("actif"));

        TableColumn<Utilisateur, String> colDate = new TableColumn<>("Date Création");
        colDate.setCellValueFactory(u ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        u.getValue().getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        );

        TableColumn<Utilisateur, Void> colAction = new TableColumn<>("Actions");

        colAction.setCellFactory(tc -> new TableCell<>() {

            private final Button btnSupprimer = new Button("Supprimer");
            private final Button btnModifier = new Button("Modifier");
            private final HBox box = new HBox(5, btnModifier, btnSupprimer);

            {
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
                    confirm.setHeaderText("Supprimer l'utilisateur " +
                            user.getNom() + " " + user.getPrenom() + " ?");
                    confirm.setContentText("Cette action est irréversible.");

                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                            	UtilisateurRepository userRepo = new UtilisateurRepository();
                            	AuthentificationRepository authRepo = new AuthentificationRepository();

                            	userRepo.delete(user.getId());
                            	authRepo.deleteByUserId(user.getId());

                            	getTableView().getItems().remove(user);


                                Alert info = new Alert(Alert.AlertType.INFORMATION,
                                        "Utilisateur supprimé !");
                                info.showAndWait();

                            } catch (IOException ex) {
                                ex.printStackTrace();
                                Alert alert = new Alert(Alert.AlertType.ERROR,
                                        "Erreur lors de la suppression !");
                                alert.showAndWait();
                            }
                        }
                    });
                });
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }
            }
        });

        table.getColumns().addAll(colId, colNom, colPrenom, colLogin, colNiveau, colActif, colDate, colAction);

        root.getChildren().add(table);

        // ---- Recherche ----
        btnRecherche.setOnAction(e -> {
            try {
                List<Utilisateur> allUsers = new UtilisateurRepository().findAll();

                ObservableList<Utilisateur> filtered = FXCollections.observableArrayList(
                        allUsers.stream()
                        .filter(u -> {
                            boolean match = true;

                            if (!tfId.getText().trim().isEmpty()) {
                                try {
                                    match &= u.getId() == Integer.parseInt(tfId.getText().trim());
                                } catch (NumberFormatException ex) {
                                    return false;
                                }
                            }

                            if (!tfNom.getText().trim().isEmpty())
                                match &= u.getNom().toLowerCase().contains(tfNom.getText().trim().toLowerCase());

                            if (!tfPrenom.getText().trim().isEmpty())
                                match &= u.getPrenom().toLowerCase().contains(tfPrenom.getText().trim().toLowerCase());

                            if (!tfLogin.getText().trim().isEmpty())
                                match &= u.getLogin().toLowerCase().contains(tfLogin.getText().trim().toLowerCase());

                            // ---- Actif ----
                            if (!tfActif.getText().trim().isEmpty()) {
                                String actif = tfActif.getText().trim().toLowerCase();
                                if (actif.equals("oui") || actif.equals("true")) {
                                    match &= u.isActif();
                                } else if (actif.equals("non") || actif.equals("false")) {
                                    match &= !u.isActif();
                                } else {
                                    return false;
                                }
                            }

                            // ---- Niveau d'autorisation ----
                            if (!tfniveau.getText().trim().isEmpty()) {
                                try {
                                    int niveau = Integer.parseInt(tfniveau.getText().trim());
                                    match &= u.getNiveauAutorisation() == niveau;
                                } catch (NumberFormatException ex) {
                                    return false;
                                }
                            }

                            return match;
                        })

                     .collect(Collectors.toList())
                );

                table.setItems(filtered);

            } catch (IOException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la lecture des utilisateurs !");
                alert.showAndWait();
            }
        });
        
        // ---- Création d'un utilisateur ----
        btnCreer.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Créer un utilisateur");
            CreerUtilisateurView.show(stage, table.getItems());
            stage.show();
        });

        btnRecherche.fire();

        return root;
    }
}
