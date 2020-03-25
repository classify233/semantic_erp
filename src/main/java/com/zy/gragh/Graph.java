package com.zy.gragh;

import com.zy.db.ServiceDao;
import com.zy.entity.Ontology;
import com.zy.entity.Service;
import com.zy.similarity.SimilarityUtil;

import java.util.*;

/**
 * 生成有向图
 */
public class Graph {
    public static final double SIM_THRESHOLD = 0.1;

    public GraphNode[] serviceNodes;
    public GraphNode startNode;
    public GraphNode endNode;

    // public int startIndex;
    // public int endIndex;

    public List<Service> services;    // 服务集合

    private Ontology inputOnt;
    private Ontology outputOnt;
    private String inputType;
    private String outputType;

    private double[][] edgeWeight;    // edgeWeight[i][j]表示services[i]->services[j]有向边的权重

    public Graph() {
        // 从数据库得到所有服务
        this.services = ServiceDao.getAllServices();
        System.out.println("数据库导入服务列表成功...");

        initServiceNode();
    }

    public Graph(Ontology inputOnt, Ontology outputOnt, String inputType,
                 String outputType) {
        // 从数据库得到所有服务
        this.services = ServiceDao.getAllServices();
        System.out.println("数据库导入服务列表成功...");

        initServiceNode();
        initStartEndNode(inputOnt, outputOnt, inputType, outputType);
    }

    public Graph(List<Service> services, Ontology inputOnt,
                 Ontology outputOnt, String inputType, String outputType) {
        this.services = services;
        initServiceNode();
        initStartEndNode(inputOnt, outputOnt, inputType, outputType);
    }

    // 初始化中间节点
    private void initServiceNode() {

        edgeWeight = new double[services.size() + 2][services.size() + 2];

        // 初始化所有中间service节点
        serviceNodes = new GraphNode[services.size()];
        for (int i = 0; i < services.size(); i++) {
            GraphNode nodeI = new GraphNode();
            nodeI.setService(services.get(i));
            nodeI.setNexts(new ArrayList<>());
            nodeI.setIndex(i);
            serviceNodes[i] = nodeI;
        }

        // 计算服务之间的相似度(i->j)
        for (int i = 0; i < services.size(); i++) {
            for (int j = 0; j < services.size(); j++) {
                if (j == i)
                    edgeWeight[i][j] = 1;
                else {
                    SimilarityUtil util = new SimilarityUtil(services.get(i), services.get(j));
                    double sim = util.getSimilarity();
                    edgeWeight[i][j] = sim;

                    // 如果超过阈值
                    if (sim > SIM_THRESHOLD) {
                        serviceNodes[i].getNexts().add(serviceNodes[j]);
                    }
                }
            }
        }

        System.out.println("中间服务节点初始化完成...");
    }

    public void initStartEndNode(Ontology inputOnt, Ontology outputOnt,
                                  String inputType, String outputType) {
        // 初始化输入和输出
        this.inputOnt = inputOnt;
        this.outputOnt = outputOnt;
        this.inputType = inputType;
        this.outputType = outputType;

        // 封装start和end节点
        initStartNode();
        initEndNode();

        /*// 初始化edgeWeight
        startIndex = services.size();
        endIndex = services.size() + 1;*/

        // 计算输入与各服务之间的相似度
        calInputWeight();
        // 计算输出与各服务之间的相似度
        calOutputWeight();

        System.out.println("输入输出节点初始化完成...");
    }

    private void initStartNode() {
        Service startService = new Service();
        startService.setOutput(inputOnt);
        startService.setOutputType(inputType);
        startService.setQosTime(Service.MAX_TIME);
        startService.setQosThroughout(Service.MAX_THROUGHOUT);

        startNode = new GraphNode();
        startNode.setService(startService);
        startNode.setNexts(new ArrayList<>());
        startNode.setIndex(services.size());
    }

    private void initEndNode() {
        Service endService = new Service();
        endService.setInput(outputOnt);
        endService.setInputType(outputType);
        endService.setQosTime(Service.MAX_TIME);
        endService.setQosThroughout(Service.MAX_THROUGHOUT);

        endNode = new GraphNode();
        endNode.setService(endService);
        endNode.setNexts(new ArrayList<>());
        endNode.setIndex(services.size() + 1);
    }

    // 计算input节点和其它节点的权重
    private void calInputWeight() {
        for (int i = 0; i < services.size(); i++) {
            SimilarityUtil util = new SimilarityUtil(startNode.getService(), services.get(i));
            double sim = util.getSimilarity();
            edgeWeight[startNode.getIndex()][i] = sim;

            if (sim > SIM_THRESHOLD) {
                startNode.getNexts().add(serviceNodes[i]);
            }
        }
    }

    private void calOutputWeight() {

        for (int i = 0; i < services.size(); i++) {
            SimilarityUtil util = new SimilarityUtil(services.get(i), endNode.getService());
            double sim = util.getSimilarity();
            edgeWeight[i][endNode.getIndex()] = sim;

            if (sim > SIM_THRESHOLD) {   // 把结束插到nexts list的开头
                serviceNodes[i].getNexts().add(0, endNode);
            }
        }
    }

    /*// 深度优先搜索，贪心法优先搜索Qos大的
    private void dfs() {
        doDfs(startNode);
    }

    private void doDfs(GraphNode curNode) {
        Collections.sort(curNode.getNexts());

        for(GraphNode node : curNode.getNexts()) {
            if (node == endNode) {   // 结束DFS
                return;
            }
            doDfs(node);
        }
    }*/
}
