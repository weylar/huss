package com.android.huss.models;


import java.util.List;


public class Ad {

    private String status;
    private Integer statusCode;
    private List<Data> data = null;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {


        private Integer id;
        private Integer userId;
        private String categoryName;
        private String subCategoryName;
        private String title;
        private String description;
        private String type;
        private String status;
        private String price;
        private Boolean isNegotiable;
        private Integer count;
        private Object payDate;
        private Object location;
        private String createdAt;
        private String updatedAt;


        public Integer getId() {
            return id;
        }


        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }


        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getDescription() {
            return description;
        }


        public void setDescription(String description) {
            this.description = description;
        }


        public String getType() {
            return type;
        }


        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Boolean getIsNegotiable() {
            return isNegotiable;
        }

        public void setIsNegotiable(Boolean isNegotiable) {
            this.isNegotiable = isNegotiable;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Object getPayDate() {
            return payDate;
        }

        public void setPayDate(Object payDate) {
            this.payDate = payDate;
        }


        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }


        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }


        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }


    }


}