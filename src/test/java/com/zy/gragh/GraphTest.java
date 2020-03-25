package com.zy.gragh;

import com.zy.db.OntologyDao;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void makeGraph() {
        Graph graph = new Graph();
        graph.initStartEndNode(OntologyDao.selectById(34),
                OntologyDao.selectById(36), "string", "int");
    }
}