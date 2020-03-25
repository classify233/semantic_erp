package com.zy.algorithm;

import com.zy.gragh.Graph;
import com.zy.gragh.GraphNode;

import java.util.*;

/**
 * BFS + 贪心算法
 */
public class BfsTravel {
    private Graph graph;

    private boolean isFound = false;

    // 记录当前节点的前一个节点
    // private Map<GraphNode, GraphNode> before = new HashMap<>();

    // 保存路径
    LinkedList<GraphNode> path;

    public BfsTravel(Graph graph) {
        this.graph = graph;
        solve();
    }

    private void solve() {
        // 广度优先搜索，贪心法优先搜索Qos大的
        // 优先队列
        PriorityQueue<GraphNode> queue = new PriorityQueue<>();
        queue.add(graph.startNode);

        while (!queue.isEmpty()) {
            GraphNode pollNode = queue.poll();
            pollNode.isUsed = true;    // 访问标记

            if (pollNode == graph.endNode) {   // 结束BFS
                isFound = true;   // 找到标记
                return;
            }

            for (GraphNode n : pollNode.getNexts()) {
                if (!n.isUsed) {
                    //before.put(n, pollNode);   // 记录前一个节点
                    n.before = pollNode;
                    queue.offer(n);
                }
            }
        }
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
}
