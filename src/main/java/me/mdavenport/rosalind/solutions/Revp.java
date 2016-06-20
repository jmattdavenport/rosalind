package me.mdavenport.rosalind.solutions;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import me.mdavenport.rosalind.Runner;
import me.mdavenport.rosalind.Solution;
import me.mdavenport.rosalind.util.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 6/19/2016.
 */
public class Revp implements Solution<String, List<List<Integer>>> {
  private static final int MAX_PALINDROME_LENGTH = 12;

  private boolean[][] palindromes;

  @Override
  public String parseInput(List<String> input) {
    return Parser.parseFasta(input).values().iterator().next();
  }

  @Override
  public List<List<Integer>> process(String input) {
    return reversePalindromes(input);
  }

  @Override
  public String formatOutput(List<List<Integer>> output) {
    StringBuilder sb = new StringBuilder();
    for (List<Integer> palindrome : output) {
      sb.append(Joiner.on(' ').join(palindrome)).append('\n');
    }
    return sb.toString();
  }

  private List<List<Integer>> reversePalindromes(String input) {
    List<List<Integer>> output = new ArrayList<>();

    palindromes = new boolean[input.length() + 1][input.length()];
    for (int i = 0; i < input.length() - 1; i++) {
      palindromes[2][i] = complements(input.charAt(i), input.charAt(i + 1));
    }

    // Reverse palindromes can only be even length. The middle nucleobase can't be its own complement for odd strings.
    for (int length = 4; length <= MAX_PALINDROME_LENGTH; length += 2) {
      for (int start = 0; start < input.length() - length + 1; start++) {
        if (palindromes[length - 2][start + 1] && complements(input.charAt(start), input.charAt(start + length - 1))) {
          palindromes[length][start] = true;
          output.add(ImmutableList.of(start + 1, length));
        }
      }
    }

    return output;
  }

  private boolean complements(char a, char b) {
    a = Character.toUpperCase(a);
    b = Character.toUpperCase(b);
    switch (a) {
      case 'A':
        return b == 'T';
      case 'C':
        return b == 'G';
      case 'G':
        return b == 'C';
      case 'T':
        return b == 'A';
      default:
        return false;
    }
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new Revp());
  }
}
