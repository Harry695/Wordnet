import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on Fri Feb 28 2025
 *
 * @author Harry Ouyang attests that this code is their original work and was written in compliance
 *     with the class Academic Integrity and Collaboration Policy found in the syllabus.
 */
public class WordNet {
  private Digraph graph;
  private List<String> synsetList = new ArrayList<>();
  private Map<String, Bag<Integer>> nounToIDs = new HashMap<>();

  // Why arraylist?
  // can access via index (ID) in O(1)
  // can construct in O(n)
  // can search in O(log n) with binary search since input is sorted

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms) {
    // add all synsets to arraylist
    In inSyn = new In(synsets); // create input stream with file name
    while (inSyn.hasNextLine()) {
      String[] synsetLine = inSyn.readLine().split(",");
      int currentLineID = Integer.parseInt(synsetLine[0]);
      String[] currentSynset = synsetLine[1].split(" ");

      for (int i = 0; i < currentSynset.length; i++) {
        String currentNoun = currentSynset[i];

        if (nounToIDs.containsKey(currentNoun)) { // if noun in map, update bag
          nounToIDs.get(currentNoun).add(currentLineID);
        } else { // if not, add new bag
          Bag<Integer> idBag = new Bag<>();
          idBag.add(currentLineID);
          nounToIDs.put(currentNoun, idBag);
        }
      }
      synsetList.add(synsetLine[1]); // split ID from synset, add the string
    }

    // add all hypernyms to graph
    graph = new Digraph(synsetList.size()); // # of vertices should = # of synsets
    In inHyp = new In(hypernyms);
    while (inHyp.hasNextLine()) {
      String[] hypernym = inHyp.readLine().split(",");
      // run through all vertices / hypernyms it points to
      for (int i = 1; i < hypernym.length; i++) {
        graph.addEdge(Integer.parseInt(hypernym[0]), Integer.parseInt(hypernym[i]));
      }
    }

    // debug
    // System.out.println("Graph:\n" + graph);
    // for (String s : synsetList) {
    //    System.out.println(s);
    // }
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return synsetList;
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    // System.out.println(Collections.binarySearch(synsetList, word)); //debug
    return nounToIDs.containsKey(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    SAP sap = new SAP(graph);
    return sap.length(nounToIDs.get(nounA), nounToIDs.get(nounB));
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    SAP sap = new SAP(graph);
    return synsetList.get(sap.ancestor(nounToIDs.get(nounA), nounToIDs.get(nounB)));
  }

  // do unit testing of this class
  public static void main(String[] args) {
    // file location depends on your file structure
    WordNet wordNet = new WordNet("wordnet\\synsets.txt", "wordnet\\hypernyms.txt");
    System.out.println(wordNet.distance("Black_Plague", "black_marlin"));
  }
}