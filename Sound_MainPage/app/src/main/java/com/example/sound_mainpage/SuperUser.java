package com.example.sound_mainpage;

public class SuperUser {
    //싱글톤 객체로 처음 로그인 할때 설정해 주고 계속 이용해 먹기
    private static SuperUser superUser=new SuperUser();
    private String user_id;
    private String user_setting;

    public String getUser_setting() {
        return user_setting;
    }

    public void setUser_setting(String user_setting) {
        this.user_setting = user_setting;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private SuperUser( ) {}
    public static SuperUser getSuperUser(){
        return superUser;
    }
}
