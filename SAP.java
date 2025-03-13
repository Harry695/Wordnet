import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created on Fri Feb 28 2025
 *
 * @author Harry Ouyang attests that this code is their original work and was written in compliance
 *     with the class Academic Integrity and Collaboration Policy found in the syllabus.
 */
public class SAP {
  private Digraph graph;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    if (G == null) {
      throw new IllegalArgumentException();
    }

    graph = new Digraph(G);
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(graph, v);
    BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(graph, w);

    int minLen = Integer.MAX_VALUE;

    // search through all vertices, find point that is connected to both v and w with smallest
    // distance
    for (int i = 0; i < graph.V(); i++) {
      if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
        minLen = Math.min(vBFS.distTo(i) + wBFS.distTo(i), minLen);
      }
    }

    if (minLen != Integer.MAX_VALUE) {
      return minLen;
    }
    return -1;
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(graph, v);
    BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(graph, w);

    int minLen = Integer.MAX_VALUE;
    int ancestor = -1;

    // search through all vertices, find point that is connected to both v and w with smallest
    // distance
    for (int i = 0; i < graph.V(); i++) {
      if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
        // System.out.println("true for " + i);

        int currentDist = vBFS.distTo(i) + wBFS.distTo(i);
        if (currentDist < minLen) {
          minLen = currentDist;
          ancestor = i;
        }
      }
    }

    if (minLen != Integer.MAX_VALUE) {
      return ancestor;
    }
    return -1;
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such
  // path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null || !v.iterator().hasNext() || !w.iterator().hasNext()) {
      throw new IllegalArgumentException();
    }
    if (!validList(v) || !validList(w)) {
      throw new IllegalArgumentException();
    }

    BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(graph, v);
    BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(graph, w);

    int minLen = Integer.MAX_VALUE;

    // search through all vertices, find point that is connected to both v and w with smallest
    // distance
    for (int i = 0; i < graph.V(); i++) {
      if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
        minLen = Math.min(vBFS.distTo(i) + wBFS.distTo(i), minLen);
      }
    }

    if (minLen != Integer.MAX_VALUE) {
      return minLen;
    }
    return -1;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null || !v.iterator().hasNext() || !w.iterator().hasNext()) {
      throw new IllegalArgumentException();
    }
    if (!validList(v) || !validList(w)) {
      throw new IllegalArgumentException();
    }

    BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(graph, v);
    BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(graph, w);

    int minLen = Integer.MAX_VALUE;
    int ancestor = -1;

    // search through all vertices, find point that is connected to both v and w with smallest
    // distance
    for (int i = 0; i < graph.V(); i++) {
      if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
        // System.out.println("true for " + i);

        int currentDist = vBFS.distTo(i) + wBFS.distTo(i);
        if (currentDist < minLen) {
          minLen = currentDist;
          ancestor = i;
        }
      }
    }

    if (minLen != Integer.MAX_VALUE) {
      return ancestor;
    }
    return -1;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In("wordnet\\digraph2.txt");
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }

  private boolean validList(Iterable<Integer> list) {
    for (Integer i : list) {
      if (i == null || !isInRange(i)) {
        return false;
      }
    }
    return true;
  }

  private boolean isInRange(int i) {
    return i >= 0 && i < graph.V();
  }
 }