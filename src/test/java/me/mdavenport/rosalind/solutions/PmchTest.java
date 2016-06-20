package me.mdavenport.rosalind.solutions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matt on 6/20/2016.
 */
public class PmchTest {
  @Test
  public void testSample() throws Exception {
    Pmch pmch = new Pmch();
    assertThat(pmch.process("AGCUAGUCAU")).isEqualTo(12);
  }
}