import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Matt on 12/6/2015.
 */
public class CONS {

  private static final char[] nucleotides = new char[]{'A', 'C', 'G', 'T'};

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
    Map<String, String> dnaStrings = new HashMap<>();
    String currentName = null;
    StringBuilder currentString = null;
    for (String line : input) {
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

    Result result = concencusString(dnaStrings.values());
    StringBuilder output = new StringBuilder(result.concencusString);
    output.append('\n');
    for (int i = 0; i < result.profileMatrix.length; i++) {
      output.append(nucleotides[i]);
      output.append(": ");
      output.append(Arrays.stream(result.profileMatrix[i]).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
      output.append('\n');
    }
    return output.toString();
  }

  private static Result concencusString(Collection<String> dnaStrings) {
    int length = dnaStrings.iterator().next().length();
    int[][] profileMatrix = new int[nucleotides.length][length];
    StringBuilder concencusString = new StringBuilder();
    for (int i = 0; i < length; i++) {
      for (String dnaString : dnaStrings) {
        int index = Arrays.binarySearch(nucleotides, dnaString.charAt(i));
        profileMatrix[index][i]++;
      }
      int maxIndex = 0;
      for (int j = 1; j < profileMatrix.length; j++) {
        if (profileMatrix[j][i] > profileMatrix[maxIndex][i]) {
          maxIndex = j;
        }
      }
      concencusString.append(nucleotides[maxIndex]);
    }
    return new Result(concencusString.toString(), profileMatrix);
  }

  private static class Result {
    public final String concencusString;
    public final int[][] profileMatrix;

    public Result(String concencusString, int[][] profileMatrix) {
      this.concencusString = concencusString;
      this.profileMatrix = profileMatrix;
    }
  }
}
