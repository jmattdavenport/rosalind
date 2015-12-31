import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/25/2015.
 */
public class IPRB {

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
    long k = in.nextInt();
    long m = in.nextInt();
    long n = in.nextInt();
    double probability = dominantOffsprintProbability(k, m, n);
    return String.format("%.5f", probability);
  }

  private static double dominantOffsprintProbability(long k, long m, long n) {
    double dominant = numPairs(k);
    dominant += k * (m + n);
    dominant += 3.0 * numPairs(m) / 4;
    dominant += m * n / 2.0;
    System.out.println(dominant);
    long total = numPairs(k + m + n);
    System.out.println(total);
    return dominant * 1.0 / total;
  }

  private static long numPairs(long n) {
    return n * (n - 1) / 2;
  }
}
