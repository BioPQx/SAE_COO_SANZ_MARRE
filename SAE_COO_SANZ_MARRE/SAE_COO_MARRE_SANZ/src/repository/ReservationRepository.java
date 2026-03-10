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
}