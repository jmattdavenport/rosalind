import util.SuffixTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by matt on 1/3/2016.
 */
public class Lcsm implements Solution<Iterable<String>, String> {

  @Override
  public Iterable<String> parseInput(List<String> input) {
    Map<String, String> dnaStrings = Parser.parseFasta(input);
    return dnaStrings.values();
  }

  @Override
  public String process(Iterable<String> input) {
    SuffixTree tree = new SuffixTree();
    for (String dnaString : input) {
      tree.addWord(dnaString);
    }
    return tree.longestCommonSubstring();
  }

  @Override
  public String formatOutput(String output) {
    return output;
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new Lcsm());
  }
}
