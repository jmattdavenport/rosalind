package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 12/4/2015.
 */
public class PRTM {

  private static final ImmutableMap<Character, Double> AMINO_ACID_MASS = ImmutableMap.<Character, Double>builder()
      .put('A', 71.03711)
      .put('C', 103.00919)
      .put('D', 115.02694)
      .put('E', 129.04259)
      .put('F', 147.06841)
      .put('G', 57.02146)
      .put('H', 137.05891)
      .put('I', 113.08406)
      .put('K', 128.09496)
      .put('L', 113.08406)
      .put('M', 131.04049)
      .put('N', 114.04293)
      .put('P', 97.05276)
      .put('Q', 128.05858)
      .put('R', 156.10111)
      .put('S', 87.03203 )
      .put('T', 101.04768)
      .put('V', 99.06841)
      .put('W', 186.07931)
      .put('Y', 163.06333)
      .build();

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
    return String.format("%.5f", stringWeight(input));
  }

  private static double stringWeight(String proteinString) {
    double weight = 0;
    for (int i = 0; i < proteinString.length(); i++) {
      weight += AMINO_ACID_MASS.get(proteinString.charAt(i));
    }
    return weight;
  }
}
