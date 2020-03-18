package com.zy.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MapToEntity {
    public static Object mapToEntity(HashMap<String, Object> map, Class clazz) {

        try {
            Object o = clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (map.get(field.getName().toLowerCase()) != null) {
                    Object v = map.get(field.getName().toLowerCase());
                    Class<?> type = field.getType();

                    // 判断是否是基本数据类型
                    if (type.isPrimitive() || type.isAssignableFrom(v.getClass())) {
                        field.set(o, v);
                    }
                }
            }

            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
