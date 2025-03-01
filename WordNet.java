import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

/**
 * Created on Fri Feb 28 2025
 * @author Harry Ouyang attests that this code is their original work and was written in compliance with the class Academic Integrity and Collaboration Policy found in the syllabus.
 */

public class WordNet {
   private Digraph graph;
   private HashMap<Integer, String> synsetIdMap = new HashMap<>(); // map of id to synset

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
      // add all ID -> synsets mappings
      In inSyn = new In(synsets); // create input stream with file name
      while (inSyn.hasNextLine()) {
         String[] synset = inSyn.readLine().split(","); // split ID from synset
         synsetIdMap.put(Integer.parseInt(synset[0]), synset[1]);
      }

      // add all hypernyms to graph
      graph = new Digraph(synsetIdMap.size()); // # of vertices should = # of synsets
      In inHyp = new In(hypernyms);
      while (inHyp.hasNextLine()) {
         String[] hypernym = inHyp.readLine().split(",");
         for (int i = 1; i < hypernym.length; i++) { // run through all vertices / hypernyms it points to
            graph.addEdge(Integer.parseInt(hypernym[0]), Integer.parseInt(hypernym[i]));
         }
      }

      // debug
      // System.out.println("Graph:\n" + graph);
      // System.out.println("Map:\n" + synsetIdMap);
   }

   // returns all WordNet nouns
   // public Iterable<String> nouns()

   // is the word a WordNet noun?
   // public boolean isNoun(String word)

   // distance between nounA and nounB (defined below)
   // public int distance(String nounA, String nounB)

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   // public String sap(String nounA, String nounB)

   // do unit testing of this class
   public static void main(String[] args) {
      // file location depends on your file structure
      WordNet wordNet = new WordNet("wordnet\\synsets.txt", "wordnet\\hypernyms.txt");
   }
}