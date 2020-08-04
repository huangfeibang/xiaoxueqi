package com.example.cinema.po;


public class Comment {

    private long userId;
    private java.sql.Date date;
    private String content;
    private long mark;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
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

}
