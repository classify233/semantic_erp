package com.zy.similarity;

import com.zy.entity.Ontology;
import com.zy.entity.Service;
import com.zy.wordnet.WordNetSim;

/**
 * 测试服务s1->s2的相似度
 */
public class SimilarityUtil {

    private static double PARAM = 1;   // 计算本体间距离的一个参数，大于0

    private static double TYPE_WEIGHT = 2;   // typeSimilarity所占权重
    private static double ONT_WEIGHT = 2;    // OntologySimilarity所占权重
    private static double WORDNET_WEIGHT = 1;  // WordNetSimilarity所占权重

    private Service s1;
    private Service s2;

    public SimilarityUtil(Service s1, Service s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public double getSimilarity() {
        double T =  getTypeSim() * TYPE_WEIGHT + getOntSim() * ONT_WEIGHT
                + getWordNetSim() * WORDNET_WEIGHT;
        return T / (TYPE_WEIGHT + ONT_WEIGHT + WORDNET_WEIGHT);
    }

    public double getTypeSim() {

        BasicType outType1 = BasicType.getInstance(s1.getOutputType());
        BasicType inputType2 = BasicType.getInstance(s2.getInputType());
        return BasicTypeSimilarity.getSimilarity(outType1, inputType2);
    }

    public double getOntSim() {

        double ret = calOntSim(s1.getOutput(), s2.getInput());
        return ret;
    }

    public double getWordNetSim() {

        double ret = calWordNetSim(s1.getOutput(), s2.getInput());
        return ret;
    }

    /**
     * 计算两个本体的相似度
     * @param o1
     * @param o2
     * @return
     */
    public static double calOntSim(Ontology o1, Ontology o2) {

        int dis = 0;
        int level1 = o1.getLevel();
        int level2 = o2.getLevel();

        // 双指针法
        while (o1 != null && o2 != null && !o1.equals(o2)) {
            if (level1 > level2) {
                o1 = o1.getParent();
                level1--;
                dis++;
            } else if (level1 == level2) {
                level1--;
                level2--;
                o1 = o1.getParent();
                o2 = o2.getParent();
                dis += 2;
            } else {
                o2 = o2.getParent();
                level2--;
                dis++;
            }
        }

        if (o1 == null || o2 == null) {
            return 0;
        } else {
            return PARAM / (PARAM + dis);
        }
    }

    /**
     * 计算两个本体的wordnet相似度
     * @param o1
     * @param o2
     * @return
     */
    public static double calWordNetSim(Ontology o1, Ontology o2) {

        WordNetSim wordNetSim = new WordNetSim(o1.getName(), o2.getName());
        return wordNetSim.getSimilarity();
    }
}
