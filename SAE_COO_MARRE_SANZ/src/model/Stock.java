package model;

public class Stock {

    private int id;
    private String ressource;
    private String description;
    private String etat;
    private int nombre;
    private int quantiteDisponible;
    private int seuilAlerte;
    private boolean actif;
    private String statut;

    public Stock(int id, String ressource, String description, String etat,
                 int nombre, int quantiteDisponible, int seuilAlerte,
                 boolean actif, String statut) {

        this.id = id;
        this.ressource = ressource;
        this.description = description;
        this.etat = etat;
        this.nombre = nombre;
        this.quantiteDisponible = quantiteDisponible;
        this.seuilAlerte = seuilAlerte;
        this.actif = actif;
        this.statut = statut;
    }

    public int getId() { return id; }
    public String getRessource() { return ressource; }
    public String getDescription() { return description; }
    public String getEtat() { return etat; }
    public int getNombre() { return nombre; }
    public int getQuantiteDisponible() { return quantiteDisponible; }
    public int getSeuilAlerte() { return seuilAlerte; }
    public boolean isActif() { return actif; }
    public String getStatut() { return statut; }

    public void setRessource(String ressource) { this.ressource = ressource; }
    public void setDescription(String description) { this.description = description; }
    public void setEtat(String etat) { this.etat = etat; }
    public void setNombre(int nombre) { this.nombre = nombre; }
    public void setQuantiteDisponible(int quantiteDisponible) { this.quantiteDisponible = quantiteDisponible; }
    public void setSeuilAlerte(int seuilAlerte) { this.seuilAlerte = seuilAlerte; }
    public void setActif(boolean actif) { this.actif = actif; }
    public void setStatut(String statut) { this.statut = statut; }
}