
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "word_frequency.txt";

        HashMap<String, Integer> frequency = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(inputFile))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase()
                        .split("[^a-z0-9']+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        frequency.put(
                            word,
                            frequency.getOrDefault(word, 0) + 1
                        );
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(outputFile))) {

                for (Map.Entry<String, Integer> entry
                        : frequency.entrySet()) {
                    bw.write(entry.getKey() + " : " + entry.getValue());
                    bw.newLine();
                }
            }

            System.out.println("Word frequency calculated successfully.");
            System.out.println("Output saved to " + outputFile);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
            System.out.println(
                "Make sure input.txt exists in the working directory."
            );
        }
    }
}
