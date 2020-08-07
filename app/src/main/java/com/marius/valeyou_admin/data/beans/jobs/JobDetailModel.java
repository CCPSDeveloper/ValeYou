package com.marius.valeyou_admin.data.beans.jobs;

import java.util.List;

public class JobDetailModel {

    /**
     * id : 196
     * date : 1596306600
     * title : New Game
     * startTime : Aug 02 2020
     * endTime : Aug 31 2020
     * startPrice : 0
     * endPrice : 1000
     * description : game developement
     * location : Sector 40 Market Rd
     * status : 1
     * time : 1596299446
     * jobType : 0
     * type : 1
     * startjobDate : 0
     * endjobDate : 0
     * state : punjab
     * city : Chandigarh
     * zipCode : 160036
     * appartment : homeland
     * street :
     * bid_price : 900
     * fav : 2
     * orderCategories : [{"id":75,"categoryId":24,"category":{"id":24,"name":"Programming & Tech ","image":"program_tech.png"},"subCategory":[{"id":116,"categoryId":24,"subCategory":{"id":9,"name":"Game Development","image":"ClipartKey_1040106.png"}}]}]
     * orderImages : [{"id":78,"orderId":196,"images":"eb3d0a00-d2e5-4be1-bdd2-eb0ee1ca2566.jpg","createdAt":"2020-08-01T13:39:38.000Z","updatedAt":"2020-08-01T13:39:38.000Z"}]
     */

    private int id;
    private String date;
    private String title;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String endPrice;
    private String description;
    private String location;
    private int status;
    private String time;
    private int jobType;
    private int type;
    private String startjobDate;
    private String endjobDate;
    private String state;
    private String city;
    private String zipCode;
    private String appartment;
    private String street;
    private int bid_price;
    private int fav;
    private List<OrderCategoriesBean> orderCategories;
    private List<OrderImagesBean> orderImages;

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

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStartjobDate() {
        return startjobDate;
    }

    public void setStartjobDate(String startjobDate) {
        this.startjobDate = startjobDate;
    }

    public String getEndjobDate() {
        return endjobDate;
    }

    public void setEndjobDate(String endjobDate) {
        this.endjobDate = endjobDate;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAppartment() {
        return appartment;
    }

    public void setAppartment(String appartment) {
        this.appartment = appartment;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBid_price() {
        return bid_price;
    }

    public void setBid_price(int bid_price) {
        this.bid_price = bid_price;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
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

    public static class OrderCategoriesBean {
        /**
         * id : 75
         * categoryId : 24
         * category : {"id":24,"name":"Programming & Tech ","image":"program_tech.png"}
         * subCategory : [{"id":116,"categoryId":24,"subCategory":{"id":9,"name":"Game Development","image":"ClipartKey_1040106.png"}}]
         */

        private int id;
        private int categoryId;
        private CategoryBean category;
        private List<SubCategoryBeanX> subCategory;

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

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public List<SubCategoryBeanX> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategoryBeanX> subCategory) {
            this.subCategory = subCategory;
        }

        public static class CategoryBean {
            /**
             * id : 24
             * name : Programming & Tech
             * image : program_tech.png
             */

            private int id;
            private String name;
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public static class SubCategoryBeanX {
            /**
             * id : 116
             * categoryId : 24
             * subCategory : {"id":9,"name":"Game Development","image":"ClipartKey_1040106.png"}
             */

            private int id;
            private int categoryId;
            private SubCategoryBean subCategory;

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

            public SubCategoryBean getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(SubCategoryBean subCategory) {
                this.subCategory = subCategory;
            }

            public static class SubCategoryBean {
                /**
                 * id : 9
                 * name : Game Development
                 * image : ClipartKey_1040106.png
                 */

                private int id;
                private String name;
                private String image;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }
        }
    }

    public static class OrderImagesBean {
        /**
         * id : 78
         * orderId : 196
         * images : eb3d0a00-d2e5-4be1-bdd2-eb0ee1ca2566.jpg
         * createdAt : 2020-08-01T13:39:38.000Z
         * updatedAt : 2020-08-01T13:39:38.000Z
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
