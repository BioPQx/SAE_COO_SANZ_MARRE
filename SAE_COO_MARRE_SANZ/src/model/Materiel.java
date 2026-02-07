package model;

public class Materiel {

    private int id;
    private String nom;
    private String description;
    private String etat; // neuf, bon, use, hs
    private int quantiteTotale;
    private int quantiteDisponible;
    private int seuilAlerte;
    private boolean actif;
    private int idCategorie;

    public Materiel(int id, String nom, String description, String etat,
                    int quantiteTotale, int quantiteDisponible,
                    int seuilAlerte, boolean actif, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.quantiteTotale = quantiteTotale;
        this.quantiteDisponible = quantiteDisponible;
        this.seuilAlerte = seuilAlerte;
        this.actif = actif;
        this.idCategorie = idCategorie;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getEtat() { return etat; }
    public int getQuantiteTotale() { return quantiteTotale; }
    public int getQuantiteDisponible() { return quantiteDisponible; }
    public int getSeuilAlerte() { return seuilAlerte; }
    public boolean isActif() { return actif; }
    public int getIdCategorie() { return idCategorie; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setEtat(String etat) { this.etat = etat; }
    public void setQuantiteTotale(int quantiteTotale) { this.quantiteTotale = quantiteTotale; }
    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }
    public void setSeuilAlerte(int seuilAlerte) { this.seuilAlerte = seuilAlerte; }
    public void setActif(boolean actif) { this.actif = actif; }
}

