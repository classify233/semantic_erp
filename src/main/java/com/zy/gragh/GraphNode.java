package com.zy.gragh;

import com.zy.entity.Service;

import java.util.List;

public class GraphNode implements Comparable<GraphNode> {
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

    @Override
    public int compareTo(GraphNode o) {
        // 根据Qos对节点进行排序
        double qosTotal = this.getService().getQosTotal();
        double qosTotal_ = o.getService().getQosTotal();
        if (qosTotal + 1e5 < qosTotal_)       // 为了保证浮点数的精度，使用1e5
            return -1;
        else if (qosTotal > qosTotal_ + 1e5)
            return 1;
        else
            return 0;
    }
}
