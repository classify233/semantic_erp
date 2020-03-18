package com.zy.jena;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.zy.db.OntologyDao;
import com.zy.entity.Ontology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadOwl {
    public File owlDirectory = new File(getClass().getResource("/owl").getFile());

    private List<Ontology> ontologies;
    private Map<String, Ontology> ontMap;
    private Ontology rootOnt;   // 树的根节点

    /**
     * 默认读取owl文件夹下的所有文件
     * @throws FileNotFoundException
     */
    public void readOwl() throws FileNotFoundException {
        File[] files = owlDirectory.listFiles();
        for(File f : files) {
            if (f.getName().endsWith(".owl")) {
                System.out.println("正在处理：" + f.getName());
                processOwl(f);
            }
        }
    }

    /**
     * 处理单个文件
     * @param file
     * @throws FileNotFoundException
     */
    public void readOwl(File file) throws FileNotFoundException {
        processOwl(file);
    }

    /**
     * 处理OWL文件，得到所有的Ontology对象
     * @param file
     * @throws FileNotFoundException
     */
    public void processOwl(File file) throws FileNotFoundException {
        ontologies = new ArrayList<>();
        ontMap = new HashMap<>();
        rootOnt = null;

        // 加载本体模型
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(new FileInputStream(file), "");

        // 第一次遍历所有class，生成Map（name --> Ontology）
        for (Iterator<?> i = ontModel.listClasses(); i.hasNext();) {
            OntClass c = (OntClass) i.next();  // 返回类型强制转换
            if (!c.isAnon()) {  //如果不是匿名类，则定义一个Ontology对象
                Ontology ont = new Ontology();
                ont.setName(c.getLocalName());
                ontMap.put(ont.getName(), ont);
                ontologies.add(ont);
            }
        }

        // 第二次遍历所有class，更新父类和子类
        List<Ontology> parentList = null;
        List<Ontology> childIdList = null;

        for (Iterator<?> i = ontModel.listClasses(); i.hasNext();) {
            // 遍历一次，容器清零
            parentList = new ArrayList<>();
            childIdList = new ArrayList<>();

            // 如果不是匿名类，则进行父节点和子节点的完善
            OntClass c = (OntClass) i.next();
            if (!c.isAnon()) {
                Ontology curOnt = ontMap.get(c.getLocalName());

                // 迭代显示当前类的直接父类
                for (Iterator<?> it = c.listSuperClasses(); it.hasNext();){
                    OntClass sp = (OntClass) it.next();
                    parentList.add(ontMap.get(sp.getLocalName()));
                }
                if (parentList.size() == 1)
                    curOnt.setParent(parentList.get(0));
                if (parentList.size() == 0) {   // 没有父节点
                    curOnt.setLevel(0);
                    curOnt.setRoot(curOnt);
                    rootOnt = curOnt;
                }

                // 迭代显示当前类的直接子类
                for (Iterator<?> it = c.listSubClasses(); it.hasNext();) {
                    OntClass sb = (OntClass) it.next();
                    childIdList.add(ontMap.get(sb.getLocalName()));
                    //childIdBuf.add(sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()).substring(4)+"~");
                }
                curOnt.setChildren(childIdList);
            }
        }

        // 从根节点开始更新树节点的高度level，递归更新
        updateLevel(rootOnt);

        // 把所有本体存储到数据库
        storeDb();

        System.out.println("文档" + file.getName() + "处理完毕......");
    }

    // 更新当前节点所有子节点的level
    private void updateLevel(Ontology ont) {
        if (ont.getChildren().size() > 0) {
            for(Ontology o : ont.getChildren()) {
                o.setLevel(ont.getLevel() + 1);
                o.setRoot(rootOnt);
                updateLevel(o);
            }
        }
    }

    // 把所有本体存储到数据库
    private void storeDb() {
        for(Ontology ont : ontologies) {
            // 存储到数据库，并更新自己的ID
            long pk = (Long) OntologyDao.insert(ont);
            ont.setID((int)pk);
        }

        for(Ontology ont : ontologies) {
            // 更新parentID和childrenID，有可能为空
            if (ont.getParent() != null) {
                ont.setParentID(ont.getParent().getID());
            }

            if (ont.getChildren() != null && ont.getChildren().size() > 0) {
                List<Integer> childrenId = new ArrayList<>();
                for(Ontology child : ont.getChildren()) {
                    childrenId.add(child.getID());
                }
                ont.setChildrenID(childrenId);
            }
            OntologyDao.update(ont);
        }
    }
}
