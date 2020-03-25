package com.zy.algorithm;

import com.zy.gragh.Graph;
import com.zy.gragh.GraphNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Spfa {
    private Graph graph;
    private int nodeNum;

    private boolean isFound = false;

    private double[] minDistance;

    // 保存路径
    private LinkedList<GraphNode> path;

    public Spfa(Graph graph) {
        this.graph = graph;
        this.nodeNum = graph.services.size() + 2;
        solve();
    }

    private void solve() {
        // 清除节点的使用标记
        for(GraphNode node : graph.serviceNodes) {
            node.resetFlag();
        }
        graph.startNode.resetFlag();
        graph.endNode.resetFlag();

        // 初始化最短距离和队列
        minDistance = new double[nodeNum];
        for (int i = 0; i < minDistance.length; i++) {
            minDistance[i] = Double.MAX_VALUE;
        }
        Queue<GraphNode> queue = new LinkedList<>();

        minDistance[graph.startNode.getIndex()] = 0;   // 开始节点到自身的距离为0
        graph.startNode.isUsed = true;
        graph.startNode.accessTime = 1;
        queue.offer(graph.startNode);

        while (!queue.isEmpty()) {
            GraphNode pollNode = queue.poll();

            for(GraphNode nn : pollNode.getNexts()) {
                double tmp = minDistance[pollNode.getIndex()] + calDistance(pollNode, nn);

                if (minDistance[nn.getIndex()] > tmp) {
                    minDistance[nn.getIndex()] = tmp;
                    nn.before = pollNode;

                    if (!nn.isUsed) {
                        queue.offer(nn);
                        nn.accessTime++;
                        if (nn.accessTime > nodeNum) {
                            isFound = false;
                            return;
                        }
                        nn.isUsed = true;
                    }
                }
            }

            pollNode.isUsed = false;
        }
        isFound = true;
    }

    public List<GraphNode> getTravelPath() {
        if (path != null)
            return path;

        if (!isFound)
            return null;

        path = new LinkedList<>();
        GraphNode curNode = graph.endNode;

        while (curNode != graph.startNode) {
            path.addFirst(curNode);
            //curNode = before.get(curNode);
            curNode = curNode.before;

            if (curNode == null) {
                System.err.println("Error!找不到上一个节点");
                isFound = false;
                path = null;
                return path;
            }
        }
        path.addFirst(graph.startNode);
        return path;
    }

    private double calDistance(GraphNode pollNode, GraphNode nn) {
        return 1;
    }
}