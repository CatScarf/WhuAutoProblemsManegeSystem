package com.whuamps.entity;

import org.w3c.dom.Text;

public class TextAndInfo {
    private String text;
    private boolean isDeleted; //isDeleted 和 isHead 是互斥的
    private boolean isHead;

    public TextAndInfo(String text, boolean isDeleted, boolean isHead){
        this.text = text;
        this.isDeleted = isDeleted;
        if(isDeleted == true){
            this.isHead = false;
        }else{
            this.isHead = isHead;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        if(deleted == true){
            isHead = false;
        }
        isDeleted = deleted;
    }

    public boolean getIsHead() {
        return isHead;
    }

    public void setIsHead(boolean head) {
        if(head == true){
            isDeleted = false;
        }
        isHead = head;
    }
}
