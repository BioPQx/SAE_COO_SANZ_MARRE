package model;

public class Ressource {

    private int id;
    private String nom;
    private Domaine domaine;
    private StatutRessource statut;
    private String libelle;
    private String description;

    public Ressource(int id, String nom, Domaine domaine, StatutRessource statut) {
        this.id = id;
        this.nom = nom;
        this.domaine = domaine;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public StatutRessource getStatut() {
        return statut;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public void setStatut(StatutRessource statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return nom;
    }
    
    public boolean estDisponible() {
        return statut == StatutRessource.DISPONIBLE;
    }

    public void emprunter() {
        statut = StatutRessource.EMPRUNTEE;
    }

    public void retourner() {
        statut = StatutRessource.DISPONIBLE;
    }
    
    public String getLibelle() { return libelle; }
    public String getDescription() { return description; }
}