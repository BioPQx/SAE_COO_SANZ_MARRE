package view;

import model.Utilisateur;
import repository.UtilisateurRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;

public class AfficherUtilisateurView {

    public static BorderPane create() {
        BorderPane root = new BorderPane();

        TableView<Utilisateur> table = new TableView<>();

        TableColumn<Utilisateur, Integer> colId =
                new TableColumn<>("ID");
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Utilisateur, String> colNom =
                new TableColumn<>("Nom");
        colNom.setCellValueFactory(
                new PropertyValueFactory<>("nom"));

        TableColumn<Utilisateur, String> colPrenom =
                new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));

        TableColumn<Utilisateur, String> colLogin =
                new TableColumn<>("Login");
        colLogin.setCellValueFactory(
                new PropertyValueFactory<>("login"));

        TableColumn<Utilisateur, Integer> colNiveau =
                new TableColumn<>("Niveau");
        colNiveau.setCellValueFactory(
                new PropertyValueFactory<>("niveauAutorisation"));
        
        TableColumn<Utilisateur, Boolean> colActif = 
        		new TableColumn<>("Actif");
        colActif.setCellValueFactory(
        		new PropertyValueFactory<>("actif"));
        colActif.setPrefWidth(70);
        colActif.setMaxWidth(80);
        colActif.setMinWidth(60);

        TableColumn<Utilisateur, LocalDate> colDate = 
        		 new TableColumn<>("Date de création");
        colDate.setCellValueFactory(
        		new PropertyValueFactory<>("dateCreation"));
        
        table.getColumns().addAll(colId, colNom, colPrenom, colLogin, colNiveau, colActif, colDate);

        // Chargement des données CSV
        ObservableList<Utilisateur> data = FXCollections.observableArrayList();
        try {
            data.addAll(new UtilisateurRepository().findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setItems(data);

        root.setCenter(table);
        return root;

    }
}
