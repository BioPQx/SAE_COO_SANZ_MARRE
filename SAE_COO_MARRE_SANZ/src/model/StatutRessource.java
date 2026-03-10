package model;

public enum StatutRessource {

    DISPONIBLE("DISPONIBLE"),
    EMPRUNTEE("EMPRUNTEE"),
    EN_MAINTENANCE("EN_MAINTENANCE");
	
	private final String label;

    StatutRessource(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
