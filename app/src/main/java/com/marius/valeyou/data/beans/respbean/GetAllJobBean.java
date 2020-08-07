package com.marius.valeyou.data.beans.respbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GetAllJobBean implements Parcelable {

    /**
     * id : 175
     * userId : 70
     * providerId : 0
     * title : IT
     * description : Lorem ispusm.......
     * location : Ashok Nagar
     * date : 1595442600
     * status : 0
     * type : 3
     * state : Delhi
     * startTime : 23-07-2020
     * endTime : 31-07-2020
     * startPrice : 0
     * endPrice : 3
     * jobType : 1
     * orderImages : [{"id":60,"orderId":175,"images":"57d54db4-026e-40e4-b84f-f3acd7d7ff86.jpg","createdAt":"2020-07-23T18:18:24.000Z","updatedAt":"2020-07-23T18:18:24.000Z"}]
     */

    private int id;
    private int userId;
    private int providerId;
    private String title;
    private String description;
    private String location;
    private String date;
    private int status;
    private int type;
    private String state;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String endPrice;
    private int jobType;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public List<OrderImagesBean> getOrderImages() {
        return orderImages;
    }

    public void setOrderImages(List<OrderImagesBean> orderImages) {
        this.orderImages = orderImages;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public static class OrderImagesBean implements Parcelable {
        /**
         * id : 60
         * orderId : 175
         * images : 57d54db4-026e-40e4-b84f-f3acd7d7ff86.jpg
         * createdAt : 2020-07-23T18:18:24.000Z
         * updatedAt : 2020-07-23T18:18:24.000Z
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.orderId);
            dest.writeString(this.images);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public OrderImagesBean() {
        }

        protected OrderImagesBean(Parcel in) {
            this.id = in.readInt();
            this.orderId = in.readInt();
            this.images = in.readString();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Creator<OrderImagesBean> CREATOR = new Creator<OrderImagesBean>() {
            @Override
            public OrderImagesBean createFromParcel(Parcel source) {
                return new OrderImagesBean(source);
            }

            @Override
            public OrderImagesBean[] newArray(int size) {
                return new OrderImagesBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeInt(this.providerId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.location);
        dest.writeString(this.date);
        dest.writeInt(this.status);
        dest.writeInt(this.type);
        dest.writeInt(this.jobType);
        dest.writeString(this.state);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.startPrice);
        dest.writeString(this.endPrice);
        dest.writeList(this.orderImages);
    }

    public GetAllJobBean() {
    }

    protected GetAllJobBean(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.providerId = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.location = in.readString();
        this.date = in.readString();
        this.status = in.readInt();
        this.type = in.readInt();
        this.jobType = in.readInt();
        this.state = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.startPrice = in.readString();
        this.endPrice = in.readString();
        this.orderImages = new ArrayList<OrderImagesBean>();
        in.readList(this.orderImages, OrderImagesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetAllJobBean> CREATOR = new Parcelable.Creator<GetAllJobBean>() {
        @Override
        public GetAllJobBean createFromParcel(Parcel source) {
            return new GetAllJobBean(source);
        }

        @Override
        public GetAllJobBean[] newArray(int size) {
            return new GetAllJobBean[size];
        }
    };
}
