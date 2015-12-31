import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Matt on 12/4/2015.
 */
public class GRPH {

  private static class DirectedAdjacency {
    public String tail;
    public String head;

    public DirectedAdjacency(String tail, String head) {
      this.tail = tail;
      this.head = head;
    }

    @Override
    public String toString() {
      return tail + " " + head;
    }
  }

public static void main(String[] args) throws IOException {
  String className = new Object(){}.getClass().getEnclosingClass().getSimpleName();
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
    ImmutableMap.Builder<String, String> dnaStrings = ImmutableMap.builder();
    StringBuilder currentString = null;
    String currentName = null;
    for (int i = 0; i < input.size(); i++) {
      String currentLine = input.get(i);
      if (currentLine.startsWith(">")) {
        if (currentName != null) {
          dnaStrings.put(currentName, currentString.toString());
        }
        currentName = currentLine.substring(1);
        currentString = new StringBuilder();
      } else {
        currentString.append(currentLine);
      }
    }
    dnaStrings.put(currentName, currentString.toString());

    Iterable<DirectedAdjacency> adjacencies = overlapGraph(dnaStrings.build());

    return Joiner.on('\n').join(adjacencies);
  }

  private static Iterable<DirectedAdjacency> overlapGraph(Map<String, String> dnaStrings) {
    Multimap<String, String> overlaps = HashMultimap.create();
    // Populate map with overlaps
    dnaStrings.entrySet().forEach(entry -> overlaps.put(entry.getValue().substring(0, 3), entry.getKey()));

    // Read and return overlaps
    ArrayDeque<DirectedAdjacency> adjacencies = new ArrayDeque<>();
    for (Map.Entry<String, String> dnaString : dnaStrings.entrySet()) {
      String name = dnaString.getKey();
      String dna = dnaString.getValue();
      int length = dna.length();
      adjacencies.addAll(overlaps.get(dna.substring(length - 3, length))
          .stream().filter(next -> !name.equals(next))
          .map(next -> new DirectedAdjacency(name, next)).collect(Collectors.toList()));
    }
    return adjacencies;
  }
}
