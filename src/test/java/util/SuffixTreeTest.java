package util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Matt on 6/12/2016.
 */
public class SuffixTreeTest {
  private SuffixTree tree;

  @Before
  public void setUp() throws Exception {
    tree = new SuffixTree();
  }

  @Test
  public void testOneWord() {
    String expected = "0: []\n" +
        "\ta: 1\n" +
        "\tb: 5\n" +
        "1: [0]\n" +
        "\tb: 2\n" +
        "5: [0]\n" +
        "\ta: 6\n" +
        "2: [0]\n" +
        "\ta: 3\n" +
        "6: [0]\n" +
        "\tb: 7\n" +
        "3: [0]\n" +
        "\tb: 4\n" +
        "7: [0]\n" +
        "4: [0]\n";
    tree.addWord("abab");
    assertEquals(expected, tree.toString());
  }

  @Test
  public void testTwoWords() throws Exception {
    String expected = "0: []\n" +
        "\ta: 1\n" +
        "\tb: 5\n" +
        "1: [0, 1]\n" +
        "\tb: 2\n" +
        "5: [0, 1]\n" +
        "\ta: 6\n" +
        "2: [0, 1]\n" +
        "\ta: 3\n" +
        "6: [0, 1]\n" +
        "\tb: 7\n" +
        "3: [0, 1]\n" +
        "\tb: 4\n" +
        "7: [0, 1]\n" +
        "\ta: 8\n" +
        "4: [0]\n" +
        "8: [1]\n";
    tree.addWord("abab");
    tree.addWord("baba");
    assertEquals(expected, tree.toString());
  }

  @Test
  public void testLongestCommonSubstring() throws Exception {
    tree.addWord("abab");
    tree.addWord("babc");
    assertEquals("bab", tree.longestCommonSubstring());
  }
}