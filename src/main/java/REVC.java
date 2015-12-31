import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Matt on 11/25/2015.
 */
public class REVC {

  public static void main(String[] args) throws FileNotFoundException {
    System.out.println(reverseComplement("AGCCATGTAGCTAACTCAGGTTACATGGGGATGACCCCGCGACTTGGATTAGAGTCTCTTTTGGAATAAGCCTGAATGATCCGAGTAGCATCTCAG"));
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
    return reverseComplement(input);
  }

  public static String reverseComplement(String dna) {
    Map<Character, Character> complementBases =
        ImmutableMap.of(
            'A', 'T',
            'T', 'A',
            'C', 'G',
            'G', 'C');

    return new StringBuilder(dna).reverse().toString().chars()
        .mapToObj(num -> (char)num)
        .map(complementBases::get)
        .map(String::valueOf)
        .collect(Collectors.joining());
  }
}
