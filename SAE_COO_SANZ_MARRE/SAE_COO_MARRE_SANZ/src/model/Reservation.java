package model;

public class Reservation {

    private int id;
    private String domaine;
    private String ressource;
    private String description;
    private String heureDuree;
    private String type;
    private String derniereMaj;

    public Reservation(int id, String domaine, String ressource,
                       String description, String heureDuree,
                       String type, String derniereMaj) {

        this.id = id;
        this.domaine = domaine;
        this.ressource = ressource;
        this.description = description;
        this.heureDuree = heureDuree;
        this.type = type;
        this.derniereMaj = derniereMaj;
    }

    public int getId() {
        return id;
    }

    public String getDomaine() {
        return domaine;
    }

    public String getRessource() {
        return ressource;
    }

    public String getDescription() {
        return description;
    }

    public String getHeureDuree() {
        return heureDuree;
    }

    public String getType() {
        return type;
    }

    public String getDerniereMaj() {
        return derniereMaj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeureDuree(String heureDuree) {
        this.heureDuree = heureDuree;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDerniereMaj(String derniereMaj) {
        this.derniereMaj = derniereMaj;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", domaine='" + domaine + '\'' +
                ", ressource='" + ressource + '\'' +
                ", description='" + description + '\'' +
                ", heureDuree='" + heureDuree + '\'' +
                ", type='" + type + '\'' +
                ", derniereMaj='" + derniereMaj + '\'' +
                '}';
    }
}