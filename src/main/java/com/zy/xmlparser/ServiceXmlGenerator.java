package com.zy.xmlparser;

import com.zy.entity.Service;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.PrintWriter;

public class ServiceXmlGenerator {
    private Service service;

    private String outputXmlName;
    private Document doc;
    private Element root;

    public ServiceXmlGenerator(Service service) {
        this.service = service;
    }

    public void generateXml(String filePath) {
        outputXmlName = filePath;

        doc = DocumentHelper.createDocument();
        root = doc.addElement("profile");

        addOntologyElement();
        addProcessElement();
        addDescriptionElement();

        doWriteXml();
    }

    private void doWriteXml() {
        // 写Xml
        XMLWriter xmlWriter = null;
        PrintWriter printWriter = null;
        try {
            OutputFormat prettyPrint = OutputFormat.createPrettyPrint();
            printWriter = new PrintWriter(outputXmlName);
            xmlWriter = new XMLWriter(printWriter, prettyPrint);
            xmlWriter.write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (printWriter != null)
                printWriter.close();
        }
    }

    private void addOntologyElement() {
        Element ontElement = root.addElement("ontology");

        // 添加import子标签
        String inName = service.getInput().getName();
        Element e1 = ontElement.addElement("import");
        e1.addAttribute("resource", inName);

        String outName = service.getOutput().getName();
        Element e2 = ontElement.addElement("import");
        e2.addAttribute("resource", outName);
    }

    private void addProcessElement() {
        Element processElement = root.addElement("process");

        Element hasInput = processElement.addElement("hasInput");
        Element input = hasInput.addElement("input");
        input.addAttribute("id", service.getInput().getName());
        input.addAttribute("datatype", service.getInputType());

        Element hasOutput = processElement.addElement("hasOutput");
        Element output = hasOutput.addElement("output");
        output.addAttribute("id", service.getOutput().getName());
        output.addAttribute("datatype", service.getOutputType());
    }

    private void addDescriptionElement() {
        Element descElement = root.addElement("description");

        Element providedBy = descElement.addElement("providedBy");
        providedBy.setText(service.getProvidedBy());

        Element serviceName = descElement.addElement("serviceName");
        serviceName.setText(service.getServiceName());

        Element textDescription = descElement.addElement("textDescription");
        textDescription.setText(service.getTextDescription());

        Element serviceCategory = descElement.addElement("serviceCategory");
        serviceCategory.setText(service.getServiceCategory());

        addQosElement(descElement);
    }

    private void addQosElement(Element descElement) {
        Element qos = descElement.addElement("qos");

        Element time = qos.addElement("time");
        time.setText(String.valueOf(service.getQosTime()));

        Element throughout = qos.addElement("throughout");
        throughout.setText(String.valueOf(service.getQosThroughout()));
    }
}