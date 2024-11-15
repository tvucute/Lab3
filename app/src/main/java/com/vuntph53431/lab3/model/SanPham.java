package com.vuntph53431.lab3.model;

public class SanPham {
    private String title,content,date;
    private int status,id;
    private String style;

    public SanPham(String content, String title, String date,String style, int status, int id) {
        this.content = content;
        this.title = title;
        this.date = date;
        this.status = status;
        this.style = style;
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public SanPham(String title, String content, String date, int status) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.status = status;
    }

    public SanPham() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
