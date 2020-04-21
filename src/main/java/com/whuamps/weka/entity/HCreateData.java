package com.whuamps.weka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
//@AllArgsConstructor
// Serializable是一个对象序列化的接口
// 一个类只有实现了Serializable接口
// 它的对象才能被序列化
public class HCreateData implements Serializable {

    private static final long serialVersionUID = 1L;

    //分类名
    private String classifyName;

    //文本值
    private String testValue;

    public HCreateData(String classifyName, String keywordName) {
        this.classifyName = classifyName;
        this.testValue = keywordName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }
}