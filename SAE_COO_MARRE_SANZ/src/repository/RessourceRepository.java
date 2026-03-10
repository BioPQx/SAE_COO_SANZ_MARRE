package repository;

import model.*;
import utils.CsvUtils;

import java.util.ArrayList;
import java.util.List;

public class RessourceRepository {

    private static final String FILE = "data/ressources.csv";

    public List<Ressource> findAll() {

        List<Ressource> list = new ArrayList<>();

        try {

            DomaineRepository domaineRepo = new DomaineRepository();
            List<Domaine> domaines = domaineRepo.findAll();

            for (String[] row : CsvUtils.read(FILE)) {

                int id = Integer.parseInt(row[0]);
                String nom = row[1];
                int domaineId = Integer.parseInt(row[2]);
                StatutRessource statut = StatutRessource.valueOf(row[3]);

                Domaine domaine = domaines.stream()
                        .filter(d -> d.getId() == domaineId)
                        .findFirst()
                        .orElse(null);

                list.add(new Ressource(id, nom, domaine, statut));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}