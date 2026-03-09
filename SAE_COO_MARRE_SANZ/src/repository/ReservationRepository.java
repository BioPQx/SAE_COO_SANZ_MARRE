package repository;

import model.Reservation;
import utils.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private static final String FILE = "data/reservations.csv";

    public List<Reservation> findAll() {

        List<Reservation> list = new ArrayList<>();

        try {

            for (String[] row : CsvUtils.read(FILE)) {

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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public void save(Reservation res) throws IOException {
        List<String[]> rows = CsvUtils.read(FILE); // lire le CSV existant
        if (rows == null) rows = new ArrayList<>();

        // Ajouter la nouvelle réservation en respectant l'ordre du CSV
        rows.add(new String[] {
            String.valueOf(res.getId()),
            res.getDomaine(),
            res.getRessource(),
            res.getDescription(),
            res.getHeureDuree(),
            res.getType(),
            res.getDerniereMaj()
        });

        CsvUtils.write(FILE, "Réservation au nom de ;Domaines;Ressource ;Description :;Heure - Durée :;Type;Dernière mise à jour", rows);
    }

    // Méthode utilitaire pour transformer une Reservation en ligne CSV
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