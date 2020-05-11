package com.whuamps.entity;

public class QuestionAndInfo {
    private String question;
    private int type;
    private int subject;

    public QuestionAndInfo(String question, int type, int subject){
        this.question = question;
        this.type = type;
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }
}
