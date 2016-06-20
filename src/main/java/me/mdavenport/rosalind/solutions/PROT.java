package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Matt on 11/26/2015.
 */
public class PROT {
  // UAA, UAG, and UGA are stop codons, so they aren't in the table
  private static Map<String, Character> codonTable = ImmutableMap.<String, Character>builder()
      .put("UUU", 'F').put("CUU", 'L').put("AUU", 'I').put("GUU", 'V')
      .put("UUC", 'F').put("CUC", 'L').put("AUC", 'I').put("GUC", 'V')
      .put("UUA", 'L').put("CUA", 'L').put("AUA", 'I').put("GUA", 'V')
      .put("UUG", 'L').put("CUG", 'L').put("AUG", 'M').put("GUG", 'V')
      .put("UCU", 'S').put("CCU", 'P').put("ACU", 'T').put("GCU", 'A')
      .put("UCC", 'S').put("CCC", 'P').put("ACC", 'T').put("GCC", 'A')
      .put("UCA", 'S').put("CCA", 'P').put("ACA", 'T').put("GCA", 'A')
      .put("UCG", 'S').put("CCG", 'P').put("ACG", 'T').put("GCG", 'A')
      .put("UAU", 'Y').put("CAU", 'H').put("AAU", 'N').put("GAU", 'D')
      .put("UAC", 'Y').put("CAC", 'H').put("AAC", 'N').put("GAC", 'D')
      .put("CAA", 'Q').put("AAA", 'K').put("GAA", 'E')
      .put("CAG", 'Q').put("AAG", 'K').put("GAG", 'E')
      .put("UGU", 'C').put("CGU", 'R').put("AGU", 'S').put("GGU", 'G')
      .put("UGC", 'C').put("CGC", 'R').put("AGC", 'S').put("GGC", 'G')
      .put("CGA", 'R').put("AGA", 'R').put("GGA", 'G')
      .put("UGG", 'W').put("CGG", 'R').put("AGG", 'R').put("GGG", 'G')
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
    return translateToProtein(input);
  }

  public static String translateToProtein(String mRNA) {
    StringBuilder protein = new StringBuilder(mRNA.length() / 3);
    for (int i = 0; i < mRNA.length() - 2; i += 3) {
      String codon = mRNA.substring(i, i + 3);
      if (codonTable.containsKey(codon)) {
        protein.append(codonTable.get(codon));
      }
    }
    return protein.toString();
  }
}
