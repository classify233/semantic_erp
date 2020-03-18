package com.zy.wordnet;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.JWSRandom;
import edu.sussex.nlp.jws.JiangAndConrath;
import edu.sussex.nlp.jws.Lin;
import org.junit.Test;

import java.util.TreeMap;

public class TestJWSRandom {
    // Set up Java WordNet::WordNetSim
    String dir = "D:/WordNet";
    JWS ws = new JWS(dir, "2.1");

    @Test
    public void testJWSRandom() {
        // Create a 'JWSRandom' instance: there are 3 constructors that one can use:
        // C1.
        // JWSRandom random = new JWSRandom(dict);
        // default, completely random

        // C2.
        // JWSRandom random = new JWSRandom(dict, true);
        // true = store the randomly generated numbers (default) for a sense pair
        // false = exactly the same behaviour as C1.

        // C3.
        JWSRandom random = new JWSRandom(ws.getDictionary(), true, 16.0);    // set the upper limit on the scores

        // Example Of Use:
        // 1. all senses
        TreeMap<String, Double> map = random.random("apple", "banana", "n");
        for (String pair : map.keySet()) {
            System.out.println(pair + "\t" + (map.get(pair)));
        }

        // 2. max
        System.out.println("max\t\t\t" + random.max("apple", "banana", "n"));
    }

    @Test
    public void testJWS() {
        // 2. EXAMPLES OF USE:

        // 2.1 [JIANG & CONRATH MEASURE]
        JiangAndConrath jcn = ws.getJiangAndConrath();
        System.out.println("Jiang & Conrath\n");

        TreeMap<String, Double> scores1 = jcn.jcn("apple", "banana", "n"); // all senses
        //TreeMap<String, Double> scores1 = jcn.jcn("apple", 1, "banana", "n"); // fixed;all
        //TreeMap<String, Double> scores1 =	jcn.jcn("apple", "banana", 2, "n"); // all;fixed

        for (String s : scores1.keySet())
            System.out.println(s + "\t" + scores1.get(s));
        // specific senses
        System.out.println("\nspecific pair\t=\t" + jcn.jcn("apple", 1, "banana", 1, "n") + "\n");
        // max.
        System.out.println("\nhighest score\t=\t" + jcn.max("apple", "banana", "n") + "\n\n\n");

        // 2.2 [LIN MEASURE]
        Lin lin = ws.getLin();
        System.out.println("Lin\n");
        // all senses
        TreeMap<String, Double> scores2 = lin.lin("apple", "banana", "n");            // all senses
        //TreeMap<String, Double> scores2 = lin.lin("apple", 1, "banana", "n");    // fixed;all
        //TreeMap<String, Double> scores2 = lin.lin("apple", "banana", 2, "n");    // all;fixed
        for (String s : scores2.keySet())
            System.out.println(s + "\t" + scores2.get(s));
        // specific senses
        System.out.println("\nspecific pair\t=\t" + lin.lin("apple", 1, "banana", 1, "n") + "\n");
        // max.
        System.out.println("\nhighest score\t=\t" + lin.max("apple", "banana", "n") + "\n\n\n");

        // ... and so on for any other measure
    }
}
