package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Parametres;
import repository.ParametresRepository;

public class ParametresView {

    private static final String[] THEMES = {"Clair", "Sombre"};
    private static final String[] COULEURS = {"Bleu", "Rouge", "Vert", "Orange", "Violet"};

    public static VBox create() {

        ParametresRepository repo = new ParametresRepository();
        Parametres p = repo.load();

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("Paramètres de l'application");
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // --- Champs ---
        TextField tfSeuilAlerte = new TextField(String.valueOf(p.getSeuilAlerteDefaut()));
        TextField tfDureeReservation = new TextField(String.valueOf(p.getDureeReservationDefaut()));
        TextField tfMaxReservations = new TextField(String.valueOf(p.getMaxReservations()));

        ComboBox<String> cbTheme = new ComboBox<>();
        cbTheme.getItems().addAll(THEMES);
        cbTheme.setValue(p.getTheme());

        ComboBox<String> cbCouleur = new ComboBox<>();
        cbCouleur.getItems().addAll(COULEURS);
        cbCouleur.setValue(p.getCouleur());

        CheckBox chkAnimations = new CheckBox("Activer animations");
        chkAnimations.setSelected(p.isAnimations());

        CheckBox chkAlertes = new CheckBox("Alertes stock");
        chkAlertes.setSelected(p.isAlertesStock());

        CheckBox chkConfirmation = new CheckBox("Confirmation suppression");
        chkConfirmation.setSelected(p.isConfirmationSuppression());

        CheckBox chkStats = new CheckBox("Afficher stats au démarrage");
        chkStats.setSelected(p.isStatsDemarrage());

        // --- Ajout au GridPane ---
        grid.addRow(0, new Label("Seuil alerte :"), tfSeuilAlerte);
        grid.addRow(1, new Label("Durée réservation (jours) :"), tfDureeReservation);
        grid.addRow(2, new Label("Max réservations :"), tfMaxReservations);
        grid.addRow(3, new Label("Thème :"), cbTheme);
        grid.addRow(4, new Label("Couleur principale :"), cbCouleur);
        grid.addRow(5, new Label("Animations :"), chkAnimations);
        grid.addRow(6, new Label("Alertes stock :"), chkAlertes);
        grid.addRow(7, new Label("Confirmation suppression :"), chkConfirmation);
        grid.addRow(8, new Label("Afficher stats au démarrage :"), chkStats);

        Button btnSauvegarder = new Button("Sauvegarder");
        btnSauvegarder.setOnAction(e -> {
            try {
                // Vérifications simples
                int seuil = Integer.parseInt(tfSeuilAlerte.getText().trim());
                int duree = Integer.parseInt(tfDureeReservation.getText().trim());
                int maxRes = Integer.parseInt(tfMaxReservations.getText().trim());

                p.setSeuilAlerteDefaut(seuil);
                p.setDureeReservationDefaut(duree);
                p.setMaxReservations(maxRes);
                p.setTheme(cbTheme.getValue());
                p.setCouleur(cbCouleur.getValue());
                p.setAnimations(chkAnimations.isSelected());
                p.setAlertesStock(chkAlertes.isSelected());
                p.setConfirmationSuppression(chkConfirmation.isSelected());
                p.setStatsDemarrage(chkStats.isSelected());

                repo.save(p);

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Paramètres sauvegardés avec succès !");
                alert.showAndWait();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Seuil, durée et max réservations doivent être des nombres !");
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Erreur lors de la sauvegarde des paramètres !");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(titre, grid, btnSauvegarder);
        return root;
    }
}