package com.example.sound_mainpage;

public class list_item {
    private int seek_bar;
    private String btn_name;

    public list_item(String btn_name,int seek_bar){
        this.btn_name=btn_name;
        this.seek_bar=seek_bar;
    }

    public int getSeek_bar() {
        return seek_bar;
    }

    public void setSeek_bar(int seek_bar) {
        this.seek_bar = seek_bar;
    }

    public String getBtn_name() {
        return btn_name;
    }

    public void setBtn_name(String btn_name) {
        this.btn_name = btn_name;
    }
}
