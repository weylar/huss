package com.huss.android.models;


import com.google.gson.annotations.SerializedName;

public class Image {

    private String status;
    private int statusCode;
    private Data data = new Data();
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
        @SerializedName("imageUrl")
        private String iconUrl;
        private int adId;
        private Boolean isFeatured;
        private String createdAt;
        private String updateAt;

        public int getId() {
            return id;
        }


        public void setId(Integer id) {
            this.id = id;
        }

        public Boolean getFeatured() {
            return isFeatured;
        }

        public void setFeatured(Boolean featured) {
            isFeatured = featured;
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

        public int getAdId() {
            return adId;
        }

        public void setAdId(int adId) {
            this.adId = adId;
        }
    }
}
