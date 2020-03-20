package com.zy.random;

import com.zy.entity.Ontology;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GenServiceTest {

    @Test
    public void genService() {
        GenService.genService(100);
    }

    @Test
    public void loadOnt() {
        GenService.loadOnt();
        List<Ontology> ontologies = GenService.getOntologies();
        System.out.println(ontologies.size());
    }
}