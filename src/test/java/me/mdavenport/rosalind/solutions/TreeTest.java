package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matt on 6/20/2016.
 */
public class TreeTest {

  @Test
  public void testSample() throws Exception {
    List<List<Integer>> input = ImmutableList.of(
        ImmutableList.of(10),
        ImmutableList.of(1, 2),
        ImmutableList.of(2, 8),
        ImmutableList.of(4, 10),
        ImmutableList.of(5, 9),
        ImmutableList.of(6, 10),
        ImmutableList.of(7, 9)
    );
    Tree tree = new Tree();
    assertThat(tree.process(input)).isEqualTo(3);
  }
}