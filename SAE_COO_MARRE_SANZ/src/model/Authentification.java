package model;

public class Authentification {

    private int idUtilisateur;
    private String login;
    private String passwordHash;

    public Authentification(int idUtilisateur, String login, String passwordHash) {
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
