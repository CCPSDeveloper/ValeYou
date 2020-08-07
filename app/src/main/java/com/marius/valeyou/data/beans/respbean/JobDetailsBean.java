package com.marius.valeyou.data.beans.respbean;

import java.util.List;

public class JobDetailsBean {

    /**
     * id : 157
     * date : 1595062381
     * providerId : 0
     * userId : 70
     * title : Business Development
     * startTime :
     * endTime :
     * startPrice : 30
     * endPrice : 10
     * state : Punjab
     * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
     * location : mhali tower phase 8b
     * status : 0
     * time : 1595062381
     * type : 3
     * orderCategories : [{"id":36,"categoryId":0,"category":null,"subCategory":[{"id":70,"categoryId":0,"subCategory":null},{"id":71,"categoryId":0,"subCategory":{"id":2,"name":"Bathroom Cleaning","image":"bathroom.jpg"}}]}]
     * orderImages : [{"id":53,"orderId":157,"images":"84c208c2-2724-4011-a0c6-cb393643aac2.jpeg","createdAt":"2020-07-16T09:12:19.000Z","updatedAt":"2020-07-16T09:12:19.000Z"}]
     * bids : []
     */

    private int id;
    private String date;
    private int providerId;
    private int userId;
    private String title;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String endPrice;
    private String state;
    private String description;
    private String location;
    private int status;
    private String time;
    private int type;
    private int jobType;
    private List<OrderCategoriesBean> orderCategories;
    private List<OrderImagesBean> orderImages;
    private List<BidsBean> bids;

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<OrderCategoriesBean> getOrderCategories() {
        return orderCategories;
    }

    public void setOrderCategories(List<OrderCategoriesBean> orderCategories) {
        this.orderCategories = orderCategories;
    }

    public List<OrderImagesBean> getOrderImages() {
        return orderImages;
    }

    public void setOrderImages(List<OrderImagesBean> orderImages) {
        this.orderImages = orderImages;
    }

    public List<BidsBean> getBids() {
        return bids;
    }

    public void setBids(List<BidsBean> bids) {
        this.bids = bids;
    }

    public static class OrderCategoriesBean {
        /**
         * id : 36
         * categoryId : 0
         * category : null
         * subCategory : [{"id":70,"categoryId":0,"subCategory":null},{"id":71,"categoryId":0,"subCategory":{"id":2,"name":"Bathroom Cleaning","image":"bathroom.jpg"}}]
         */

        private int id;
        private int categoryId;
        private Object category;
        private List<SubCategoryBean> subCategory;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public Object getCategory() {
            return category;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public List<SubCategoryBean> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategoryBean> subCategory) {
            this.subCategory = subCategory;
        }

        public static class SubCategoryBean {
            /**
             * id : 70
             * categoryId : 0
             * subCategory : null
             */

            private int id;
            private int categoryId;
            private Object subCategory;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public Object getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(Object subCategory) {
                this.subCategory = subCategory;
            }
        }
    }

    public static class OrderImagesBean {
        /**
         * id : 53
         * orderId : 157
         * images : 84c208c2-2724-4011-a0c6-cb393643aac2.jpeg
         * createdAt : 2020-07-16T09:12:19.000Z
         * updatedAt : 2020-07-16T09:12:19.000Z
         */

        private int id;
        private int orderId;
        private String images;
        private String createdAt;
        private String updatedAt;

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
