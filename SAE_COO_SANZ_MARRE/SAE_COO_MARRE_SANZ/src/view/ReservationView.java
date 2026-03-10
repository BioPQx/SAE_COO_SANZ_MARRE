package view;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.beans.property.SimpleStringProperty;

import model.Reservation;
import repository.ReservationRepository;

public class ReservationView {

    public static BorderPane create() {

        BorderPane pane = new BorderPane();

        Label titre = new Label("Liste des réservations");

        TableView<Reservation> table = new TableView<>();

        TableColumn<Reservation, String> colNom = new TableColumn<>("Réservation au nom de");
        colNom.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Reservation, String> colDomaine = new TableColumn<>("Domaines");
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
                colNom,
                colDomaine,
                colRessource,
                colDescription,
                colHeure,
                colType,
                colMaj
        );

        ReservationRepository repo = new ReservationRepository();
        table.setItems(FXCollections.observableArrayList(repo.findAll()));

        pane.setTop(titre);
        pane.setCenter(table);

        return pane;
    }
}