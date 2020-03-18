package com.zy.xmlparser;

import com.zy.db.ServiceDao;
import com.zy.entity.Service;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ServiceXmlParserTest {

    @Test
    public void getService() {
        File xmlFile = new File(getClass().getResource("/services/DiseaseSearch.xml").getFile());
        ServiceXmlParser parser = new ServiceXmlParser(xmlFile);
        Service service = parser.getService();
        System.out.println(service);

        Object pk = ServiceDao.insert(service);
        long pk_ = ((Long) pk).longValue();
        Service service1 = ServiceDao.selectById((int) pk_);
        System.out.println(service1);
    }
}