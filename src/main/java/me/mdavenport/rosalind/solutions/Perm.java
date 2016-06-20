package me.mdavenport.rosalind.solutions;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import me.mdavenport.rosalind.Runner;
import me.mdavenport.rosalind.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.math.IntMath.factorial;

/**
 * Created by matt on 6/14/2016.
 */
public class Perm implements Solution<Integer,List<List<Integer>>> {
  @Override
  public Integer parseInput(List<String> input) {
    Preconditions.checkArgument(input.size() == 1);
    return Integer.valueOf(input.get(0));
  }

  @Override
  public List<List<Integer>> process(Integer input) {
    return permutations(input);
  }

  @Override
  public String formatOutput(List<List<Integer>> output) {
    StringBuilder sb = new StringBuilder();
    sb.append(output.size()).append('\n');
    for (List<Integer> permutation : output) {
      sb.append(Joiner.on(' ').join(permutation)).append('\n');
    }
    return sb.toString();
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new Perm());
  }

  private List<List<Integer>> permutations(int length) {
    if (length < 1) {
      return ImmutableList.of();
    }

    // Steinhaus–Johnson–Trotter
    int numPerms = factorial(length);
    List<List<Integer>> list = new ArrayList<>(numPerms);
    Integer[] directions = new Integer[length];
    Arrays.fill(directions, -1);
    Integer[] permutation = new Integer[length];
    Arrays.setAll(permutation, value -> value + 1);
    list.add(ImmutableList.copyOf(permutation));

    while (true) {
      int largestMobileIndex = -1;
      // Find the index of the largest "mobile" integer.
      // An integer is mobile if it is greater than the integer its direction points to.
      for (int i = 0; i < permutation.length; i++) {
        int neighborIndex = i + directions[i];
        int neighbor = neighborIndex >= 0 && neighborIndex < length ? permutation[neighborIndex] : Integer.MAX_VALUE;
        int current = permutation[i];
        if (neighbor < current && (largestMobileIndex < 0 || current > permutation[largestMobileIndex])) {
          largestMobileIndex = i;
        }
      }

      if (largestMobileIndex < 0) {
        // There are no mobile numbers, so all of the permutations have been enumerated.
        break;
      }

      Integer largestMobile = permutation[largestMobileIndex];

      swap(permutation, largestMobileIndex, largestMobileIndex + directions[largestMobileIndex]);
      swap(directions, largestMobileIndex, largestMobileIndex + directions[largestMobileIndex]);

      for (int i = 0; i < permutation.length; i++) {
        if (permutation[i] > largestMobile) {
          directions[i] *= -1;
        }
      }

      list.add(ImmutableList.copyOf(permutation));
    }

    return list;
  }

  private static <T> void swap(T[] array, int index1, int index2) {
    T temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}
