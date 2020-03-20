package com.zy.random;

import com.zy.entity.Ontology;
import com.zy.db.OntologyDao;
import com.zy.entity.Service;
import com.zy.similarity.BasicType;
import com.zy.xmlparser.ServiceXmlGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GenService {
    public static double MAX_TIME = 1000;
    public static double MIN_TIME = 0;

    public static double MAX_THROUGHOUT = 100;
    public static double MIN_THROUGHOUT = 0;

    private static int counter = 0;

    private static List<Ontology> ontologies;

    private static Random rand = new Random();

    private static Service genService;

    private static BasicType[] types = {BasicType.BOOLEAN, BasicType.FLOAT,
            BasicType.INT, BasicType.STRING, BasicType.DATE};

    // 从数据库加载本体
    public static void loadOnt() {
        ontologies = OntologyDao.selectAll();
    }

    public static List<Ontology> getOntologies() {
        return ontologies;
    }

    public static Service getRandService() {
        genService = new Service();
        genService.setInput(ontologies.get(rand.nextInt(ontologies.size())));
        genService.setInputType(types[rand.nextInt(types.length)].getVal());

        genService.setOutput(ontologies.get(rand.nextInt(ontologies.size())));
        genService.setOutputType(types[rand.nextInt(types.length)].getVal());

        genService.setQosThroughout(getRandThroughout());
        genService.setQosTime(getRandTime());

        // 随机生成服务名
        String serviceName = getRandServiceName();
        genService.setProvidedBy("com.zy.service." + serviceName);
        genService.setServiceName(serviceName);

        genService.setServiceCategory(getRandCategory());
        genService.setTextDescription(getRandDescription());

        return genService;
    }

    private static String getRandDescription() {
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        return dateString + "随机生成服务" + genService.getServiceName();
    }

    private static String getRandCategory() {
        String[] categories = {"produce", "finance", "humanResource",
                "information", "afterSale", "sale"};
        return categories[rand.nextInt(categories.length)];
    }

    private static String getRandServiceName() {
        String nameStr = "S_" + genService.getInput().getName() + "_" +
                genService.getOutput().getName() + "_" + String.format("%04d", counter);
        counter++;
        return nameStr;
    }

    private static double getRandTime() {
        return rand.nextDouble() * (MAX_TIME - MIN_TIME) + MIN_TIME;
    }

    private static double getRandThroughout() {
        return rand.nextDouble() * (MAX_THROUGHOUT - MIN_THROUGHOUT) + MIN_THROUGHOUT;
    }

    /**
     * 生成count数量的Service并写到文件系统
     *
     * @param count
     */
    public static void genService(int count) {
        loadOnt();

        for (int i = 0; i < count; i++) {
            // 生成随机服务
            Service randService = getRandService();

            // 生成随机文件名
            String randomName = randService.getServiceName() + ".xml";
            String filepath = "src/main/resources/out_services/" + randomName;

            // 写到文件系统
            ServiceXmlGenerator generator = new ServiceXmlGenerator(randService);
            generator.generateXml(filepath);

            System.out.println("文档" + randomName + "处理完毕.......");
        }
    }
}
