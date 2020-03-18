package com.zy.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Ontology {
    private int ID = -1;
    private String name;
    private int level = -1;
    private int parentID = -1;   // parentID默认值 < 0， 说明没有父节点
    private List<Integer> childrenID;
    private String remarks = "";

    private List<Ontology> children;
    private Ontology parent;
    private Ontology root;

    public Ontology() {
    }

    public Ontology(int ID, String name, int level, int parentID,
                    List<Integer> childrenID, String remarks) {
        this.ID = ID;
        this.name = name;
        this.level = level;
        this.parentID = parentID;
        this.childrenID = childrenID;
        this.remarks = remarks;
    }

    public Ontology(int ID, String name, int level, int parentID,
                    String childrenIDStr, String remarks) {
        this.ID = ID;
        this.name = name;
        this.level = level;
        this.parentID = parentID;
        setChildrenID(childrenIDStr);
        this.remarks = remarks;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public List<Integer> getChildrenID() {
        return childrenID;
    }

    public String getChildrenIDStr() {
        if (childrenID == null || childrenID.isEmpty()) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (Integer id : this.childrenID) {
            out.append(id + ",");
        }
        if (out.length() > 0)
            out.deleteCharAt(out.length() - 1);
        return out.toString();
    }

    public List<Ontology> getChildren() {
        return children;
    }

    public void setChildren(List<Ontology> children) {
        this.children = children;
    }

    public Ontology getParent() {
        return parent;
    }

    public void setParent(Ontology parent) {
        this.parent = parent;
    }

    public void setChildrenID(List<Integer> childrenID) {
        this.childrenID = childrenID;
    }

    public void setChildrenID(String IDString) {

        this.childrenID = new ArrayList<>();

        if (IDString != null && !IDString.equals("")) {
            String[] strs = IDString.split(",");
            if (strs.length > 0) {
                for (String str : strs) {
                    childrenID.add(Integer.parseInt(str));
                }
            }
        }
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Ontology getRoot() {
        return root;
    }

    public void setRoot(Ontology root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "Ontology{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", parentID=" + parentID +
                ", childrenID=" + childrenID +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    /**
     * map转换成Ontology
     * childrenid是用“,”隔开的字符串，需要特殊处理，不用使用MapToEntity工具类
     *
     * @param map
     * @return
     */
    public static Ontology parse(HashMap<String, Object> map) {

        Ontology ont = new Ontology();

        if (map.get("id") != null) {
            long id = ((Long) map.get("id")).longValue();
            ont.setID((int) id);
        }

        if (map.get("name") != null)
            ont.setName((String) map.get("name"));
        if (map.get("level") != null)
            ont.setLevel((Integer) map.get("level"));
        if (map.get("parentid") != null)
            ont.setParentID((Integer) map.get("parentid"));
        if (map.get("childrenid") != null)
            ont.setChildrenID((String) map.get("childrenid"));
        if (map.get("remarks") != null)
            ont.setRemarks((String) map.get("remarks"));
        return ont;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ontology ontology = (Ontology) o;
        return ID == ontology.ID &&
                Objects.equals(name, ontology.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }
}