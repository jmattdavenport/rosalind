package me.mdavenport.rosalind.solutions;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import me.mdavenport.rosalind.Runner;
import me.mdavenport.rosalind.Solution;
import me.mdavenport.rosalind.util.Parser;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static com.google.common.math.LongMath.factorial;

/**
 * Created by matt on 6/19/2016.
 */
public class Pmch implements Solution<String, BigInteger> {
  @Override
  public String parseInput(List<String> input) {
    return Parser.parseFasta(input).values().iterator().next();
  }

  @Override
  public BigInteger process(String input) {
    Multiset<Character> bases = HashMultiset.create(4);
    for (int i = 0; i < input.length(); i++) {
      bases.add(Character.toUpperCase(input.charAt(i)));
    }
    if (bases.count('A') != bases.count('U')) {
      return BigInteger.ZERO;
    }
    if (bases.count('C') != bases.count('G')) {
      return BigInteger.ZERO;
    }

    return BigInteger.valueOf(factorial(bases.count('A'))).multiply(BigInteger.valueOf(factorial(bases.count('C'))));
  }

  @Override
  public String formatOutput(BigInteger output) {
    return output.toString();
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new Pmch());
  }
}
