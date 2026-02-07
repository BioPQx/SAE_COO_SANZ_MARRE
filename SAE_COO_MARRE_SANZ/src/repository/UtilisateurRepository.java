package repository;

import model.Utilisateur;
import utils.CsvUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepository {

    private static final String FILE = "data/utilisateurs.csv";
    private static final String HEADER =
            "id_utilisateur;nom;prenom;login;niveau_autorisation;actif;date_creation";

    public List<Utilisateur> findAll() throws IOException {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        for (String[] row : CsvUtils.read(FILE)) {
            utilisateurs.add(new Utilisateur(
                    Integer.parseInt(row[0]),
                    row[1],
                    row[2],
                    row[3],
                    Integer.parseInt(row[4]),
                    Boolean.parseBoolean(row[5]),
                    LocalDate.parse(row[6].trim())

            ));
        }
        return utilisateurs;
    }

    public void save(Utilisateur u) throws IOException {
        List<String[]> rows = new ArrayList<>();

        for (Utilisateur user : findAll()) {
            rows.add(toRow(user));
        }

        rows.add(toRow(u));
        CsvUtils.write(FILE, HEADER, rows);
    }

    private String[] toRow(Utilisateur u) {
        return new String[]{
                String.valueOf(u.getId()),
                u.getNom(),
                u.getPrenom(),
                u.getLogin(),
                String.valueOf(u.getNiveauAutorisation()),
                String.valueOf(u.isActif()),
                u.getDateCreation().toString()
        };
    }
    
    public void delete(int id) throws IOException {
        File file = new File(FILE);
        if (!file.exists()) return;

        List<String> lignes = new ArrayList<>();
        String entete = null;

        // Lire le fichier ligne par ligne
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (entete == null) {
                    entete = line; // première ligne = entête
                } else {
                    String[] row = line.split(";");
                    if (row.length > 0) {
                        try {
                            int currentId = Integer.parseInt(row[0].trim());
                            if (currentId != id) {
                                lignes.add(line); // conserver la ligne
                            }
                        } catch (NumberFormatException e) {
                            lignes.add(line); // conserver ligne invalide
                        }
                    }
                }
            }
        }

        // Réécrire le fichier
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            if (entete != null) {
                bw.write(entete);
                bw.newLine();
            }
            for (String l : lignes) {
                bw.write(l);
                bw.newLine();
            }
        }
    }
    
    public void update(Utilisateur utilisateur) throws IOException {

        File file = new File(FILE);
        if (!file.exists()) return;

        List<String> lignes = new ArrayList<>();
        String entete = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {

                if (entete == null) {
                    entete = line;
                    continue;
                }

                String[] row = line.split(";");
                if (row.length == 0) continue;

                int id;
                try {
                    id = Integer.parseInt(row[0].trim());
                } catch (NumberFormatException e) {
                    lignes.add(line);
                    continue;
                }

                if (id == utilisateur.getId()) {
                    lignes.add(utilisateur.toCsvLine());
                } else {
                    lignes.add(line);
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(entete);
            bw.newLine();

            for (String l : lignes) {
                bw.write(l);
                bw.newLine();
            }
        }
    }

    public int getNextId() throws IOException {
        return findAll().stream()
                .mapToInt(Utilisateur::getId)
                .max()
                .orElse(0) + 1;
    }
    
    public boolean loginExiste(String login, int idCourant) throws IOException {
        return this.findAll().stream()
                .anyMatch(u ->
                    u.getLogin().equalsIgnoreCase(login)
                    && u.getId() != idCourant
                );
    }

    public Utilisateur findById(int id) throws IOException {

        return findAll().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }


}

