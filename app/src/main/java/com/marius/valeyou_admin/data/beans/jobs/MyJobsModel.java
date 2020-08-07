package com.marius.valeyou_admin.data.beans.jobs;

import java.util.List;

public class MyJobsModel {

    /**
     * id : 158
     * userId : 70
     * providerId : 156
     * title : Business Development
     * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
     * location : Mohali
     * date : 1594915709
     * status : 1
     * bid_price : 300
     * orderImages : [{"id":54,"orderId":158,"images":"05831980-11f3-41c5-8214-2fd6830fa404.jpeg"}]
     */

    private int id;
    private int userId;
    private int providerId;
    private String title;
    private String description;
    private String location;
    private String date;
    private int status;
    private int bid_price;
    private List<OrderImagesBean> orderImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBid_price() {
        return bid_price;
    }

    public void setBid_price(int bid_price) {
        this.bid_price = bid_price;
    }

    public List<OrderImagesBean> getOrderImages() {
        return orderImages;
    }

    public void setOrderImages(List<OrderImagesBean> orderImages) {
        this.orderImages = orderImages;
    }

    public static class OrderImagesBean {
        /**
         * id : 54
         * orderId : 158
         * images : 05831980-11f3-41c5-8214-2fd6830fa404.jpeg
         */

        private int id;
        private int orderId;
        private String images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }
    }
}
