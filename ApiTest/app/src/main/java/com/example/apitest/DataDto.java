package com.example.apitest;

import java.util.List;

public class DataDto {

    private List<UserDto> data;

    public List<UserDto> getData() {
        return data;
    }

    public void setData(List<UserDto> data) {
        this.data = data;
    }

    static class UserDto{
        private String user_id;
        private String day_1;

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

    }
}

