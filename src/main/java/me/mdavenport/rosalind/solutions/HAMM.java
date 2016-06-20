package me.mdavenport.rosalind.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/28/2015.
 */
public class HAMM {

public static void main(String[] args) throws FileNotFoundException {
  String className = new Object(){}.getClass().getEnclosingClass().getSimpleName();
  String inputName = "rosalind_" + className.toLowerCase() + ".txt";
  String outputName = "out.txt";

  File inputFile = new File(inputName);
  File outputFile = new File(outputName);
  try (Scanner in = new Scanner(inputFile); PrintWriter out = new PrintWriter(outputFile)) {
    while (in.hasNextLine()) {
      String output = process(in.nextLine(), in.nextLine());
      System.out.println(output);
      out.println(output);
    }
  }
}
  
  private static String process(String line1, String line2) {
    return String.valueOf(hammingDistance(line1, line2));
  }

  private static int hammingDistance(String line1, String line2) {
    int distance = 0;
    for (int i = 0; i < line1.length(); i++) {
      if (line1.charAt(i) != line2.charAt(i)) {
        distance++;
      }
    }
    return distance;
  }
}
