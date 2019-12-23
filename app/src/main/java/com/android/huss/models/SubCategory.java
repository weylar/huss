package com.android.huss.models;


import com.google.gson.annotations.SerializedName;

public class SubCategory {
    private int id;



    private String catId;
    @SerializedName("title")
    private String name;
    private String createdAt;
    private String updateAt;

    public int getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
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
