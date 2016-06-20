package me.mdavenport.rosalind.util;

import com.google.common.base.Throwables;

import java.util.*;

/**
 * Created by Matt on 6/11/2016.
 */
public class SuffixTree {
  private final Node root;

  private int numNodes = 0;
  private int numWords;

  public SuffixTree() {
    this.root = new Node();
    numWords = 0;
  }

  public void addWord(String word) {
    Queue<Node> nodes = new ArrayDeque<>();
    nodes.add(root);
    for (int i = 0; i < word.length(); i++) {
      String suffix = word.substring(i, word.length());
      addSuffix(suffix);
    }
    numWords++;
  }

  public String longestCommonSubstring() {
    Queue<NodeAndString> nodes = new ArrayDeque<>();
    nodes.add(new NodeAndString(root, ""));
    NodeAndString last = null;
    while (!nodes.isEmpty()) {
      last = nodes.poll();
      for (Map.Entry<Character, Node> child : last.getNode().children.entrySet()) {
        char c = child.getKey();
        Node node = child.getValue();
        if (node.getWords().size() == numWords) {
          nodes.add(new NodeAndString(node, last.getString() + c));
        }
      }
    }
    return last.getString();
  }

  private void addSuffix(String suffix) {
    Node node = root;
    System.err.println(suffix);
    System.err.println(numNodes);
    System.err.println(toString());
    for (int i = 0; i < suffix.length(); i++) {
      char c = suffix.charAt(i);
      node = node.getOrAddChild(c);
      node.addWord(numWords);
    }
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    Queue<Node> nodes = new ArrayDeque<>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
      Node node = nodes.poll();
      sb.append(node.id).append(": ");
      sb.append(node.words).append('\n');
      for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
        sb.append('\t').append(entry.getKey()).append(": ").append(entry.getValue().id).append('\n');
        nodes.add(entry.getValue());
      }
    }
    return sb.toString();
  }

  private class Node {
    private final int id;
    private final Set<Integer> words;
    private final Map<Character, Node> children;

    private Node() {
      this.id = numNodes++;
      this.words = new HashSet<>();
      this.children = new HashMap<>();
    }

    public Set<Integer> getWords() {
      return words;
    }

    public void addWord(int word) {
      words.add(word);
    }

    public Node getOrAddChild(char c) {
      if (!children.containsKey(c)) {
        children.put(c, new Node());
      } else {
        children.get(c);
      }
      return children.get(c);
    }
  }

  private class NodeAndString {
    private Node node;
    private String string;

    public NodeAndString(Node node, String string) {
      this.node = node;
      this.string = string;
    }

    public Node getNode() {
      return node;
    }

    public String getString() {
      return string;
    }
  }
}
