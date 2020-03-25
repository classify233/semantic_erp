package com.zy.db;

import com.zy.entity.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceDao {

    public static Object insert(Service service) {
        String sql= "INSERT INTO service(input,output,providedBy,serviceName,TextDescription," +
                "serviceCategory,qosTime,qosThroughout,inputType,outputType) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(service.getInput().getID());
        params.add(service.getOutput().getID());
        params.add(service.getProvidedBy());
        params.add(service.getServiceName());
        params.add(service.getTextDescription());
        params.add(service.getServiceCategory());
        params.add(service.getQosTime());
        params.add(service.getQosThroughout());
        params.add(service.getInputType());
        params.add(service.getOutputType());

        Object pk = DbHelper.executeInsert(sql, params);
        return pk;
    }

    public static Service selectById(int id) {
        String sql = "SELECT * FROM service WHERE ID = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);

        ArrayList<HashMap<String, Object>> ret = DbHelper.executeQuery(sql, params);
        if (ret == null || ret.isEmpty())
            return null;
        HashMap<String, Object> map = ret.get(0);
        return Service.parse(map);
    }

    public static List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();

        String sql = "SELECT * FROM service";
        ArrayList<HashMap<String, Object>> ret = DbHelper.executeQuery(sql, null);

        for(HashMap<String, Object> map : ret) {
            serviceList.add(Service.parse(map));
        }

        return serviceList;
    }
}
