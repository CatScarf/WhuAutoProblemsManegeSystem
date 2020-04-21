package com.whuamps.weka;

public class SecRepoEntity {
    private String className;
    private String value;

    public SecRepoEntity() {
        super();
    }

    public SecRepoEntity(String className, String value) {
        super();
        this.className = className;
        this.value = value;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SecRepoEntity [className=" + className + ", value=" + value + "]";
    }
}
