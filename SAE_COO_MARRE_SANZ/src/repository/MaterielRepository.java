package repository;

import model.Materiel;
import utils.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaterielRepository {

    private static final String FILE = "data/materiel.csv";
    private static final String HEADER =
            "id_materiel;nom;description;etat;quantite_totale;quantite_disponible;seuil_alerte;actif;id_categorie";

    public List<Materiel> findAll() throws IOException {
        List<Materiel> list = new ArrayList<>();

        for (String[] row : CsvUtils.read(FILE)) {
            list.add(new Materiel(
                    Integer.parseInt(row[0]),
                    row[1],
                    row[2],
                    row[3],
                    Integer.parseInt(row[4]),
                    Integer.parseInt(row[5]),
                    Integer.parseInt(row[6]),
                    Boolean.parseBoolean(row[7]),
                    Integer.parseInt(row[8])
            ));
        }
        return list;
    }

    public void saveAll(List<Materiel> materiels) throws IOException {
        List<String[]> rows = new ArrayList<>();
        for (Materiel m : materiels) {
            rows.add(toRow(m));
        }
        CsvUtils.write(FILE, HEADER, rows);
    }

    public void add(Materiel m) throws IOException {
        List<Materiel> list = findAll();
        list.add(m);
        saveAll(list);
    }

    private String[] toRow(Materiel m) {
        return new String[]{
                String.valueOf(m.getId()),
                m.getNom(),
                m.getDescription(),
                m.getEtat(),
                String.valueOf(m.getQuantiteTotale()),
                String.valueOf(m.getQuantiteDisponible()),
                String.valueOf(m.getSeuilAlerte()),
                String.valueOf(m.isActif()),
                String.valueOf(m.getIdCategorie())
        };
    }
}