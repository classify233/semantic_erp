package com.zy.gragh;

import com.zy.entity.Service;

import java.util.List;

public class GraphNode {
    /**
     * input和output节点可以封装成一个空的服务，有用的只有本体和type
     */
    private Service service;

    private List<GraphNode> nexts;

    public GraphNode() {
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<GraphNode> getNexts() {
        return nexts;
    }

    public void setNexts(List<GraphNode> nexts) {
        this.nexts = nexts;
    }
}
