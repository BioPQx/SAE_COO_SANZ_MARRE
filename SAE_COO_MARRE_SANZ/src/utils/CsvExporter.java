package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static void export(String filename, String header, List<String[]> rows) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(header + "\n");
            for (String[] row : rows) {
                writer.write(String.join(";", row) + "\n");
            }
        }
    }
}