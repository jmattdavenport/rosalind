import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Matt on 11/28/2015.
 */
public class SUBS {

  public static void main(String[] args) throws FileNotFoundException {
    String className = new Object() {}.getClass().getEnclosingClass().getSimpleName();
    String inputName = "rosalind_" + className.toLowerCase() + ".txt";
    String outputName = "out.txt";
    File inputFile = new File(inputName);
    File outputFile = new File(outputName);
    Scanner in = new Scanner(inputFile);
    PrintWriter out = new PrintWriter(outputFile);
    while (in.hasNextLine()) {
      String output = process(in.nextLine(), in.nextLine());
      System.out.println(output);
      out.println(output);
    }
    in.close();
    out.close();
  }

  private static String process(String line1, String line2) {
    return substringLocations(line1, line2).stream().map(String::valueOf).collect(Collectors.joining(" "));
  }

  private static List<Integer> substringLocations(String superstring, String substring) {
    List<Integer> locations = new ArrayList<>();
    int i = 0;
    while (i < superstring.length()) {
      int nextLocation =  superstring.indexOf(substring, i);
      if (nextLocation < 0) {
        break;
      } else {
        locations.add(nextLocation + 1);
        i = nextLocation + 1;
      }
    }
    return locations;
  }
}
