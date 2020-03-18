package com.zy.entity;

import com.zy.db.OntologyDao;
import com.zy.utils.MapToEntity;

import java.util.HashMap;

public class Service {
    private int id = -1;

    private Ontology input;
    private Ontology output;

    private String inputType;
    private String outputType;

    private String providedBy;
    private String serviceName;
    private String textDescription;
    private String serviceCategory;

    private double qosTime;
    private double qosThroughout;

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ontology getInput() {
        return input;
    }

    public void setInput(Ontology input) {
        this.input = input;
    }

    public Ontology getOutput() {
        return output;
    }

    public void setOutput(Ontology output) {
        this.output = output;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getProvidedBy() {
        return providedBy;
    }

    public void setProvidedBy(String providedBy) {
        this.providedBy = providedBy;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public double getQosTime() {
        return qosTime;
    }

    public void setQosTime(double qosTime) {
        this.qosTime = qosTime;
    }

    public double getQosThroughout() {
        return qosThroughout;
    }

    public void setQosThroughout(double qosThroughout) {
        this.qosThroughout = qosThroughout;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", input=" + input.getName() +
                ", output=" + output.getName() +
                ", inputType='" + inputType + '\'' +
                ", outputType='" + outputType + '\'' +
                ", providedBy='" + providedBy + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", textDescription='" + textDescription + '\'' +
                ", serviceCategory='" + serviceCategory + '\'' +
                ", qosTime=" + qosTime +
                ", qosThroughout=" + qosThroughout +
                '}';
    }

    public static Service parse(HashMap<String, Object> map) {
        Service service = (Service) MapToEntity.mapToEntity(map, Service.class);
        // input和output单独设置
        if (map.get("input") != null)
            service.setInput(OntologyDao.selectById((Integer) map.get("input")));
        if (map.get("output") != null)
            service.setOutput(OntologyDao.selectById((Integer) map.get("output")));

        return service;
    }
}