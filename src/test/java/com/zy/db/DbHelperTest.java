package com.zy.db;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class DbHelperTest {

    @Test
    public void testGetConnection() {
        java.sql.Connection con = DbHelper.getConnction();
        System.out.println(con);
    }

    @Test
    public void testSelect() {
        String sqlString = "select * from ontology WHERE ID = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(2);

        ArrayList<HashMap<String, Object>> tempresult = DbHelper.executeQuery(sqlString, params);

        for (HashMap<String, Object> row : tempresult) {
            for (String key : row.keySet()) {
                System.out.println(key + " --> " + row.get(key));
            }
        }
    }

    @Test
    public void testDelete() {
        String sql = "DELETE FROM ontology WHERE ID = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(1);

        int ret = DbHelper.executeSave(sql, params);
        System.out.println(ret);
    }

    @Test
    public void testInsert() {
        String sql= "INSERT INTO ontology(name,level,remarks) VALUES(?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add("people");
        params.add(0);
        params.add("这是所有人的集合");

        int result = DbHelper.executeSave(sql, params);
        System.out.println(result);
    }

    @Test
    public void executeInsert() {
        String sql= "INSERT INTO ontology(name,level,parentID,childrenID,remarks) " +
                "VALUES(?,?,?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add("wuyulun");
        params.add(0);
        params.add(-1);
        params.add("");
        params.add("这是wuyulun");

        Object pk = DbHelper.executeInsert(sql, params);
        System.out.println(pk);
    }
}