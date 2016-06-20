package me.mdavenport.rosalind.solutions;

import com.google.common.base.Charsets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matt on 11/25/2015.
 */
public class GC {

  public static void main(String[] args) throws IOException {
    String className = new Object() {
    }.getClass().getEnclosingClass().getSimpleName();
    String inputName = "rosalind_" + className.toLowerCase() + ".txt";
    String outputName = "out.txt";
    File outputFile = new File(outputName);
    try (PrintWriter out = new PrintWriter(outputFile)){
      String output = process(Files.readAllLines(Paths.get(inputName), Charsets.UTF_8));
      System.out.println(output);
      out.println(output);
    }
  }

  public static String process(List<String> input) {
    Map<String, String> dnaStrings = new HashMap<>();
    String currentName = null;
    StringBuilder currentString = null;
    for (int i = 0; i < input.size(); i++) {
      String line = input.get(i);
      if (line.startsWith(">")) {
        if (currentName != null) {
          dnaStrings.put(currentName, currentString.toString());
        }
        currentName = line.substring(1);
        currentString = new StringBuilder();
      } else {
        currentString.append(line);
      }
    }
    dnaStrings.put(currentName, currentString.toString());

    GCContent largestGC = largestGC(dnaStrings);
    return String.format("%s\n%.5f", largestGC.name, largestGC.gcContent);
  }

  private static GCContent largestGC(Map<String, String> dnaStrings) {
    double maxGC = 0;
    String maxName = null;

    for (Map.Entry<String, String> entry : dnaStrings.entrySet()) {
      String dnaString = entry.getValue();
      int gcCount = 0;
      for (int i = 0; i < dnaString.length(); i++) {
        char nucleotide = dnaString.charAt(i);
        if (nucleotide == 'C' || nucleotide == 'G') {
          gcCount++;
        }
      }
      double gcRatio = gcCount * 100.0 / dnaString.length();
      if (gcRatio > maxGC) {
        maxGC = gcRatio;
        maxName = entry.getKey();
      }
    }
    return new GCContent(maxName, maxGC);
  }

  private static class GCContent {
    public String name;
    public double gcContent;

    public GCContent(String name, double gcContent) {
      this.name = name;
      this.gcContent = gcContent;
    }
  }
}
