package com.whuamps.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Table(name="questions")
public class Question {
    @Id //标注这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //自增主键
    public int id;

    @Column(name="problemid") //所属题目
    private Integer problemid;

    @Column(name="typeid") //问题类型
    private Integer typeid;

    @Column(name="innerorder") //问题在题目中的次序，为0代表此问题为独立问题，无需题干
    private Integer innerorder;

    @Lob //问题主体
    @Basic(fetch = FetchType.LAZY) //懒加载
    @Column(columnDefinition = "text")
    public String text;

    @Lob //解析
    @Basic(fetch = FetchType.LAZY) //懒加载
    @Column(columnDefinition = "text")
    public String answer;

    @Column(name="options") //选项数量
    private Integer options;

    @Column(name="optiona",length=255)
    private String optiona;

    @Column(name="optionb",length=255)
    private String optionb;

    @Column(name="optionc",length=255)
    private String optionc;

    @Column(name="optiond",length=255)
    private String optiond;

    @Column(name="optione",length=255)
    private String optione;

    @Column(name="optionf",length=255)
    private String optionf;

    @Column(name="optiong",length=255)
    private String optiong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProblemid() {
        return problemid;
    }

    public void setProblemid(Integer problemid) {
        this.problemid = problemid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getInnerorder() {
        return innerorder;
    }

    public void setInnerorder(Integer innerorder) {
        this.innerorder = innerorder;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
    }

    public String getOptiona() {
        return optiona;
    }

    public void setOptiona(String optiona) {
        this.optiona = optiona;
    }

    public String getOptionb() {
        return optionb;
    }

    public void setOptionb(String optionb) {
        this.optionb = optionb;
    }

    public String getOptionc() {
        return optionc;
    }

    public void setOptionc(String optionc) {
        this.optionc = optionc;
    }

    public String getOptiond() {
        return optiond;
    }

    public void setOptiond(String optiond) {
        this.optiond = optiond;
    }

    public String getOptione() {
        return optione;
    }

    public void setOptione(String optione) {
        this.optione = optione;
    }

    public String getOptionf() {
        return optionf;
    }

    public void setOptionf(String optionf) {
        this.optionf = optionf;
    }

    public String getOptiong() {
        return optiong;
    }

    public void setOptiong(String optiong) {
        this.optiong = optiong;
    }
}
