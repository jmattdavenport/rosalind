import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Matt on 11/28/2015.
 */
public interface Solution<S, T> {

  S readInput()
  
  T process(S input);
}
