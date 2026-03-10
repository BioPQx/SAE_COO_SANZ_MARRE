package model;

public class Parametres {

    private int seuilAlerteDefaut;
    private int dureeReservationDefaut;
    private int maxReservations;
    private String theme;
    private String couleur;
    private boolean animations;
    private boolean alertesStock;
    private boolean confirmationSuppression;
    private boolean statsDemarrage;

    public Parametres(int seuilAlerteDefaut, int dureeReservationDefaut, int maxReservations,
                      String theme, String couleur, boolean animations,
                      boolean alertesStock, boolean confirmationSuppression, boolean statsDemarrage) {
        this.seuilAlerteDefaut = seuilAlerteDefaut;
        this.dureeReservationDefaut = dureeReservationDefaut;
        this.maxReservations = maxReservations;
        this.theme = theme;
        this.couleur = couleur;
        this.animations = animations;
        this.alertesStock = alertesStock;
        this.confirmationSuppression = confirmationSuppression;
        this.statsDemarrage = statsDemarrage;
    }

    // ----- GETTERS -----
    public int getSeuilAlerteDefaut() { return seuilAlerteDefaut; }
    public int getDureeReservationDefaut() { return dureeReservationDefaut; }
    public int getMaxReservations() { return maxReservations; }
    public String getTheme() { return theme; }
    public String getCouleur() { return couleur; }
    public boolean isAnimations() { return animations; }
    public boolean isAlertesStock() { return alertesStock; }
    public boolean isConfirmationSuppression() { return confirmationSuppression; }
    public boolean isStatsDemarrage() { return statsDemarrage; }

    // ----- SETTERS -----
    public void setSeuilAlerteDefaut(int seuilAlerteDefaut) { this.seuilAlerteDefaut = seuilAlerteDefaut; }
    public void setDureeReservationDefaut(int dureeReservationDefaut) { this.dureeReservationDefaut = dureeReservationDefaut; }
    public void setMaxReservations(int maxReservations) { this.maxReservations = maxReservations; }
    public void setTheme(String theme) { this.theme = theme; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setAnimations(boolean animations) { this.animations = animations; }
    public void setAlertesStock(boolean alertesStock) { this.alertesStock = alertesStock; }
    public void setConfirmationSuppression(boolean confirmationSuppression) { this.confirmationSuppression = confirmationSuppression; }
    public void setStatsDemarrage(boolean statsDemarrage) { this.statsDemarrage = statsDemarrage; }
}