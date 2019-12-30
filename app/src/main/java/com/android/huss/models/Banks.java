package com.android.huss.models;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Banks {
    public List<Datum> data = null;

    public class Datum {
        private String name;

        private String slug;

        private String code;

        private String longcode;

        private String gateway;

        private Boolean payWithBank;

        private Boolean active;

        private Object isDeleted;

        private String country;

        private String currency;

        private String type;

        private Integer id;

        private String createdAt;

        private String updatedAt;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getSlug() {
            return slug;
        }


        public void setSlug(String slug) {
            this.slug = slug;
        }


        public String getCode() {
            return code;
        }


        public void setCode(String code) {
            this.code = code;
        }


        public String getLongcode() {
            return longcode;
        }


        public void setLongcode(String longcode) {
            this.longcode = longcode;
        }


        public String getGateway() {
            return gateway;
        }


        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public Boolean getPayWithBank() {
            return payWithBank;
        }


        public void setPayWithBank(Boolean payWithBank) {
            this.payWithBank = payWithBank;
        }


        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }


        public Object getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Object isDeleted) {
            this.isDeleted = isDeleted;
        }


        public String getCountry() {
            return country;
        }


        public void setCountry(String country) {
            this.country = country;
        }


        public String getCurrency() {
            return currency;
        }


        public void setCurrency(String currency) {
            this.currency = currency;
        }


        public String getType() {
            return type;
        }


        public void setType(String type) {
            this.type = type;
        }


        public Integer getId() {
            return id;
        }


        public void setId(Integer id) {
            this.id = id;
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

