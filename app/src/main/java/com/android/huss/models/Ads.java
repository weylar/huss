package com.android.huss.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Ads {


    private int id;
    private String title;
    private String favorite;
    @SerializedName("url")
    private String featureImgUrl;
    private ArrayList<String> allImgUrl;
    private String userId;
    private String categoryId;
    private String subCategoryId;
    private String description;
    private String type;
    private String status;
    private String price;
    private String isNegotiable;
    private String createdAt;
    private String updateAt;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String title) {
        this.favorite = favorite;
    }

    public String getFeatureImgUrl() {
        return featureImgUrl;
    }

    public void setFeatureImgurl(String featureImgUrl) {
        this.featureImgUrl = featureImgUrl;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public String getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        this.isNegotiable = isNegotiable;
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

    public ArrayList<String> getAllImgUrl() {
        return allImgUrl;
    }

    public void setAllImgUrl(ArrayList<String> allImgUrl) {
        this.allImgUrl = allImgUrl;
    }
}
