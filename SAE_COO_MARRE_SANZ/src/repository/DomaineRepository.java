package repository;

import model.Domaine;
import utils.CsvUtils;

import java.util.ArrayList;
import java.util.List;

public class DomaineRepository {

    private static final String FILE = "data/domaines.csv";
    private static final String HEADER = "id;libelle";

    public List<Domaine> findAll() {

        List<Domaine> list = new ArrayList<>();

        try {

            for(String[] row : CsvUtils.read(FILE)){
                list.add(new Domaine(
                        Integer.parseInt(row[0]),
                        row[1]
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void save(Domaine domaine) {

        try {

            List<String[]> rows = CsvUtils.read(FILE);

            rows.add(new String[]{
                    String.valueOf(domaine.getId()),
                    domaine.getLibelle()
            });

            CsvUtils.write(FILE, HEADER, rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}