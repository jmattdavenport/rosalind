package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Matt on 12/6/2015.
 */
public class ORF {

  private static Pattern startCodon = Pattern.compile("ATG");
  private static ImmutableMap<String, Character> dnaCodonTable = ImmutableMap.<String, Character>builder()
      .put("TTT", 'F').put("CTT", 'L').put("ATT", 'I').put("GTT", 'V')
      .put("TTC", 'F').put("CTC", 'L').put("ATC", 'I').put("GTC", 'V')
      .put("TTA", 'L').put("CTA", 'L').put("ATA", 'I').put("GTA", 'V')
      .put("TTG", 'L').put("CTG", 'L').put("ATG", 'M').put("GTG", 'V')
      .put("TCT", 'S').put("CCT", 'P').put("ACT", 'T').put("GCT", 'A')
      .put("TCC", 'S').put("CCC", 'P').put("ACC", 'T').put("GCC", 'A')
      .put("TCA", 'S').put("CCA", 'P').put("ACA", 'T').put("GCA", 'A')
      .put("TCG", 'S').put("CCG", 'P').put("ACG", 'T').put("GCG", 'A')
      .put("TAT", 'Y').put("CAT", 'H').put("AAT", 'N').put("GAT", 'D')
      .put("TAC", 'Y').put("CAC", 'H').put("AAC", 'N').put("GAC", 'D')
      .put("CAA", 'Q').put("AAA", 'K').put("GAA", 'E')
      .put("CAG", 'Q').put("AAG", 'K').put("GAG", 'E')
      .put("TGT", 'C').put("CGT", 'R').put("AGT", 'S').put("GGT", 'G')
      .put("TGC", 'C').put("CGC", 'R').put("AGC", 'S').put("GGC", 'G')
      .put("CGA", 'R').put("AGA", 'R').put("GGA", 'G')
      .put("TGG", 'W').put("CGG", 'R').put("AGG", 'R').put("GGG", 'G')
      .build();

  public static void main(String[] args) throws IOException {
    String className = new Object() {
    }.getClass().getEnclosingClass().getSimpleName();
    String inputName = "rosalind_" + className.toLowerCase() + ".txt";
    String outputName = "out.txt";
    File outputFile = new File(outputName);
    try (PrintWriter out = new PrintWriter(outputFile)) {
      String output = process(Files.readAllLines(Paths.get(inputName)));
      System.out.println(output);
      out.println(output);
    }
  }

  private static String process(List<String> input) {
    StringBuilder dnaString = new StringBuilder();
    for (int i = 1; i < input.size(); i++) {
      dnaString.append(input.get(i));
    }
    Set<String> proteins = dnaProteins(dnaString.toString());
    return proteins.stream().collect(Collectors.joining("\n"));
  }

  private static Set<String> dnaProteins(String dnaString) {
    Set<String> proteins = findProteins(dnaString);
    proteins.addAll(findProteins(REVC.reverseComplement(dnaString)));
    return proteins;
  }

  private static Set<String> findProteins(String dnaString) {
    Set<String> proteins = new HashSet<>();
    Matcher matcher = startCodon.matcher(dnaString);
    while (matcher.find()) {
      int pos = matcher.start();
      StringBuilder protein = new StringBuilder();
      while (pos + 3 <= dnaString.length()) {
        String codon = dnaString.substring(pos, pos + 3);
        if (dnaCodonTable.containsKey(codon)) {
          protein.append(dnaCodonTable.get(dnaString.substring(pos, pos + 3)));
        } else {
          proteins.add(protein.toString());
          break;
        }
        pos += 3;
      }
    }
    return proteins;
  }
}
