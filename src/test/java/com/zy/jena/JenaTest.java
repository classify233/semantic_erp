package com.zy.jena;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class JenaTest {

    URL url = getClass().getClassLoader().getResource("owl/hr_management.owl");
    File file = new File(url.getFile());

    @Test
    public void test() throws FileNotFoundException {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(new FileInputStream(file), "");

        List<String> parentList = new ArrayList<>();
        List<String> childIdList = new ArrayList<>();

        // 遍历所有class
        for (Iterator<?> i = ontModel.listClasses(); i.hasNext();) {

            parentList.clear();
            childIdList.clear();

            OntClass c = (OntClass) i.next();  // 返回类型强制转换
            if (!c.isAnon()) {  //如果不是匿名类，则打印类的名字

                System.out.println("【" + c.getLocalName() + "】");

                // 迭代显示当前类的直接父类
                for (Iterator<?> it = c.listSuperClasses(); it.hasNext();){
                    OntClass sp = (OntClass) it.next();
                    parentList.add(sp.getLocalName());
                    //parentList.add(sp.getModel().getGraph().getPrefixMapping().shortForm(sp.getURI()).substring(4));
                }

                // 迭代显示当前类的直接子类
                for (Iterator<?> it = c.listSubClasses(); it.hasNext();) {
                    OntClass sb = (OntClass) it.next();
                    childIdList.add(sb.getLocalName());
                    //childIdBuf.add(sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()).substring(4)+"~");
                }

                System.out.println("============================");
                System.out.println("父节点：" + parentList);
                System.out.println("子节点：" + childIdList.toString());

                StmtIterator iterator = c.listProperties();
                while(iterator.hasNext()){
                    Statement statement = iterator.next();
                    String predict = statement.getPredicate().toString().substring(statement.getPredicate().toString().indexOf("#")+1);
                    String object = statement.getObject().toString();
                    System.out.println(predict + " -----> " + object);
                }

                System.out.println("============================\n");
            }
        }
    }
}