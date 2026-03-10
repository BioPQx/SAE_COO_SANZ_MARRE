package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Utilisateur;
import repository.AuthentificationRepository;
import repository.UtilisateurRepository;

import java.io.IOException;

public class ModifierUtilisateurView {
	
	public static void show(Stage stage, Utilisateur utilisateur, ObservableList<Utilisateur> tableItems) {
	    VBox root = new VBox(10);
	    root.setPadding(new Insets(20));

	    
	    TextField tfId = new TextField(String.valueOf(utilisateur.getId()));
        tfId.setDisable(true);
        
	    TextField tfNom = new TextField(utilisateur.getNom());
	    TextField tfPrenom = new TextField(utilisateur.getPrenom());
	    TextField tfLogin = new TextField(utilisateur.getLogin());
	    TextField tfniveau = new TextField(String.valueOf(utilisateur.getNiveauAutorisation()));
	    CheckBox cbActif = new CheckBox();
	    cbActif.setSelected(utilisateur.isActif());
	    
	    DatePicker dpDateCreation = new DatePicker(utilisateur.getDateCreation());
        dpDateCreation.setDisable(true);

	    Button btnEnregistrer = new Button("Enregistrer");
	    Button btnResetPwd = new Button("Réinitialiser mot de passe");


	    root.getChildren().addAll(
	    	new Label("Id :"), tfId,
	        new Label("Nom :"), tfNom,
	        new Label("Prénom :"), tfPrenom,
	        new Label("Login :"), tfLogin,
	        new Label("Niveau d'autorisation :"), tfniveau,
	        new Label("Actif :"), cbActif,
	        new Label("Date de création :"), dpDateCreation,
	        btnEnregistrer,
	        btnResetPwd
	    );

	    btnEnregistrer.setOnAction(e -> {
	        // Mise à jour de l'utilisateur
	        utilisateur.setNom(tfNom.getText().trim());
	        utilisateur.setPrenom(tfPrenom.getText().trim());
	        utilisateur.setLogin(tfLogin.getText().trim());
	        utilisateur.setNiveauAutorisation(Integer.parseInt(tfniveau.getText().trim()));
	        utilisateur.setActif(cbActif.isSelected());
	        
	        UtilisateurRepository repo = new UtilisateurRepository();
	    	try {
				if (repo.loginExiste(tfLogin.getText().trim(), Integer.parseInt(tfId.getText().trim()))) {
				    new Alert(Alert.AlertType.WARNING, "Ce login existe déjà !").showAndWait();
				    return;
				}
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}

	        try {
	            new UtilisateurRepository().update(utilisateur);

	            if (tableItems != null) {
	                int index = tableItems.indexOf(utilisateur);
	                if (index >= 0) {
	                    tableItems.set(index, utilisateur); // met à jour la ligne
	                }
	            }

	            stage.close(); // fermeture de la fenêtre
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour !");
	            alert.showAndWait();
	        }
	    });
	    
	    
	    btnResetPwd.setOnAction(e -> {
	        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	        confirm.setTitle("Réinitialiser mot de passe");
	        confirm.setHeaderText("Réinitialiser le mot de passe de " + utilisateur.getNom() + " ?");
	        confirm.setContentText("Le mot de passe sera remis à 'Iut2026'.");
	        
	        confirm.showAndWait().ifPresent(response -> {
	            if(response == ButtonType.OK){
	                try {
	                    AuthentificationRepository authRepo = new AuthentificationRepository();
	                    authRepo.updatePassword(utilisateur.getId(), "Iut2026"); // mot de passe par défaut

	                    Alert info = new Alert(Alert.AlertType.INFORMATION,
	                            "Mot de passe réinitialisé pour " + utilisateur.getLogin());
	                    info.showAndWait();

	                } catch(Exception ex){
	                    ex.printStackTrace();
	                    Alert alert = new Alert(Alert.AlertType.ERROR,
	                            "Erreur lors de la réinitialisation du mot de passe !");
	                    alert.showAndWait();
	                }
	            }
	        });
	    });

	    Scene scene = new Scene(root, 400, 500);
	    stage.setScene(scene);
	    stage.show();
	}
	
}