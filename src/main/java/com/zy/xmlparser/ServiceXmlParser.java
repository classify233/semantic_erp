package com.zy.xmlparser;

import com.zy.db.OntologyDao;
import com.zy.entity.Service;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.xpath.DefaultXPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ServiceXmlParser {
    private File xmlFile;

    private Service service;

    private InputStream is;
    private Document document;

    public ServiceXmlParser(File xmlFile) {
        this.xmlFile = xmlFile;

        // 获取文档
        try {
            this.is = new FileInputStream(xmlFile);
            SAXReader reader = new SAXReader();
            this.document = reader.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Service getService() {

        if (service != null) {    // 延迟初始化
            return service;
        }

        this.service = new Service();

        setInput();
        setOutput();

        setProvidedBy();
        setServiceName();
        setTextDescription();
        setServiceCategory();

        setQosTime();
        setQosThroughout();

        // 关闭输入流
        try {
            is.close();
            is = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return service;
    }

    private void setProvidedBy() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/description/providedBy");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            String providedBy = element.getTextTrim();
            service.setProvidedBy(providedBy);
        }
    }

    private void setServiceName() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/description/serviceName");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            String serviceName = element.getTextTrim();
            service.setServiceName(serviceName);
        }
    }

    private void setTextDescription() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/description/textDescription");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            String textDescription = element.getTextTrim();
            service.setTextDescription(textDescription);
        }
    }

    private void setServiceCategory() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/description/serviceCategory");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            String serviceCategory = element.getTextTrim();
            service.setServiceCategory(serviceCategory);
        }
    }

    private void setQosTime() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/description/qos/time");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            String qosTime = element.getTextTrim();
            service.setQosTime(Double.valueOf(qosTime));
        }
    }

    private void setQosThroughout() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/description/qos/throughout");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            String qosThroughout = element.getTextTrim();
            service.setQosThroughout(Double.valueOf(qosThroughout));
        }
    }

    private void setInput() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/process/hasInput/input");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            // System.out.println(element.getTextTrim());
            String id = element.attributeValue("id");
            String datatype = element.attributeValue("datatype");
            service.setInput(OntologyDao.selectByName(id));
            service.setInputType(datatype);
        }
    }

    private void setOutput() {
        // 获取xml节点
        XPath xPath = new DefaultXPath(
                "/profile/process/hasOutput/output");
        List<Element> list = xPath.selectNodes(document);

        for(Element element : list) {
            // System.out.println(element.getTextTrim());
            String id = element.attributeValue("id");
            String datatype = element.attributeValue("datatype");
            service.setOutput(OntologyDao.selectByName(id));
            service.setOutputType(datatype);
        }
    }
}
