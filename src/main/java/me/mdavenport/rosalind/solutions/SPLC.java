package me.mdavenport.rosalind.solutions;

import me.mdavenport.rosalind.solutions.PROT;
import me.mdavenport.rosalind.solutions.RNA;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Matt on 12/6/2015.
 */
public class SPLC {

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
    List<FastaString> fastaStrings = parseFasta(input);
    String dnaString = fastaStrings.get(0).data;
    List<String> introns =
        fastaStrings.subList(1, fastaStrings.size()).stream().map(x -> x.data).collect(Collectors.toList());
    return transcribeAndTranslate(dnaString, introns);
  }

  private static String transcribeAndTranslate(String dnaString, List<String> introns) {
    for (String intron : introns) {
      dnaString = dnaString.replaceAll(intron, "");
    }
    String rnaString = RNA.transcribeRNA(dnaString);
    return PROT.translateToProtein(rnaString);
  }

  private static List<FastaString> parseFasta(List<String> lines) {
    List<FastaString> fastaStrings = new ArrayList<>();
    String name = null;
    StringBuilder data = null;
    for (String line : lines) {
      if (line.startsWith(">")) {
        if (name != null) {
          fastaStrings.add(new FastaString(name, data.toString()));
        }
        name = line.substring(1);
        data = new StringBuilder();
      } else {
        data.append(line);
      }
    }
    fastaStrings.add(new FastaString(name, data.toString()));
    return fastaStrings;
  }

  private static class FastaString {
    String name;
    String data;

    public FastaString(String name, String data) {
      this.name = name;
      this.data = data;
    }
  }
}
