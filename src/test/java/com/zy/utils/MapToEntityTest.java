package com.zy.utils;

import com.zy.entity.Person;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class MapToEntityTest {

    @Test
    public void mapToEntity() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        Person p = (Person) MapToEntity.mapToEntity(map, Person.class);
        System.out.println(p);
    }
}