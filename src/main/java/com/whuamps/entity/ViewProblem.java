package com.whuamps.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

//视图：view_problem
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="view_problems")
public class ViewProblem {
    @Id //标注这是一个主键

    @Column(name="id") //题目id
    public int id;

    @Column(name="subject") //所属科目
    private String subject;

    @Lob //简介
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "brief")
    public String brief;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
