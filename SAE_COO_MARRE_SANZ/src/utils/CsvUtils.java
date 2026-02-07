package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    private static final String SEPARATOR = ";";

    public static List<String[]> read(String path) throws IOException {
        File file = new File(path);
        List<String[]> data = new ArrayList<>();

        if (!file.exists()) {
            return data;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { // ignorer l'en-tête
                    firstLine = false;
                    continue;
                }
                data.add(line.split(SEPARATOR));
            }
        }
        return data;
    }

    public static void write(String path, String header, List<String[]> rows) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println(header);
            for (String[] row : rows) {
                pw.println(String.join(SEPARATOR, row));
            }
        }
    }
}
