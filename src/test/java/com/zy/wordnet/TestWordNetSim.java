package com.zy.wordnet;

import org.junit.Test;

public class TestWordNetSim {

    @Test
    public void test() {
        String s1 = "dog";
        String s2 = "cat";
        WordNetSim sm = new WordNetSim(s1, s2);
        System.out.println(sm.getSimilarity());
    }
}
