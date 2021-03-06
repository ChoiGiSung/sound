package com.example.sound_mainpage.Api;

import java.util.List;

public class DataDto {

    private List<UserDto> data;

    public List<UserDto> getData() {
        return data;
    }

    public void setData(List<UserDto> data) {
        this.data = data;
    }


    //이너 클래스로 정의
    public static class UserDto{
        private String user_id;
        private String day_1;
        private String day_2;
        private String day_3;
        private String day_4;
        private String day_5;
        private String day_6;
        private String day_7;
        private String user_seting;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDay_1() {
            return day_1;
        }

        public void setDay_1(String day_1) {
            this.day_1 = day_1;
        }
        public String getDay_2() {
            return day_2;
        }

        public void setDay_2(String day_2) {
            this.day_2 = day_2;
        }

        public String getDay_3() {
            return day_3;
        }

        public void setDay_3(String day_3) {
            this.day_3 = day_3;
        }

        public String getDay_4() {
            return day_4;
        }

        public void setDay_4(String day_4) {
            this.day_4 = day_4;
        }

        public String getDay_5() {
            return day_5;
        }

        public void setDay_5(String day_5) {
            this.day_5 = day_5;
        }

        public String getDay_6() {
            return day_6;
        }

        public void setDay_6(String day_6) {
            this.day_6 = day_6;
        }

        public String getDay_7() {
            return day_7;
        }

        public void setDay_7(String day_7) {
            this.day_7 = day_7;
        }

        public String getUser_seting() {
            return user_seting;
        }

        public void setUser_seting(String user_seting) {
            this.user_seting = user_seting;
        }

    }
}

