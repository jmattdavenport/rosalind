import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by matt on 1/3/2016.
 */
public class SuffixTree {

  private static final int INFINITY = Integer.MAX_VALUE / 2;

  private final String input;
  private final Node root;
  private final Node dummy;
  private final Set<Node> leaves = new HashSet<>();
  private final Set<Node> currentWordLeaves = new HashSet<>();
  private final Multimap<Node, Integer> relatedInputs = HashMultimap.create();
  private final List<Character> separators;

  public SuffixTree(Collection<String> strings) {
    SeparatorSelector separatorSelector = new SeparatorSelector(strings);
    separators = separatorSelector.getSeparators();
    StringBuilder combinedString = new StringBuilder();
    int separatorNum = 0;
    for (String s : strings) {
      combinedString.append(s);
      combinedString.append(separators.get(separatorNum++));
    }

    input = combinedString.toString();

    dummy = new Node(null) {
      @Override
      public ReferencePair getChild(char a) {
        return new ReferencePair(root, -input.indexOf(a), -input.indexOf(a));
      }
    };

    root = new Node(null);
    root.suffixPath = dummy;
    for (int i = 0; i < strings.size(); i++) {
      relatedInputs.put(root, i);
    }

    Node s = root;
    int k = 0;
    for (int i = 0; i < input.length(); i++) {
      System.out.println(input.charAt(i));
      ReferencePair ref = canonize(update(new ReferencePair(s, k, i)));
      s = ref.node;
      k = ref.start;
    }
  }

//  public SuffixTree(String input) {
//    this.input = input;
//
//    dummy = new Node(null) {
//      @Override
//      public ReferencePair getChild(char a) {
//        return new ReferencePair(root, -input.indexOf(a), -input.indexOf(a));
//      }
//    };
//
//    root = new Node(null);
//    root.suffixPath = dummy;
//
//    Node s = root;
//    int k = 0;
//    for (int i = 0; i < input.length(); i++) {
//      System.out.println(input.charAt(i));
//      ReferencePair ref = canonize(update(new ReferencePair(s, k, i)));
//      s = ref.node;
//      k = ref.start;
//    }
//  }

  private ReferencePair update(ReferencePair ref) {
    Node s = ref.node;
    int k = ref.start;
    int i = ref.end;
    Node oldR = root;
    Node r = testAndSplit(new ReferencePair(s, k, i - 1), input.charAt(i));
    while (r != null) {
      Node r2 = new Node(r);
      r.setChild(input.charAt(i), new ReferencePair(r2, i, INFINITY));
      currentWordLeaves.remove(r);
      currentWordLeaves.add(r2);
      if (oldR != root) {
        oldR.suffixPath = r;
      }
      oldR = r;
      ref = canonize(new ReferencePair(s.suffixPath, k, i - 1));
      s = ref.node;
      k = ref.start;
      r = testAndSplit(new ReferencePair(s, k, i - 1), input.charAt(i));
    }
    if (!oldR.equals(root)) {
      oldR.suffixPath = s;
    }
    return new ReferencePair(s, k, i);
  }

  private Node testAndSplit(ReferencePair ref, char t) {
    Node s = ref.node;
    int k = ref.start;
    int p = ref.end;

    if (k <= p) {
      char tk = input.charAt(k);
      ref = s.getChild(tk);
      if (ref == null) {
        return s;
      }
      Node s2 = ref.node;
      int k2 = ref.start;
      int p2 = ref.end;
      char next = input.charAt(k2 + p - k + 1);
      if (t == next) {
        return null;
      }
      Node r = new Node(s);
      s.setChild(tk, new ReferencePair(r, k2, k2 + p - k));
      r.setChild(next, new ReferencePair(s2, k2 + p - k + 1, p2));
      return r;
    } else {
      ref = s.getChild(t);
      if (ref == null) {
        return s;
      } else {
        return null;
      }
    }
  }

  private ReferencePair canonize(ReferencePair ref) {
    Node s = ref.node;
    int k = ref.start;
    int p = ref.end;

    if (p < k) {
      return ref;
    }
    char tk = input.charAt(k);

    ref = s.getChild(tk);
    if (ref == null) {
      return new ReferencePair(s, k, p);
    }
    Node s2 = ref.node;
    int k2 = ref.start;
    int p2 = ref.end;

    while (p2 - k2 <= p - k) {
      k = k + p2 - k2 + 1;
      s = s2;
      if (k <= p) {
        ref = s.getChild(tk);
        if (ref == null) {
          return new ReferencePair(s, k, p);
        }
        s2 = ref.node;
        k2 = ref.start;
        p2 = ref.end;
      }
    }
    return new ReferencePair(s, k, p);
  }

  private static class ReferencePair {
    public final Node node;
    public final int start;
    public final int end;

    public ReferencePair(Node node, int start, int end) {
      this.node = node;
      this.start = start;
      this.end = end;
    }
  }

  private static class Node {
    private static int count = 0;
    private final int num;

    public Node suffixPath = null;
    public final Node parent;
    private final Map<Character, Node> children = new HashMap<>();
    private final Map<Node, ReferencePair> edges = new HashMap<>();

    public Node(Node parent) {
      this.parent = parent;
      num = count++;
    }

    public ReferencePair getChild(char a) {
      Node child = children.get(a);
      return edges.get(child);
    }

    public void setChild(Character a, ReferencePair ref) {
      if (children.containsKey(a)) {
        Node old = children.remove(a);
        edges.remove(old);
      }
      children.put(a, ref.node);
      edges.put(ref.node, ref);
    }

    @Override
    public String toString() {
      return num + ": root" + '\n' + toString("  ");
    }

    private String toString(String prefix) {
      StringBuilder builder = new StringBuilder();
      for (ReferencePair edge : edges.values()) {
        builder.append(prefix).append("[").append(edge.start).append(", ");
        if (edge.end == INFINITY) {
          builder.append('\u221E');
        } else {
          builder.append(edge.end);
        }
        builder.append("]").append('\n');
        builder.append(edge.node.toString(prefix + "  "));
      }
      return builder.toString();
    }
  }

  private static class SeparatorSelector {
    private final Set<Character> usedCharacters = new HashSet<>();
    private final int numWords;

    public SeparatorSelector(Collection<String> words) {
      numWords = words.size();
      for (String word : words) {
        for (int i = 0; i < word.length(); i++) {
          usedCharacters.add(word.charAt(i));
        }
      }
    }

    public List<Character> getSeparators() {
      return IntStream.iterate(0, x -> x + 1)
          .mapToObj(x -> (char) x)
          .filter(x -> !usedCharacters.contains(x))
          .limit(numWords)
          .collect(Collectors.toList());
    }
  }

  @Override
  public String toString() {
    return "leaves: " + leaves + '\n' + root.toString();
  }

  public static void main(String[] args) {
    SuffixTree tree = new SuffixTree(ImmutableList.of("cacao", "cat"));
    System.out.println(tree.toString());
  }
}
