package me.mdavenport.rosalind.solutions;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.mdavenport.rosalind.Runner;
import me.mdavenport.rosalind.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by matt on 1/2/2016.
 */
public class SplcNew implements Solution<Multimap<String, String>, String> {
  @Override
  public Multimap<String, String> parseInput(List<String> input) {
    List<FastaString> fastaStrings = new ArrayList<>();
    String name = null;
    StringBuilder data = null;
    for (String line : input) {
      if (line.startsWith(">")) {
        if (name != null) {
          fastaStrings.add(new FastaString(name, data.toString()));
        }
        name = line.substring(1);
        data = new StringBuilder();
      } else {
        data.append(line);
      }
    }
    fastaStrings.add(new FastaString(name, data.toString()));
    String dnaString = fastaStrings.get(0).data;
    List<String> introns =
        fastaStrings.subList(1, fastaStrings.size()).stream().map(x -> x.data).collect(Collectors.toList());
    Multimap<String, String> map = HashMultimap.create();
    map.putAll(dnaString, introns);
    return map;
  }

  @Override
  public String process(Multimap<String, String> input) {
    String dnaString = input.keySet().iterator().next();
    Collection<String> introns = input.get(dnaString);
    for (String intron : introns) {
      dnaString = dnaString.replaceAll(intron, "");
    }
    String rnaString = RNA.transcribeRNA(dnaString);
    return PROT.translateToProtein(rnaString);
  }

  @Override
  public String formatOutput(String output) {
    return output;
  }

  public static class FastaString {
    String name;
    String data;

    public FastaString(String name, String data) {
      this.name = name;
      this.data = data;
    }
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new SplcNew());
  }
}
