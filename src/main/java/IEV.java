import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/25/2015.
 */
public class IEV {

  public static void main(String[] args) throws FileNotFoundException {
    String className = new Object() {
    }.getClass().getEnclosingClass().getSimpleName();
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

  public static String process(String input) {
    Scanner in = new Scanner(input);
    return String.valueOf(expectedDominantOffspring(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt()));
  }

  private static double expectedDominantOffspring(int... numCouples) {
    return (numCouples[0] + numCouples[1] + numCouples[2] + 0.75 * numCouples[3] + 0.5 * numCouples[4]) * 2;
  }
}
