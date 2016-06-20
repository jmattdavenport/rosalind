package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableList;
import me.mdavenport.rosalind.Runner;
import me.mdavenport.rosalind.Solution;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by matt on 6/20/2016.
 */
public class Pper implements Solution<List<Integer>, Integer> {
  private static final int MOD = 1_000_000;

  @Override
  public List<Integer> parseInput(List<String> input) {
    Scanner in = new Scanner(input.get(0));
    return ImmutableList.of(in.nextInt(), in.nextInt());
  }

  @Override
  public Integer process(List<Integer> input) {
    int n = input.get(0);
    int k = input.get(1);
    int output = 1;
    for (int i = n; i > n - k; i--) {
      output *= i;
      output %= MOD;
    }

    return output;
  }

  @Override
  public String formatOutput(Integer output) {
    return output.toString();
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new Pper());
  }
}
