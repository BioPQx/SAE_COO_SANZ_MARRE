package repository;

import model.Stock;
import utils.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockRepository {

    private static final String FILE = "data/stocks.csv";
    private static final String HEADER =
            "id;Ressource;description;etat;Nombre;quantite_disponible;seuil_alerte;actif;statut";

    public List<Stock> findAll() {

        List<Stock> list = new ArrayList<>();

        try {

            for (String[] row : CsvUtils.read(FILE)) {

                int id = Integer.parseInt(row[0]);
                String ressource = row[1];
                String description = row[2];
                String etat = row[3];
                int nombre = Integer.parseInt(row[4]);
                int quantite = Integer.parseInt(row[5]);
                int seuil = Integer.parseInt(row[6]);
                boolean actif = Boolean.parseBoolean(row[7]);
                String statut = row[8];

                list.add(new Stock(
                        id,
                        ressource,
                        description,
                        etat,
                        nombre,
                        quantite,
                        seuil,
                        actif,
                        statut
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void save(Stock stock) throws IOException {

        List<String[]> rows = new ArrayList<>();

        for (Stock s : findAll()) {
            rows.add(toRow(s));
        }

        rows.add(toRow(stock));

        CsvUtils.write(FILE, HEADER, rows);
    }

    public void update(Stock stock) throws IOException {

        List<String[]> rows = new ArrayList<>();

        for (Stock s : findAll()) {

            if (s.getId() == stock.getId()) {
                rows.add(toRow(stock));
            } else {
                rows.add(toRow(s));
            }
        }

        CsvUtils.write(FILE, HEADER, rows);
    }

    private String[] toRow(Stock s) {

        return new String[]{
                String.valueOf(s.getId()),
                s.getRessource(),
                s.getDescription(),
                s.getEtat(),
                String.valueOf(s.getNombre()),
                String.valueOf(s.getQuantiteDisponible()),
                String.valueOf(s.getSeuilAlerte()),
                String.valueOf(s.isActif()),
                s.getStatut()
        };
    }
    
    public int getNextId() {

        int max = 0;

        for (Stock s : findAll()) {
            if (s.getId() > max) {
                max = s.getId();
            }
        }

        return max + 1;
    }
}