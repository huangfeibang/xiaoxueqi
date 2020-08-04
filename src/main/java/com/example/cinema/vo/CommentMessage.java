package com.example.cinema.vo;


import java.sql.Date;

public class CommentMessage {

    private String userName;
    private java.sql.Date date;
    private String content;
    private long mark;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMark() {
        return mark;
    }

    public void setMark(long mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "CommentMessage{" +
                "userName='" + userName + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", mark=" + mark +
                '}';
    }
}
