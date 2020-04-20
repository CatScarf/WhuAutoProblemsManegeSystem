package com.whuamps.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;


//视图：view_question
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Table(name="view_questions")
public class ViewQuestion {
    @Id //标注这是一个主键

    @Column(name="id") //问题id
    public int id;

    @Column(name="innerorder") //问题在所属题目中的次序
    public int innerorder;

    @Column(name="problemid") //所属题目id
    public int problemid;

    @Lob //所属题目简介
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "stembrief")
    public String stembrief;

    @Column(name="subject") //所属科目
    public String subject;

    @Column(name="type") //题目类型
    public String type;

    @Lob //问题简介
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "textbrief")
    public String textbrief;

    @Lob //回答简介
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "answerbrief")
    public String answerbrief;

    @Column(name="options") //选项数量
    public int options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInnerorder() {
        return innerorder;
    }

    public void setInnerorder(int innerorder) {
        this.innerorder = innerorder;
    }

    public int getProblemid() {
        return problemid;
    }

    public void setProblemid(int problemid) {
        this.problemid = problemid;
    }

    public String getStembrief() {
        return stembrief;
    }

    public void setStembrief(String stembrief) {
        this.stembrief = stembrief;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTextbrief() {
        return textbrief;
    }

    public void setTextbrief(String textbrief) {
        this.textbrief = textbrief;
    }

    public String getAnswerbrief() {
        return answerbrief;
    }

    public void setAnswerbrief(String answerbrief) {
        this.answerbrief = answerbrief;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }
}
