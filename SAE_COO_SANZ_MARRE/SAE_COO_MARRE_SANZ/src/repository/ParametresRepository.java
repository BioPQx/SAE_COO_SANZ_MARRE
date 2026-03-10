package repository;

import model.Parametres;
import utils.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParametresRepository {

    private static final String FILE = "data/parametres.csv";

    public Parametres load() {
        Parametres p = getDefault();

        try {
            List<String[]> rows = CsvUtils.read(FILE);
            if (rows == null || rows.isEmpty()) return p;

            for (String[] row : rows) {
                if (row.length < 2) continue;

                String key = row[0].trim();
                String value = row[1].trim();

                switch (key) {
                    case "seuilAlerte" -> p.setSeuilAlerteDefaut(Integer.parseInt(value));
                    case "dureeReservation" -> p.setDureeReservationDefaut(Integer.parseInt(value));
                    case "maxReservations" -> p.setMaxReservations(Integer.parseInt(value));
                    case "theme" -> p.setTheme(value);
                    case "couleur" -> p.setCouleur(value);
                    case "animations" -> p.setAnimations(Boolean.parseBoolean(value));
                    case "alertes" -> p.setAlertesStock(Boolean.parseBoolean(value));
                    case "confirmation" -> p.setConfirmationSuppression(Boolean.parseBoolean(value));
                    case "stats" -> p.setStatsDemarrage(Boolean.parseBoolean(value));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    public void save(Parametres p) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"seuilAlerte", String.valueOf(p.getSeuilAlerteDefaut())});
        rows.add(new String[]{"dureeReservation", String.valueOf(p.getDureeReservationDefaut())});
        rows.add(new String[]{"maxReservations", String.valueOf(p.getMaxReservations())});
        rows.add(new String[]{"theme", p.getTheme()});
        rows.add(new String[]{"couleur", p.getCouleur()});
        rows.add(new String[]{"animations", String.valueOf(p.isAnimations())});
        rows.add(new String[]{"alertes", String.valueOf(p.isAlertesStock())});
        rows.add(new String[]{"confirmation", String.valueOf(p.isConfirmationSuppression())});
        rows.add(new String[]{"stats", String.valueOf(p.isStatsDemarrage())});

        CsvUtils.write(FILE, "parametre;valeur", rows);
    }

    private Parametres getDefault() {
        return new Parametres(
                2,  // seuilAlerte
                2,  // dureeReservation
                5,  // maxReservations
                "Clair", // theme
                "Bleu",  // couleur
                true,    // animations
                true,    // alertes
                true,    // confirmation
                true     // stats
        );
    }
}