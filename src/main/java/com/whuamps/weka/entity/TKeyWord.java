package com.whuamps.weka.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

//训练集
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="tkeywords")
public class TKeyWord {
    @Id //标注这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //自增主键
    private int id;
    @Column(name="classifyid") //分类ID
    private Integer classifyId;
    @Lob //关键词
    @Basic(fetch = FetchType.LAZY) //关键词
    @Column(name="keywordname") //关键词
    private String keywordName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }
}