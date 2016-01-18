import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by matt on 1/3/2016.
 */
public class Parser {
  public static Map<String, String> parseFasta(Iterable<String> input) {
    Map<String, String> annotatedStrings = new LinkedHashMap<>();
    StringBuilder currentString = null;
    String currentName = null;
    for (String line : input) {
      if (line.startsWith(">")) {
        if (currentName != null) {
          annotatedStrings.put(currentName, currentString.toString());
        }
        currentName = line.substring(1);
        currentString = new StringBuilder();
      } else {
        if (currentString == null) {
          throw new IllegalArgumentException("Input is not valid FASTA");
        }
        currentString.append(line);
      }
    }
    if (currentString == null) {
      throw new IllegalArgumentException("Input is not valid FASTA");
    }
    annotatedStrings.put(currentName, currentString.toString());

    return annotatedStrings;
  }
}
