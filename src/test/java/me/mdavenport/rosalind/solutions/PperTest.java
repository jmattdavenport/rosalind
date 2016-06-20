package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matt on 6/20/2016.
 */
public class PperTest {

  @Test
  public void testSample() throws Exception {
    Pper pper = new Pper();
    assertThat(pper.process(ImmutableList.of(21, 7))).isEqualTo(51200);
  }
}