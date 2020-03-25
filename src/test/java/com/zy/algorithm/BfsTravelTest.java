package com.zy.algorithm;

import com.zy.db.OntologyDao;
import com.zy.gragh.Graph;
import com.zy.gragh.GraphNode;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BfsTravelTest {

    @Test
    public void getTravelPath() {
        Graph graph = new Graph();
        graph.initStartEndNode(OntologyDao.selectById(34),
                OntologyDao.selectById(36), "string", "int");
        BfsTravel bfsTravel = new BfsTravel(graph);
        List<GraphNode> travelPath = bfsTravel.getTravelPath();
        System.out.println(travelPath);
    }
}