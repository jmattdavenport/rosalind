import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/25/2015.
 */
public class RNA {

  public static void main(String[] args) throws FileNotFoundException {
    String className = new Object(){}.getClass().getEnclosingClass().getSimpleName();
    String inputName = "rosalind_" + className.toLowerCase() + ".txt";
    String outputName = "out.txt";
    File inputFile = new File(inputName);
    File outputFile = new File(outputName);
    Scanner in = new Scanner(inputFile);
    PrintWriter out = new PrintWriter(outputFile);
    while (in.hasNextLine()) {
      String output = process(in.nextLine());
      System.out.println(output);
      out.println(output);
    }
    in.close();
    out.close();
  }

  private static String process(String input) {
    return transcribeRNA(input);
  }

  public static String transcribeRNA(String dna) {
    return dna.replaceAll("T", "U");
  }
}
