package com.zy.db;

import com.zy.entity.Ontology;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OntologyDaoTest {

    @Test
    public void insert() {
        Ontology ont = new Ontology(
                -1,"employee", 1, 2, "", "雇员");
        Object pk = OntologyDao.insert(ont);
        System.out.println(pk);
    }

    @Test
    public void insert2() {
        Ontology ont = new Ontology();
        ont.setName("manager2");
        ont.setLevel(0);
        Object pk = OntologyDao.insert(ont);
        System.out.println(pk);
    }

    @Test
    public void selectById() {
        Ontology ontology = OntologyDao.selectById(4);
        System.out.println(ontology);
    }

    @Test
    public void select() {
        List<Ontology> ontologies = OntologyDao.select("", null);
        for(Ontology o : ontologies) {
            System.out.println(o);
        }
    }
}