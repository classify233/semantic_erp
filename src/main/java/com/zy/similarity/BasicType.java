package com.zy.similarity;

public enum BasicType {
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
