import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matt on 6/19/2016.
 */
public class RevpTest {

  @Test
  public void testReversePalindrome() throws Exception {
    Revp revp = new Revp();
    assertThat(revp.process("caag")).isEmpty();
    assertThat(revp.process("catg")).containsOnly(ImmutableList.of(1, 4));
    assertThat(revp.process("tgcatgcc")).containsOnly(ImmutableList.of(1, 4), ImmutableList.of(2, 6),
        ImmutableList.of(3, 4));
    assertThat(revp.process("TCAATGCATGCGGGTCTATATGCAT")).containsOnly(
        ImmutableList.of(4, 6),
        ImmutableList.of(5, 4),
        ImmutableList.of(6, 6),
        ImmutableList.of(7, 4),
        ImmutableList.of(17, 4),
        ImmutableList.of(18, 4),
        ImmutableList.of(20, 6),
        ImmutableList.of(21, 4));
  }
}