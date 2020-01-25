package com.huss.android.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {

    private String status;
    private int statusCode;
    private ArrayList<Data> data;
    private String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public class Data {
        private int id;
        private String name;
        @SerializedName("categoryImageUrl")
        private String iconUrl;
        private String createdAt;
        private String updateAt;

        public int getId() {
            return id;
        }


        public void setId(Integer id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public String getIconUrl() {
            return iconUrl;
        }


        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }
    }
}
