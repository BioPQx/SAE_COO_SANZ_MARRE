package model;

public class SessionUtilisateur {

    private static Utilisateur utilisateurConnecte;

    public static void connecter(Utilisateur u) {
        utilisateurConnecte = u;
    }

    public static Utilisateur get() {
        return utilisateurConnecte;
    }

    public static int getNiveau() {
        return utilisateurConnecte.getNiveauAutorisation();
    }

    public static boolean estConnecte() {
        return utilisateurConnecte != null;
    }

    public static void deconnecter() {
        utilisateurConnecte = null;
    }
}
