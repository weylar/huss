package com.huss.android.models;

import com.google.gson.annotations.SerializedName;

public class Profile {

    private String status;
    private int statusCode;
    private String message;
    private Data data;


    private Profile value;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Data {
        @SerializedName("userId")
        private String id;
        private String token;
        private String firstName;
        private String lastName;
        private String email;
        private String state;
        private String city;
        private String profileImgUrl;
        private String isAdmin;
        private String lastSeen;
        private String isDeleted;
        private String password;
        private String confirmPassword;
        private String phoneNumber;
        private boolean isOnline;
        private String createdAt;
        private String updatedAt;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public boolean isOnline() {
            return isOnline;
        }

        public void setOnline(boolean online) {
            isOnline = online;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProfileImgUrl() {
            return profileImgUrl;
        }

        public void setProfileImgUrl(String profileImgUrl) {
            this.profileImgUrl = profileImgUrl;
        }

        public String getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(String isAdmin) {
            this.isAdmin = isAdmin;
        }

        public String getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(String lastSeen) {
            this.lastSeen = lastSeen;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

    }


}