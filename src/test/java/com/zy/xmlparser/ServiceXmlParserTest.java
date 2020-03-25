package com.zy.xmlparser;

import com.zy.db.ServiceDao;
import com.zy.entity.Service;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

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

    @Test
    public void getAllService() {

        // 获得文件夹下的所有xml文件
        File dir = new File(getClass()
                .getResource("/out_services").getFile());
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".xml");
            }
        });

        for(File xmlFile : files) {
            // 解析xml，获得Service对象
            ServiceXmlParser parser = new ServiceXmlParser(xmlFile);
            Service service = parser.getService();
            System.out.println(service);

            // 插入Service对象到数据库
            Object pk = ServiceDao.insert(service);
            /*long pk_ = ((Long) pk).longValue();
            Service service1 = ServiceDao.selectById((int) pk_);
            System.out.println(service1);*/

            System.out.println(service.getServiceName() + " 插入到数据库...");
        }
    }
}