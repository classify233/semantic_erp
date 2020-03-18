package com.zy.xmlparser;

import com.zy.db.OntologyDao;
import com.zy.entity.Ontology;
import com.zy.entity.Service;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceXmlGeneratorTest {

    @Test
    public void generateXml() {
        Ontology in = OntologyDao.selectById(39);
        Ontology out = OntologyDao.selectById(32);

        Service service = new Service();
        service.setInput(in);
        service.setOutput(out);
        service.setServiceName("fuckService");
        service.setProvidedBy("com.fuck.Produce");
        service.setTextDescription("null");
        service.setServiceCategory("吴");
        service.setQosTime(13212.23);
        service.setQosThroughout(21.56);

        ServiceXmlGenerator generator = new ServiceXmlGenerator(service);
        generator.generateXml("src/main/resources/output/test.xml");
    }
}