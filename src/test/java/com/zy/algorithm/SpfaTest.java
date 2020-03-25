package com.zy.algorithm;

import com.zy.db.OntologyDao;
import com.zy.gragh.Graph;
import com.zy.gragh.GraphNode;
import com.zy.similarity.SimilarityUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SpfaTest {

    @Test
    public void getTravelPath() {
        Graph graph = new Graph();
        graph.initStartEndNode(OntologyDao.selectById(34),
                OntologyDao.selectById(36), "string", "int");

        Spfa spfa = new Spfa(graph);
        List<GraphNode> travelPath = spfa.getTravelPath();
        System.out.println("路径长度:" + travelPath.size());

        for (int i = 0; i < travelPath.size(); i++) {
            GraphNode node = travelPath.get(i);
            if (i == 0)
                System.out.println("startNode:" + node.getService().getOutput().getName() + "-->");
            else if (i == travelPath.size() - 1)
                System.out.println("endNode:" + node.getService().getInput().getName() + "-->");
            else
                System.out.println("node" + node.getIndex() + ":" +
                        node.getService().getInput().getName() + " "
                        + node.getService().getOutput().getName() + "-->");
            if (i != 0) {
                double similarity = new SimilarityUtil(travelPath.get(i - 1)
                        .getService(), node.getService()).getSimilarity();
                System.out.println("本体" + travelPath.get(i - 1).getService().getOutput().getName()
                        + "和本体" + node.getService().getInput().getName() + "的相似度" + similarity);
                assert similarity > Graph.SIM_THRESHOLD;
            }
            System.out.println();
        }
    }
}