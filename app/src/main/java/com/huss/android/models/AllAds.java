package com.huss.android.models; ;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllAds {

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
        private String location;
        private String createdAt;
        private String updatedAt;
        @SerializedName("Images")
        private List<Image> images = new ArrayList<>();
        @SerializedName("isFavorite")
        private boolean favorites;

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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
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

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public boolean getFavorites() {
            return favorites;
        }

        public void setFavorites(Boolean favorites) {
            this.favorites = favorites;
        }

        public class Image {

            private Integer id;
            private Integer productId;
            private Integer userId;
            private String imageUrl;
            private Boolean displayImage;
            private String createdAt;
            private String updatedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public Boolean getDisplayImage() {
                return displayImage;
            }

            public void setDisplayImage(Boolean displayImage) {
                this.displayImage = displayImage;
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

}





