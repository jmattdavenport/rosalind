package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableList;
import me.mdavenport.rosalind.Runner;
import me.mdavenport.rosalind.Solution;

import java.io.IOException;
import java.util.*;

/**
 * Created by matt on 6/20/2016.
 */
public class Tree implements Solution<List<List<Integer>>, Integer> {
  @Override
  public List<List<Integer>> parseInput(List<String> input) {
    List<List<Integer>> parsed = new ArrayList<>();
    // Number of nodes
    parsed.add(ImmutableList.of(Integer.valueOf(input.get(0))));
    // Adjacency list
    for (int i = 1; i < input.size(); i++) {
      Scanner in = new Scanner(input.get(i));
      parsed.add(ImmutableList.of(in.nextInt(), in.nextInt()));
    }

    return parsed;
  }

  @Override
  public Integer process(List<List<Integer>> input) {
    int numNodes = input.get(0).get(0);
    Node[] nodes = new Node[numNodes];
    Set<Node> unvisited = new HashSet<>(numNodes);
    for (int i = 0; i < numNodes; i++) {
      Node node = new Node();
      nodes[i] = node;
      unvisited.add(node);
    }

    for (int i = 1; i < input.size(); i++) {
      List<Integer> nodePair = input.get(i);
      Node.addNeighbors(nodes[nodePair.get(0) - 1], nodes[nodePair.get(1) - 1]);
    }

    int numTrees = 0;

    while (!unvisited.isEmpty()) {
      numTrees++;
      Queue<Node> queue = new ArrayDeque<>();
      queue.add(unvisited.iterator().next());
      while (!queue.isEmpty()) {
        Node current = queue.poll();
        unvisited.remove(current);
        current.getNeighbors().stream().filter(unvisited::contains).forEach(queue::add);
      }
    }

    return numTrees - 1;
  }

  @Override
  public String formatOutput(Integer output) {
    return output.toString();
  }

  private static class Node {
    private Set<Node> neighbors;

    public Node() {
      neighbors = new HashSet<>();
    }

    public Set<Node> getNeighbors() {
      return neighbors;
    }

    public static void addNeighbors(Node a, Node b) {
      a.neighbors.add(b);
      b.neighbors.add(a);
    }
  }

  public static void main(String[] args) throws IOException {
    Runner.run(new Tree());
  }
}
