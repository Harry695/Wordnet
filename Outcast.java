import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
  private WordNet wordNet;

  public Outcast(WordNet wordnet) { // constructor takes a WordNet object
		if (wordnet == null) {
			throw new IllegalArgumentException();
		}

    this.wordNet = wordnet;
  }

  public String outcast(String[] nouns) { // given an array of WordNet nouns, return an outcast
    int maxDist = -1;
    String outcast = "";

    for (String nounA : nouns) {
      int distSum = 0;

      for (String nounB : nouns) {
        distSum += wordNet.distance(nounA, nounB);
      }

      if (distSum > maxDist) {
        maxDist = distSum;
        outcast = nounA;
      }
    }

    return outcast;
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
 }