package com.android.huss.models;


import java.util.List;

public class AdImage {
    private int productId;
    private int userId;
    private String imageUrl;
    private boolean displayImage;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(boolean displayImage) {
        this.displayImage = displayImage;
    }
}

