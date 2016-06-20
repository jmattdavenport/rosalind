package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/26/2015.
 */
public class MRNA {
  private static Multiset<Character> aminoAcidPossibilityCounts = ImmutableMultiset.<Character>builder()
      .addCopies('F', 2).addCopies('L', 6).addCopies('S', 6).addCopies('Y', 2)
      .addCopies('Q', 2).addCopies('C', 2).addCopies('R', 6).addCopies('W', 1)
      .addCopies('P', 4).addCopies('H', 2).addCopies('K', 2).addCopies('I', 3)
      .addCopies('M', 1).addCopies('T', 4).addCopies('N', 2).addCopies('E', 2)
      .addCopies('G', 4).addCopies('V', 4).addCopies('A', 4).addCopies('D', 2)
      .build();

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
    return String.valueOf(countPossibleRNAStrings(input));
  }

  private static int countPossibleRNAStrings(String protein) {
    // 3 possible stop codons not in protein string
    int numPossible = 3;
    for (int i = 0; i < protein.length(); i++) {
      numPossible = numPossible * aminoAcidPossibilityCounts.count(protein.charAt(i)) % 1_000_000;
    }
    return numPossible;
  }
}
