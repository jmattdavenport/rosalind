package me.mdavenport.rosalind.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/25/2015.
 */
public class FIB {

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
    Scanner scanner = new Scanner(input);
    int n = scanner.nextInt();
    int k = scanner.nextInt();
    long numRabbits = calculateRabbits(n, k);
    return String.valueOf(numRabbits);
  }

  private static long calculateRabbits(int n, int k) {
    long last = 0;
    long current = 1;
    for (int i = 0; i < n - 1; i++) {
      long next = last * k + current;
      last = current;
      current = next;
    }
    return current;
  }
}
