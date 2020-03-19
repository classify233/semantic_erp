package com.zy.gragh;

import com.zy.entity.Ontology;
import com.zy.entity.Service;
import com.zy.similarity.SimilarityUtil;

import java.util.List;

/**
 * 生成有向图
 */
public class Graph {
    private static double SIM_THRESHOLD = 0.5;

    private List<Service> services;    // 服务集合

    private Ontology inputOnt;
    private Ontology outputOnt;
    private String inputType;
    private String outputType;

    private GraphNode start;
    private int startIndex;
    private GraphNode end;
    private int endIndex;

    private double[][] edgeWeight;    // edgeWeight[i][j]表示services[i]->services[j]有向边的权重

    public Graph(List<Service> services, Ontology inputOnt,
                 Ontology outputOnt, String inputType, String outputType) {

        this.services = services;
        this.inputOnt = inputOnt;
        this.outputOnt = outputOnt;
        this.inputType = inputType;
        this.outputType = outputType;

        // 封装start和end节点
        start = new GraphNode();
        Service startService = new Service();
        startService.setOutput(inputOnt);
        startService.setOutputType(inputType);
        start.setService(startService);

        end = new GraphNode();
        Service endService = new Service();
        endService.setInput(outputOnt);
        endService.setInputType(outputType);
        end.setService(endService);

        // 初始化edgeWeight
        startIndex = services.size();
        endIndex = services.size() + 1;
        edgeWeight = new double[endIndex + 1][endIndex + 1];
    }

    public void makeGraph() {

        for (int i = 0; i < services.size(); i++) {
            for (int j = 0; j < services.size(); j++) {
                if (j == i)
                    edgeWeight[i][j] = 1;
                else {
                    SimilarityUtil util = new SimilarityUtil(services.get(i), services.get(j));
                    double sim = util.getSimilarity();
                    edgeWeight[i][j] = sim;
                }
            }
        }

        calInputWeight();
        calOutputWeight();
    }

    // 计算input节点和其它节点的权重
    private void calInputWeight() {

        for (int i = 0; i < services.size(); i++) {
            SimilarityUtil util = new SimilarityUtil(start.getService(), services.get(i));
            double sim = util.getSimilarity();
            edgeWeight[startIndex][i] = sim;
        }
    }

    private void calOutputWeight() {

        for (int i = 0; i < services.size(); i++) {
            SimilarityUtil util = new SimilarityUtil(services.get(i), end.getService());
            double sim = util.getSimilarity();
            edgeWeight[i][endIndex] = sim;
        }
    }
}
