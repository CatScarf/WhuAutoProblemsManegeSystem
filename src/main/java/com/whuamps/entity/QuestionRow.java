package com.whuamps.entity;

import com.whuamps.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionRow {
    private String id;
    private String problemid;
    private String typeid;
    private String text;
    private String answer;
    private String options;
    private String optiona;
    private String optionb;
    private String optionc;
    private String optiond;
    private String optione;
    private String optionf;
    private String optiong;

    public String toString(){
        return "problemid: " + problemid + " text: " + text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
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
