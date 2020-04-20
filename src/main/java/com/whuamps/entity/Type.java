package com.whuamps.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="types")
public class Type {
    @Id //标注这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //自增主键
    public int id;

    @Column(name="type",length=255) //科目名
    private String type; //题目类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}