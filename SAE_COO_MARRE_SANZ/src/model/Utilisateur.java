package model;

import java.time.LocalDate;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String login;
    private int niveauAutorisation;
    private boolean actif;
    private LocalDate dateCreation;

    public Utilisateur(int id, String nom, String prenom, String login,
                       int niveauAutorisation, boolean actif, LocalDate dateCreation) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.niveauAutorisation = niveauAutorisation;
        this.actif = actif;
        this.dateCreation = dateCreation;
    }

    // Getters & setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getLogin() { return login; }
    public int getNiveauAutorisation() { return niveauAutorisation; }
    public boolean isActif() { return actif; }
    public LocalDate getDateCreation() { return dateCreation; }
    
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setLogin(String login) { this.login = login; }
    public void setNiveauAutorisation(int niveauAutorisation) {
        this.niveauAutorisation = niveauAutorisation;
    }
    public void setActif(boolean actif) { this.actif = actif; }
    
    public String toCsvLine() {
        return id + ";" +
               nom + ";" +
               prenom + ";" +
               login + ";" +
               niveauAutorisation + ";" +
               actif + ";" +
               dateCreation.toString();
    }


}
