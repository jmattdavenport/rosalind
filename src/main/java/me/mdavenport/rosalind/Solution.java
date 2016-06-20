package me.mdavenport.rosalind;

import java.io.IOException;
import java.util.List;

/**
 * Created by Matt on 11/28/2015.
 */
public interface Solution<S, T> {

  S parseInput(List<String> input);
  
  T process(S input);

  String formatOutput(T output);
}
