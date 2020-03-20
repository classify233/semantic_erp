package com.zy.db;

import com.zy.entity.Ontology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OntologyDao {
    /**
     * 插入本体，返回主键
     * @param ont
     * @return
     */
    public static Object insert(Ontology ont) {
        String sql= "INSERT INTO ontology(name,level,parentID,childrenID,remarks) VALUES(?,?,?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(ont.getName());
        params.add(ont.getLevel());
        params.add(ont.getParentID());
        params.add(ont.getChildrenIDStr());
        params.add(ont.getRemarks());

        Object pk = DbHelper.executeInsert(sql, params);
        return pk;
    }

    public static Ontology selectById(int id) {
        String sql = "SELECT * FROM ontology WHERE ID = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);

        ArrayList<HashMap<String, Object>> ret = DbHelper.executeQuery(sql, params);
        if (ret == null || ret.isEmpty())
            return null;
        HashMap<String, Object> map = ret.get(0);
        return Ontology.parse(map);
    }

    public static Ontology selectByName(String name) {
        String sql = "SELECT * FROM ontology WHERE name = ?";
        List<Object> params = new ArrayList<>();
        params.add(name);

        ArrayList<HashMap<String, Object>> ret = DbHelper.executeQuery(sql, params);
        if (ret == null || ret.isEmpty())
            return null;
        HashMap<String, Object> map = ret.get(0);
        return Ontology.parse(map);
    }

    public static List<Ontology> selectAll() {
        return select(null, null);
    }

    /**
     * 查询语句
     * @param conditionStr 条件语句（WHERE,OEDER BY)，参数用?代替
     * @param params
     * @return
     */
    public static List<Ontology> select(String conditionStr, List<Object> params) {
        if (conditionStr == null)
            conditionStr = "";

        List<Ontology> ret = new ArrayList<>();

        String sql = "SELECT * FROM ontology " + conditionStr;
        ArrayList<HashMap<String, Object>> mapList = DbHelper.executeQuery(sql, params);
        if (mapList == null || mapList.isEmpty())
            return ret;

        for(HashMap<String, Object> map : mapList) {
            ret.add(Ontology.parse(map));
        }
        return ret;
    }

    public static int deleteById(int id) {
        String sql = "DELETE * FROM ontology WHERE ID = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);

        int i = DbHelper.executeSave(sql, params);
        return i;
    }

    public static int update(Ontology o) {
        String sql = null;
        Object obj = null;

        if (o.getID() > 0) {
            sql = "UPDATE ontology SET level=?, parentID=?, childrenID=?, " +
                    "remarks=? WHERE ID=?";		//sql语句
            obj = o.getID();
        } else {
            sql = "UPDATE ontology SET level=?, parentID=?, childrenID=?, " +
                    "remarks=? WHERE name=?";		//sql语句
            obj = o.getName();
        }

        List<Object> params = new ArrayList<>();
        params.add(o.getLevel());
        params.add(o.getParentID());
        params.add(o.getChildrenIDStr());
        params.add(o.getRemarks());
        params.add(obj);

        int i = DbHelper.executeSave(sql, params);
        return i;
    }
}
