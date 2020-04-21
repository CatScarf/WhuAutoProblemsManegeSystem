package com.whuamps.weka.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="hclassifys")
public class HClassify {
    @Id //标注这是一个主键
    private Integer id;

    @Column(name="classifyname") //分类名
    private String classifyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}