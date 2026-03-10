package repository;

import model.Reservation;
import utils.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private static final String FILE = "data/reservations.csv";
    private static final String HEADER = "Réservation au nom de ;Domaines :;Ressource :;Description :;Heure - Durée :;Type;Dernière mise à jour";

    public List<Reservation> findAll() {
        List<Reservation> list = new ArrayList<>();
        try {
            List<String[]> rows = CsvUtils.read(FILE);
            if (rows == null) return list;

            for (String[] row : rows) {
                if (row.length < 7) continue; // ignorer les lignes mal formées

                try {
                    int id = Integer.parseInt(row[0].trim());
                    String domaine = row[1].trim();
                    String ressource = row[2].trim();
                    String description = row[3].trim();
                    String heureDuree = row[4].trim();
                    String type = row[5].trim();
                    String derniereMaj = row[6].trim();

                    Reservation r = new Reservation(
                            id,
                            domaine,
                            ressource,
                            description,
                            heureDuree,
                            type,
                            derniereMaj
                    );

                    list.add(r);
                } catch (NumberFormatException ex) {
                    // ignorer les lignes où l'ID n'est pas un entier
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save(Reservation res) throws IOException {
        List<String[]> rows = CsvUtils.read(FILE);
        if (rows == null) rows = new ArrayList<>();

        // ajouter la nouvelle réservation
        rows.add(toRow(res));

        // réécrire le fichier avec l'en-tête exact
        CsvUtils.write(FILE, HEADER, rows);
    }

    private String[] toRow(Reservation r) {
        return new String[]{
                String.valueOf(r.getId()),
                r.getDomaine(),
                r.getRessource(),
                r.getDescription(),
                r.getHeureDuree(),
                r.getType(),
                r.getDerniereMaj()
        };
    }
    
    public void delete(int id) throws IOException {

        List<Reservation> reservations = findAll();

        reservations.removeIf(r -> r.getId() == id);

        saveAll(reservations);

    }
    
    public void saveAll(List<Reservation> reservations) throws IOException {

        List<String[]> rows = reservations.stream()
                .map(r -> new String[]{
                        String.valueOf(r.getId()),
                        r.getDomaine(),
                        r.getRessource(),
                        r.getDescription(),
                        r.getHeureDuree(),
                        r.getType(),
                        r.getDerniereMaj()
                })
                .toList();

        CsvUtils.write(
                FILE,
                "id;domaine;ressource;description;heure_duree;type;derniere_maj",
                rows
        );

    }
    
    public void update(Reservation reservation) throws IOException {
        // Récupérer toutes les réservations
        List<Reservation> reservations = findAll();

        // Chercher la réservation à mettre à jour
        boolean found = false;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, reservation); // remplacer l'ancienne par la nouvelle
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IOException("Réservation avec ID " + reservation.getId() + " introuvable.");
        }

        // Réécrire le CSV complet
        saveAll(reservations);
    }
}