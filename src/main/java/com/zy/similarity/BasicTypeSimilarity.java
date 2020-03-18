package com.zy.similarity;

public class BasicTypeSimilarity {
    enum BasicType {
        BOOLEAN("boolean"), FLOAT("float"),
        INT("int"), STRING("string"), DATE("date");

        private String val;

        BasicType(String v) {
            this.val = v;
        }

        public String getVal() {
            return val;
        }

        public static BasicType getInstance(String v) {

            // 获得BasicType类型的对象
            BasicType ret;
            switch (v) {
                case "boolean":
                    ret = BOOLEAN;
                    break;
                case "float":
                    ret = FLOAT;
                    break;
                case "int":
                    ret = INT;
                    break;
                case "string":
                    ret = STRING;
                    break;
                case "date":
                    ret = DATE;
                    break;
                default:
                    ret = null;
            }
            return ret;
        }
    }

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
