import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matt on 6/15/2016.
 */
public class PermTest {
  @Test
  public void testLengthZero() throws Exception {
    Perm perm = new Perm();
    List<List<Integer>> result = perm.process(0);
    assertThat(result).hasSize(0);
  }

  @Test
  public void testLengthOne() throws Exception {
    List<List<Integer>> permutations =
        ImmutableList.of(
            ImmutableList.of(1));
    Perm perm = new Perm();
    List<List<Integer>> result = perm.process(1);
    assertThat(result).containsOnlyElementsOf(permutations);
  }

  @Test
  public void testLengthTwo() throws Exception {
    List<List<Integer>> permutations =
        ImmutableList.of(
            ImmutableList.of(1, 2),
            ImmutableList.of(2, 1));
    Perm perm = new Perm();
    List<List<Integer>> result = perm.process(2);
    assertThat(result).containsOnlyElementsOf(permutations);
  }

  @Test
  public void testLengthThree() throws Exception {
    List<List<Integer>> permutations =
        ImmutableList.of(
            ImmutableList.of(1, 2, 3),
            ImmutableList.of(1, 3, 2),
            ImmutableList.of(2, 1, 3),
            ImmutableList.of(2, 3, 1),
            ImmutableList.of(3, 1, 2),
            ImmutableList.of(3, 2, 1));
    Perm perm = new Perm();
    List<List<Integer>> result = perm.process(3);
    assertThat(result).containsOnlyElementsOf(permutations);
  }
}