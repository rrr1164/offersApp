package com.yousef_shora.omniaoffers.pojo;

import com.yousef_shora.omniaoffers.modal.Utils;

import java.io.Serializable;
import java.util.UUID;

public class Offer  implements Serializable {
    public String title;
    public String description = "";
    public String picture_url;
    public String category;
    public final String id;

    public Offer(String title, String picture_url, String category) {
        this.title = title;
        if (Utils.is_category(category)) {
            this.category = category;
        }
        this.picture_url = picture_url;
        id = UUID.randomUUID().toString();
    }

    public Offer(String title, String picture_url, String category, String description) {
        this.title = title;
        if (Utils.is_category(category)) {
            this.category = category;
        }
        this.picture_url = picture_url;
        this.description = description;
        id = UUID.randomUUID().toString();
    }

    public Offer(String title, String category) {
        this.title = title;
        if (Utils.is_category(category)) {
            this.category = category;
        }
        id = UUID.randomUUID().toString();
    }

    public Offer() {
        id = UUID.randomUUID().toString();

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (Utils.is_category(category)) {
            this.category = category;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }
}