package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour importer des fichiers CSV.
 */
public class CsvImporter {

    /**
     * Lit un fichier CSV et renvoie toutes les lignes sous forme de liste de tableaux de chaînes.
     * Chaque tableau représente une ligne, avec les colonnes séparées.
     *
     * @param filePath le chemin du fichier CSV
     * @return liste des lignes du CSV
     * @throws IOException en cas d'erreur de lecture
     */
    public static List<String[]> readCsv(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Ignorer la première ligne si c'est un en-tête
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    // Décommenter si tu veux garder l'en-tête
                    // rows.add(line.split(";"));
                    continue;
                }

                // Découper la ligne en colonnes ; séparateur ';'
                String[] columns = line.split(";");
                rows.add(columns);
            }
        }

        return rows;
    }
}