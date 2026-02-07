package repository;

import model.Authentification;
import utils.CsvUtils;
import utils.PasswordUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthentificationRepository {

    private static final String FILE = "data/authentification.csv";
    private static final String HEADER = "id;login;passwordHash";

    // Lire tous les comptes
    public List<Authentification> findAll() throws IOException {
        List<Authentification> list = new ArrayList<>();

        for (String[] row : CsvUtils.read(FILE)) {
            list.add(new Authentification(
                    Integer.parseInt(row[0]),
                    row[1],
                    row[2]
            ));
        }
        return list;
    }

    // Trouver par login
    public Authentification findByLogin(String login) throws IOException {
        return findAll().stream()
                .filter(a -> a.getLogin().equalsIgnoreCase(login))
                .findFirst()
                .orElse(null);
    }

    // Création automatique avec mot de passe par défaut
    public void createForUser(int idUtilisateur, String login) throws IOException {

        List<String[]> rows = CsvUtils.read(FILE);

        String hash = PasswordUtils.hashPassword("Iut2026");

        rows.add(new String[]{
                String.valueOf(idUtilisateur),
                login,
                hash
        });

        CsvUtils.write(FILE, HEADER, rows);
    }

    // Mise à jour du mot de passe
    public void updatePassword(int idUtilisateur, String newPassword) throws IOException {

        List<String[]> rows = CsvUtils.read(FILE);

        for (String[] row : rows) {
            if (Integer.parseInt(row[0]) == idUtilisateur) {
                row[2] = PasswordUtils.hashPassword(newPassword);
                break;
            }
        }

        CsvUtils.write(FILE, HEADER, rows);
    }
    
    public void deleteByUserId(int idUtilisateur) throws IOException {

        List<String[]> rows = CsvUtils.read(FILE);

        rows.removeIf(row ->
                Integer.parseInt(row[0]) == idUtilisateur
        );

        CsvUtils.write(FILE, HEADER, rows);
    }

}
