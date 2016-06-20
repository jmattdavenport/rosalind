package me.mdavenport.rosalind.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Scanner;
import java.util.stream.LongStream;

/**
 * Created by Matt on 11/25/2015.
 */
public class FIBD {

  public static void main(String[] args) throws FileNotFoundException {
    String className = new Object() {}.getClass().getEnclosingClass().getSimpleName();
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
    int m = scanner.nextInt();
    long numRabbits = calculateRabbits(n, m);
    return String.valueOf(numRabbits);
  }

  private static long calculateRabbits(int n, int m) {
    ArrayDeque<Long> rabbits = new ArrayDeque<>(Collections.nCopies(m, 0L));
    rabbits.pollFirst();
    rabbits.addLast(1L);
    for (int i = 0; i < n - 1; i++) {
      long next = rabbits.stream().mapToLong(x -> x).sum();
      next -= rabbits.getLast();
      rabbits.pollFirst();
      rabbits.addLast(next);
    }
    return rabbits.stream().mapToLong(x -> x).sum();
  }
}
