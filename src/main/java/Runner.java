import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by matt on 1/2/2016.
 */
public class Runner {
  public static <S, T> void run(Solution<S, T> s) throws IOException {
    String problemName = s.getClass().getSimpleName().toLowerCase();
    String inputName = "in/rosalind_" + problemName + ".txt";
    String outputName = "out/" + problemName + ".txt";
    S input = s.parseInput(Files.readAllLines(Paths.get(inputName)));
    T output = s.process(input);
    String formattedOutput = s.formatOutput(output);
    System.out.println(formattedOutput);
    File outputFile = new File(outputName);
    outputFile.getParentFile().mkdirs();
    try (PrintWriter out = new PrintWriter(outputFile)) {
      out.println(formattedOutput);
    }
  }
}
