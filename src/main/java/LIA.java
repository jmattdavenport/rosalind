import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 12/2/2015.
 */
public class LIA {

public static void main(String[] args) throws FileNotFoundException {
  String className = new Object(){}.getClass().getEnclosingClass().getSimpleName();
  String inputName = "rosalind_" + className.toLowerCase() + ".txt";
  String outputName = "out.txt";
  File inputFile = new File(inputName);
  File outputFile = new File(outputName);
  try (Scanner in = new Scanner(inputFile); PrintWriter out = new PrintWriter(outputFile)) {
    while (in.hasNextLine()) {
      String output = process(in.nextLine());
      System.out.println(output);
      out.println(output);
    }
  }
}
  
  private static String process(String input) {
    Scanner in = new Scanner(input);
    int k = in.nextInt();
    int n = in.nextInt();
    return String.format("%.5f", probability(k, n));
  }

  private static double probability(int k, int n) {
    double probability = 0;
    int total = (int) Math.pow(2, k);
    double probMatches = 0.25;
    double probDifferent = 1 - probMatches;
    for (int i = n; i <= total; i++) {
      probability += combinations(total, i) * Math.pow(probMatches, i) * Math.pow(probDifferent, total - i);
    }
    return probability;
  }

  private static long[] combinations = null;

  private static long combinations(int n, int k) {
    if (combinations == null) {
      combinations = new long[n / 2 + 1];
      combinations[0] = 1;
    }
    if (k > n / 2) {
      k = n - k;
    }
    if (combinations[k] == 0) {
      combinations[k] = combinations(n, k - 1) * (n - (k - 1)) / k;
    }
    return combinations[k];
  }
}
