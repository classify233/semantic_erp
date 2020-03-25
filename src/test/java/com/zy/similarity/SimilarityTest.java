package com.zy.similarity;

import com.zy.db.ServiceDao;
import com.zy.entity.Service;
import org.junit.Test;

import java.util.Random;

public class SimilarityTest {

    @Test
    public void getSimilarity() {

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int j = random.nextInt(3);
            System.out.println(j);
        }
    }

    @Test
    public void getTypeSim() {
        Service s1 = ServiceDao.selectById(1);
        Service s2 = ServiceDao.selectById(1);
        SimilarityUtil typeSim = new SimilarityUtil(s1, s2);
        System.out.println(typeSim);
    }

    @Test
    public void getOntSim() {
    }

    @Test
    public void getWordNetSim() {
    }
}