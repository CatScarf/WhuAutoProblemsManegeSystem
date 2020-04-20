package com.whuamps.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="problems")
public class Problem {
    @Id //标注这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //自增主键
    public int id;

    @Column(name="subjectid") //所属科目
    private Integer subjectid;

    @Lob //题目
    @Basic(fetch = FetchType.LAZY) //题干
    @Column(columnDefinition = "stemtext")
    public String stemtext;

    public Problem(){}

    public Problem(Integer subjectid, String stemtext){
        this.subjectid=subjectid;
        this.stemtext=stemtext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getStemtext() {
        return stemtext;
    }

    public void setStemtext(String stemtext) {
        this.stemtext = stemtext;
    }
}
