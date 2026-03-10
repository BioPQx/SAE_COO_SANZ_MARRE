package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Parametres;
import repository.ParametresRepository;

public class ParametresView {

    private static final String[] THEMES   = {"Clair", "Sombre"};
    private static final String[] COULEURS = {"Bleu", "Rouge", "Vert", "Orange", "Violet"};

    public static VBox create() {

        ParametresRepository repo = new ParametresRepository();
        Parametres p = repo.load();

        VBox root = new VBox(20);
        root.setPadding(new Insets(28, 32, 28, 32));
        root.setStyle("-fx-background-color: #f3f4f6;");

        // ---- Header ----
        Label titre = new Label("Paramètres de l'application");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Configurez les préférences générales de l'application");
        sousTitre.setFont(Font.font("Segoe UI", 13));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox headerBlock = new VBox(4, titre, sousTitre);

        // ---- Section : Valeurs par défaut ----
        TextField tfSeuilAlerte       = styledField(String.valueOf(p.getSeuilAlerteDefaut()));
        TextField tfDureeReservation  = styledField(String.valueOf(p.getDureeReservationDefaut()));
        TextField tfMaxReservations   = styledField(String.valueOf(p.getMaxReservations()));

        GridPane gridValeurs = new GridPane();
        gridValeurs.setHgap(14);
        gridValeurs.setVgap(12);
        ColumnConstraints lc1 = new ColumnConstraints(220);
        ColumnConstraints fc1 = new ColumnConstraints();
        fc1.setHgrow(Priority.ALWAYS);
        gridValeurs.getColumnConstraints().addAll(lc1, fc1);

        gridValeurs.addRow(0, fieldLabel("Seuil alerte"),                tfSeuilAlerte);
        gridValeurs.addRow(1, fieldLabel("Durée réservation (jours)"),   tfDureeReservation);
        gridValeurs.addRow(2, fieldLabel("Max réservations"),            tfMaxReservations);

        VBox sectionValeurs = sectionCard("⚙️  Valeurs par défaut", gridValeurs);

        // ---- Section : Apparence ----
        ComboBox<String> cbTheme   = styledCombo();
        cbTheme.getItems().addAll(THEMES);
        cbTheme.setValue(p.getTheme());

        ComboBox<String> cbCouleur = styledCombo();
        cbCouleur.getItems().addAll(COULEURS);
        cbCouleur.setValue(p.getCouleur());

        GridPane gridApparence = new GridPane();
        gridApparence.setHgap(14);
        gridApparence.setVgap(12);
        ColumnConstraints lc2 = new ColumnConstraints(220);
        ColumnConstraints fc2 = new ColumnConstraints();
        fc2.setHgrow(Priority.ALWAYS);
        gridApparence.getColumnConstraints().addAll(lc2, fc2);

        gridApparence.addRow(0, fieldLabel("Thème"),             cbTheme);
        gridApparence.addRow(1, fieldLabel("Couleur principale"), cbCouleur);

        VBox sectionApparence = sectionCard("🎨  Apparence", gridApparence);

        // ---- Section : Comportement ----
        CheckBox chkAnimations    = styledCheck("Activer les animations",           p.isAnimations());
        CheckBox chkAlertes       = styledCheck("Alertes de stock",                 p.isAlertesStock());
        CheckBox chkConfirmation  = styledCheck("Confirmation avant suppression",   p.isConfirmationSuppression());
        CheckBox chkStats         = styledCheck("Afficher les stats au démarrage",  p.isStatsDemarrage());

        VBox checksBox = new VBox(10, chkAnimations, chkAlertes, chkConfirmation, chkStats);
        VBox sectionComportement = sectionCard("🔧  Comportement", checksBox);

        // ---- Bouton sauvegarde ----
        Button btnSauvegarder = new Button("Sauvegarder les paramètres");
        btnSauvegarder.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 11 24;" +
            "-fx-cursor: hand;"
        );
        btnSauvegarder.setOnMouseEntered(e -> btnSauvegarder.setStyle(
            "-fx-background-color: #4338ca;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 11 24;" +
            "-fx-cursor: hand;"
        ));
        btnSauvegarder.setOnMouseExited(e -> btnSauvegarder.setStyle(
            "-fx-background-color: #4f46e5;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 11 24;" +
            "-fx-cursor: hand;"
        ));

        HBox btnRow = new HBox(btnSauvegarder);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        btnSauvegarder.setOnAction(e -> {
            try {
                int seuil  = Integer.parseInt(tfSeuilAlerte.getText().trim());
                int duree  = Integer.parseInt(tfDureeReservation.getText().trim());
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
                new Alert(Alert.AlertType.INFORMATION, "Paramètres sauvegardés avec succès !").showAndWait();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.WARNING,
                        "Seuil, durée et max réservations doivent être des nombres !").showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la sauvegarde des paramètres !").showAndWait();
            }
        });

        root.getChildren().addAll(headerBlock, sectionValeurs, sectionApparence, sectionComportement, btnRow);
        return root;
    }

    private static VBox sectionCard(String sectionTitle, javafx.scene.Node content) {
        Label label = new Label(sectionTitle);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        label.setTextFill(Color.web("#374151"));

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(6, 0, 10, 0));

        VBox card = new VBox(0, label, sep, content);
        card.setPadding(new Insets(20, 24, 20, 24));
        card.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;"
        );
        return card;
    }

    private static TextField styledField(String value) {
        TextField tf = new TextField(value);
        tf.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        );
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> tf.setStyle(
            "-fx-background-color: " + (newVal ? "#ffffff" : "#f9fafb") + ";" +
            "-fx-border-color: "     + (newVal ? "#4f46e5" : "#e5e7eb") + ";" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 13px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-text-fill: #111827;"
        ));
        return tf;
    }

    private static ComboBox<String> styledCombo() {
        ComboBox<String> cb = new ComboBox<>();
        cb.setMaxWidth(Double.MAX_VALUE);
        cb.setStyle(
            "-fx-background-color: #f9fafb;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 13px;"
        );
        return cb;
    }

    private static CheckBox styledCheck(String text, boolean selected) {
        CheckBox cb = new CheckBox(text);
        cb.setSelected(selected);
        cb.setFont(Font.font("Segoe UI", 13));
        cb.setTextFill(Color.web("#374151"));
        cb.setStyle("-fx-cursor: hand;");
        return cb;
    }

    private static Label fieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        lbl.setTextFill(Color.web("#374151"));
        lbl.setAlignment(Pos.CENTER_LEFT);
        return lbl;
    }
}