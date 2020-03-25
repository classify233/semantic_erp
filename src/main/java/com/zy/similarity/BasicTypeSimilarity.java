package com.zy.similarity;

public class BasicTypeSimilarity {

    // BOOLEAN("boolean"), FLOAT("float"),
    // INT("int"), STRING("string"), DATE("date");
    static double[][] matrix = new double[][]{
            {1, 0, 0.1, 0, 0},
            {0, 1, 0.5, 0, 0},
            {0.1, 0.8, 1, 0, 0},
            {0.3, 0.3, 0.3, 1, 0.1},
            {0, 0, 0, 0.1, 1}
    };

    public static double getSimilarity(BasicType t1, BasicType t2) {
        // 要处理为null的情况
        if (t1 == null || t2 == null)
            return 0;
        return matrix[t1.ordinal()][t2.ordinal()];
    }
}
