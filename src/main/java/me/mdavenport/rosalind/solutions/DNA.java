package me.mdavenport.rosalind.solutions;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/25/2015.
 */
public class DNA {
  public static Multiset<Character> countNucleotides(String dnaString) {
    Multiset<Character> nucleotideCounts = HashMultiset.create();
    dnaString.chars().forEach(nucleotide -> nucleotideCounts.add((char) nucleotide));
    System.out.println(nucleotideCounts);
    return nucleotideCounts;
  }

  public static String process(String line) {
    Multiset<Character> counts = countNucleotides(line);
    return String.format("%d %d %d %d", counts.count('A'), counts.count('C'), counts.count('G'), counts.count('T'));
  }

  public static void main(String[] args) throws FileNotFoundException {
    String className = new Object(){}.getClass().getEnclosingClass().getSimpleName();
    String inputName = "rosalind_" + className.toLowerCase() + ".txt";
    String outputName = "out.txt";
    File inputFile = new File(inputName);
    File outputFile = new File(outputName);
    Scanner in = new Scanner(inputFile);
    PrintWriter out = new PrintWriter(outputFile);
    while (in.hasNextLine()) {
      out.println(process(in.nextLine()));
    }
    in.close();
    out.close();
  }
}
