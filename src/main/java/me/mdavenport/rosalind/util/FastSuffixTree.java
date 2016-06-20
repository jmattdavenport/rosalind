package me.mdavenport.rosalind.util;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by matt on 1/3/2016.
 */
public class FastSuffixTree {

  private static final int INFINITY = Integer.MAX_VALUE / 2;

  private final Node root;
  private final Node dummy;

  private final Function<Integer, Integer> wordNumber;

  private String input;

  private List<String> words;
  private Map<String, Integer> wordEndings;

  private int numNodes = 0;

  public FastSuffixTree(List<String> strings) {
    input = Joiner.on("").join(strings);
    words = strings;

    wordEndings = new HashMap<>();
    int lastEnding = 0;
    for (String word : strings) {
      lastEnding += word.length();
      wordEndings.put(word, lastEnding);
    }

    wordNumber = index -> {
      int currentWord = 0;
      Iterator<String> it = strings.iterator();
      while (index > 0 && it.hasNext()) {
        String next = it.next();
        if (index >= next.length()) {
          index -= next.length();
          currentWord++;
        }
      }
      return currentWord;
    };

    dummy = new Node(null) {
      @Override
      public ReferencePair getChild(char a) {
        return new ReferencePair(root, -input.indexOf(a), -input.indexOf(a));
      }
    };

    root = new Node(null);
    root.suffixPath = dummy;

    Node s = root;
    int k = 0;
    for (int i = 0; i < input.length(); i++) {
      ReferencePair ref = canonize(update(new ReferencePair(s, k, i)));
      s = ref.node;
      k = ref.start;
    }
  }

  private ReferencePair update(ReferencePair ref) {
    Node s = ref.node;
    int k = ref.start;
    int i = ref.end;
    Node oldR = root;
    Node r = testAndSplit(new ReferencePair(s, k, i - 1), input.charAt(i));
    while (r != null) {
      System.out.println(toString());
      System.out.println("------------------------------------------------");
      r.addWord(wordNumber.apply(i));
      Node r2 = new Node(r);
      r2.addWord(wordNumber.apply(i));
      r.setChild(input.charAt(i), new ReferencePair(r2, i, INFINITY));
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
      r.addWords(s.getWords());
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

  private class Node {
    private final int num;

    private final Set<Integer> words;

    public Node suffixPath = null;
    public final Node parent;
    private final Map<Character, Node> children = new HashMap<>();
    private final Map<Node, ReferencePair> edges = new HashMap<>();

    public Node(Node parent) {
      this.parent = parent;
      num = numNodes++;
      words = new HashSet<>();
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

    public Set<Integer> getWords() {
      return words;
    }

    public void addWord(int word) {
      words.add(word);
    }

    public void addWords(Iterable<Integer> words) {
      words.forEach(this::addWord);
    }
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    Queue<Node> nodes = new ArrayDeque<>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
      Node node = nodes.poll();
      sb.append(node.num).append(": ");
      sb.append(node.words).append('\n');
      for (Node child : node.children.values()) {
        ReferencePair referencePair = node.edges.get(child);
        int start = referencePair.start;
        String word = words.get(wordNumber.apply(start));
        int end = Math.min(referencePair.end + 1, wordEndings.get(word));
        sb.append('\t').append(input.substring(start, end)).append(": ").append(child.num).append('\n');
        nodes.add(child);
      }
    }
    return sb.toString();
  }
}
