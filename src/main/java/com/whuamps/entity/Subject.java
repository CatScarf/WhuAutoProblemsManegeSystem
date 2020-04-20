package com.whuamps.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="subjects")
public class Subject{
    @Id //标注这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //自增主键
    public int id;

    @Column(name="subject",length=255) //科目名
    private String subject;

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
}