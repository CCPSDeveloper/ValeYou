package com.marius.valeyou_admin.data.beans.profilebean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProfileModel {


    /**
     * id : 127
     * name :
     * firstName : john
     * lastName : Doe
     * email : john@gmail.com
     * forgotPassword : 6b6d47c023ce354ca6fbeb146930a564ad2b4f52a09bd6e67e
     * countryCode : +91
     * state : punjab
     * city : mohali
     * street : Sector 71
     * houseNumber : 314
     * appartment : shanti
     * zipCode : 1600360071
     * phone : 9876987690
     * image : c65064b2-dc2d-4461-af38-e64b0cf5eaf7.jpeg
     * dob : 5/11/1991
     * age : 34
     * isEmailVerify : 0
     * otp : 3883
     * description : Great work
     * categoryId : 0
     * socialId :
     * socialType : 0
     * status : 0
     * price :
     * tip : 0
     * isApprove : 0
     * isRead : 0
     * businessLicence : e6009b2e-d299-41a6-9080-c5d5eda14d07.jpg
     * driverLicence : a0e1f0a7-edb4-442f-a3f8-9bc4a53600a6.png
     * socialSecurityNo : 1234567890
     * insurance : 0cb41556-c20e-4939-899a-5a99748054ea.jfif
     * resume :
     * address : Sector 70, Jan Marg, Mohali, PB, India
     * latitude : 31.3852
     * longitude : 75.3857
     * authKey : f926a1ed698e2ef9ff59eb6fc3c556521a53941c7e86aaafe0
     * online : 0
     * profession :
     * deviceType : 1
     * deviceToken : xyz
     * paypalId : john@gmail.com
     * tagId : 219dda31434a24ef14a305ffa5a7e433a5a781ae1b180fb3b5_127
     * createdAt : 2020-06-29T13:26:37.000Z
     * updatedAt : 2020-08-04T16:25:54.000Z
     * providerPortfolios : [{"id":24,"providerId":127,"title":"new project","description":"service providing app","status":0,"image":"af7955a6-35c7-440d-8e8a-a2262367cb91.jpg","createdAt":"2020-07-31T17:09:56.000Z","updatedAt":"2020-07-31T17:09:56.000Z"}]
     * providerLanguages : [{"id":27,"providerId":127,"name":"French","type":"Conversational","status":0,"createdAt":"2020-07-18T11:38:58.000Z","updatedAt":"2020-07-18T11:38:58.000Z"},{"id":29,"providerId":127,"name":"Herero","type":"Basic","status":0,"createdAt":"2020-07-22T17:18:45.000Z","updatedAt":"2020-07-22T17:18:45.000Z"}]
     * certificates : [{"id":16,"providerId":127,"image":"0fa62b09-6e94-40ac-88eb-5f88d94d0953.jpg","title":"tedt","description":"testing","status":0,"createdAt":"2020-07-31T17:16:11.000Z","updatedAt":"2020-07-31T17:16:11.000Z"}]
     * business_hours : [{"id":31,"providerId":127,"day":"Monday","fromTime":"22:15","toTime":"21:22","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"},{"id":32,"providerId":127,"day":"Tuesday","fromTime":"8:00","toTime":"6:00","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"},{"id":33,"providerId":127,"day":"Wednesday","fromTime":"8:00","toTime":"6:00","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"},{"id":34,"providerId":127,"day":"Thrusday","fromTime":"8:00","toTime":"6:00","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"},{"id":35,"providerId":127,"day":"Friday","fromTime":"8:00","toTime":"6:00","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"},{"id":36,"providerId":127,"day":"Saturday","fromTime":"8:00","toTime":"6:00","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"},{"id":37,"providerId":127,"day":"Sunday","fromTime":"8:00","toTime":"6:00","createdAt":"2020-07-20T13:52:56.000Z","updatedAt":"2020-07-20T13:52:56.000Z"}]
     * providerCategories : [{"id":297,"providerId":127,"categoryId":18,"category":{"id":18,"name":"Saloon","image":"saloon.png"},"providerSubCategories":[{"id":188,"providerId":127,"subCategoryId":4,"price":250,"subCategory":{"id":4,"name":"Spa","image":"kindpng_1721146.png"}},{"id":189,"providerId":127,"subCategoryId":5,"price":350,"subCategory":{"id":5,"name":"Beard Cutting","image":"Beard-trim-04.png"}}]}]
     * education : [{"id":2,"providerId":127,"school":"david College","degree":"Master of computer application","studyType":"","startDate":"2014","endDate":"2016","grade":"A","activity":"","description":"","status":0,"createdAt":"2020-08-05T10:51:15.000Z","updatedAt":"2020-08-05T10:51:15.000Z"}]
     * experience : []
     */

    private int id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String forgotPassword;
    private String countryCode;
    private String state;
    private String city;
    private String street;
    private String houseNumber;
    private String appartment;
    private String zipCode;
    private String phone;
    private String image;
    private String dob;
    private int age;
    private int isEmailVerify;
    private String otp;
    private String description;
    private int categoryId;
    private String socialId;
    private int socialType;
    private int status;
    private String price;
    private int tip;
    private int isApprove;
    private int isRead;
    private String businessLicence;
    private String driverLicence;
    private String socialSecurityNo;
    private String insurance;
    private String resume;
    private String address;
    private double latitude;
    private double longitude;
    private String authKey;
    private int online;
    private String profession;
    private int deviceType;
    private String deviceToken;
    private String paypalId;
    private String tagId;
    private String createdAt;
    private String updatedAt;
    private List<ProviderPortfoliosBean> providerPortfolios;
    private List<ProviderLanguagesBean> providerLanguages;
    private List<CertificatesBean> certificates;
    private List<BusinessHoursBean> business_hours;
    private List<ProviderCategoriesBean> providerCategories;
    private List<EducationBean> education;
    private List<ExperienceBean> experience;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(String forgotPassword) {
        this.forgotPassword = forgotPassword;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAppartment() {
        return appartment;
    }

    public void setAppartment(String appartment) {
        this.appartment = appartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIsEmailVerify() {
        return isEmailVerify;
    }

    public void setIsEmailVerify(int isEmailVerify) {
        this.isEmailVerify = isEmailVerify;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public int getSocialType() {
        return socialType;
    }

    public void setSocialType(int socialType) {
        this.socialType = socialType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(int isApprove) {
        this.isApprove = isApprove;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public String getSocialSecurityNo() {
        return socialSecurityNo;
    }

    public void setSocialSecurityNo(String socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(String paypalId) {
        this.paypalId = paypalId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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

    public List<ProviderPortfoliosBean> getProviderPortfolios() {
        return providerPortfolios;
    }

    public void setProviderPortfolios(List<ProviderPortfoliosBean> providerPortfolios) {
        this.providerPortfolios = providerPortfolios;
    }

    public List<ProviderLanguagesBean> getProviderLanguages() {
        return providerLanguages;
    }

    public void setProviderLanguages(List<ProviderLanguagesBean> providerLanguages) {
        this.providerLanguages = providerLanguages;
    }

    public List<CertificatesBean> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificatesBean> certificates) {
        this.certificates = certificates;
    }

    public List<BusinessHoursBean> getBusiness_hours() {
        return business_hours;
    }

    public void setBusiness_hours(List<BusinessHoursBean> business_hours) {
        this.business_hours = business_hours;
    }

    public List<ProviderCategoriesBean> getProviderCategories() {
        return providerCategories;
    }

    public void setProviderCategories(List<ProviderCategoriesBean> providerCategories) {
        this.providerCategories = providerCategories;
    }

    public List<EducationBean> getEducation() {
        return education;
    }

    public void setEducation(List<EducationBean> education) {
        this.education = education;
    }

    public List<ExperienceBean> getExperience() {
        return experience;
    }

    public void setExperience(List<ExperienceBean> experience) {
        this.experience = experience;
    }

    public static class ProviderPortfoliosBean implements Parcelable {
        /**
         * id : 24
         * providerId : 127
         * title : new project
         * description : service providing app
         * status : 0
         * image : af7955a6-35c7-440d-8e8a-a2262367cb91.jpg
         * createdAt : 2020-07-31T17:09:56.000Z
         * updatedAt : 2020-07-31T17:09:56.000Z
         */

        private int id;
        private int providerId;
        private String title;
        private String description;
        private int status;
        private String image;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
            dest.writeInt(this.providerId);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeInt(this.status);
            dest.writeString(this.image);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public ProviderPortfoliosBean() {
        }

        protected ProviderPortfoliosBean(Parcel in) {
            this.id = in.readInt();
            this.providerId = in.readInt();
            this.title = in.readString();
            this.description = in.readString();
            this.status = in.readInt();
            this.image = in.readString();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Parcelable.Creator<ProviderPortfoliosBean> CREATOR = new Parcelable.Creator<ProviderPortfoliosBean>() {
            @Override
            public ProviderPortfoliosBean createFromParcel(Parcel source) {
                return new ProviderPortfoliosBean(source);
            }

            @Override
            public ProviderPortfoliosBean[] newArray(int size) {
                return new ProviderPortfoliosBean[size];
            }
        };
    }

    public static class ProviderLanguagesBean implements Parcelable {
        /**
         * id : 27
         * providerId : 127
         * name : French
         * type : Conversational
         * status : 0
         * createdAt : 2020-07-18T11:38:58.000Z
         * updatedAt : 2020-07-18T11:38:58.000Z
         */

        private int id;
        private int providerId;
        private String name;
        private String type;
        private int status;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
            dest.writeInt(this.providerId);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeInt(this.status);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public ProviderLanguagesBean() {
        }

        protected ProviderLanguagesBean(Parcel in) {
            this.id = in.readInt();
            this.providerId = in.readInt();
            this.name = in.readString();
            this.type = in.readString();
            this.status = in.readInt();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Parcelable.Creator<ProviderLanguagesBean> CREATOR = new Parcelable.Creator<ProviderLanguagesBean>() {
            @Override
            public ProviderLanguagesBean createFromParcel(Parcel source) {
                return new ProviderLanguagesBean(source);
            }

            @Override
            public ProviderLanguagesBean[] newArray(int size) {
                return new ProviderLanguagesBean[size];
            }
        };
    }

    public static class CertificatesBean implements Parcelable {
        /**
         * id : 16
         * providerId : 127
         * image : 0fa62b09-6e94-40ac-88eb-5f88d94d0953.jpg
         * title : tedt
         * description : testing
         * status : 0
         * createdAt : 2020-07-31T17:16:11.000Z
         * updatedAt : 2020-07-31T17:16:11.000Z
         */

        private int id;
        private int providerId;
        private String image;
        private String title;
        private String description;
        private int status;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
            dest.writeInt(this.providerId);
            dest.writeString(this.image);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeInt(this.status);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public CertificatesBean() {
        }

        protected CertificatesBean(Parcel in) {
            this.id = in.readInt();
            this.providerId = in.readInt();
            this.image = in.readString();
            this.title = in.readString();
            this.description = in.readString();
            this.status = in.readInt();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Parcelable.Creator<CertificatesBean> CREATOR = new Parcelable.Creator<CertificatesBean>() {
            @Override
            public CertificatesBean createFromParcel(Parcel source) {
                return new CertificatesBean(source);
            }

            @Override
            public CertificatesBean[] newArray(int size) {
                return new CertificatesBean[size];
            }
        };
    }

    public static class BusinessHoursBean {
        /**
         * id : 31
         * providerId : 127
         * day : Monday
         * fromTime : 22:15
         * toTime : 21:22
         * createdAt : 2020-07-20T13:52:56.000Z
         * updatedAt : 2020-07-20T13:52:56.000Z
         */

        private int id;
        private int providerId;
        private String day;
        private String fromTime;
        private String toTime;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
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

    public static class ProviderCategoriesBean {
        /**
         * id : 297
         * providerId : 127
         * categoryId : 18
         * category : {"id":18,"name":"Saloon","image":"saloon.png"}
         * providerSubCategories : [{"id":188,"providerId":127,"subCategoryId":4,"price":250,"subCategory":{"id":4,"name":"Spa","image":"kindpng_1721146.png"}},{"id":189,"providerId":127,"subCategoryId":5,"price":350,"subCategory":{"id":5,"name":"Beard Cutting","image":"Beard-trim-04.png"}}]
         */

        private int id;
        private int providerId;
        private int categoryId;
        private CategoryBean category;
        private List<ProviderSubCategoriesBean> providerSubCategories;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
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

        public List<ProviderSubCategoriesBean> getProviderSubCategories() {
            return providerSubCategories;
        }

        public void setProviderSubCategories(List<ProviderSubCategoriesBean> providerSubCategories) {
            this.providerSubCategories = providerSubCategories;
        }

        public static class CategoryBean {
            /**
             * id : 18
             * name : Saloon
             * image : saloon.png
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

        public static class ProviderSubCategoriesBean {
            /**
             * id : 188
             * providerId : 127
             * subCategoryId : 4
             * price : 250
             * subCategory : {"id":4,"name":"Spa","image":"kindpng_1721146.png"}
             */

            private int id;
            private int providerId;
            private int subCategoryId;
            private int price;
            private SubCategoryBean subCategory;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProviderId() {
                return providerId;
            }

            public void setProviderId(int providerId) {
                this.providerId = providerId;
            }

            public int getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(int subCategoryId) {
                this.subCategoryId = subCategoryId;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public SubCategoryBean getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(SubCategoryBean subCategory) {
                this.subCategory = subCategory;
            }

            public static class SubCategoryBean {
                /**
                 * id : 4
                 * name : Spa
                 * image : kindpng_1721146.png
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

    public static class EducationBean implements Parcelable {
        /**
         * id : 2
         * providerId : 127
         * school : david College
         * degree : Master of computer application
         * studyType :
         * startDate : 2014
         * endDate : 2016
         * grade : A
         * activity :
         * description :
         * status : 0
         * createdAt : 2020-08-05T10:51:15.000Z
         * updatedAt : 2020-08-05T10:51:15.000Z
         */

        private int id;
        private int providerId;
        private String school;
        private String degree;
        private String studyType;
        private String startDate;
        private String endDate;
        private String grade;
        private String activity;
        private String description;
        private int status;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getStudyType() {
            return studyType;
        }

        public void setStudyType(String studyType) {
            this.studyType = studyType;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
            dest.writeInt(this.providerId);
            dest.writeString(this.school);
            dest.writeString(this.degree);
            dest.writeString(this.studyType);
            dest.writeString(this.startDate);
            dest.writeString(this.endDate);
            dest.writeString(this.grade);
            dest.writeString(this.activity);
            dest.writeString(this.description);
            dest.writeInt(this.status);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public EducationBean() {
        }

        protected EducationBean(Parcel in) {
            this.id = in.readInt();
            this.providerId = in.readInt();
            this.school = in.readString();
            this.degree = in.readString();
            this.studyType = in.readString();
            this.startDate = in.readString();
            this.endDate = in.readString();
            this.grade = in.readString();
            this.activity = in.readString();
            this.description = in.readString();
            this.status = in.readInt();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Parcelable.Creator<EducationBean> CREATOR = new Parcelable.Creator<EducationBean>() {
            @Override
            public EducationBean createFromParcel(Parcel source) {
                return new EducationBean(source);
            }

            @Override
            public EducationBean[] newArray(int size) {
                return new EducationBean[size];
            }
        };
    }

    public static class ExperienceBean implements Parcelable {

        /**
         * id : 2
         * providerId : 127
         * title : IOS Developer
         * experienceType : Part Time
         * company : The Brihaspati Infotech Pvt Ltd
         * location : Mohali
         * currentlyWorking : 0
         * startDate : Sep, 2016
         * endDate : Feb, 2019
         * description : Testing
         * status : 0
         * createdAt : 2020-08-05T14:16:34.000Z
         * updatedAt : 2020-08-05T14:16:34.000Z
         */

        private int id;
        private int providerId;
        private String title;
        private String experienceType;
        private String company;
        private String location;
        private int currentlyWorking;
        private String startDate;
        private String endDate;
        private String description;
        private int status;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getExperienceType() {
            return experienceType;
        }

        public void setExperienceType(String experienceType) {
            this.experienceType = experienceType;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getCurrentlyWorking() {
            return currentlyWorking;
        }

        public void setCurrentlyWorking(int currentlyWorking) {
            this.currentlyWorking = currentlyWorking;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
            dest.writeInt(this.providerId);
            dest.writeString(this.title);
            dest.writeString(this.experienceType);
            dest.writeString(this.company);
            dest.writeString(this.location);
            dest.writeInt(this.currentlyWorking);
            dest.writeString(this.startDate);
            dest.writeString(this.endDate);
            dest.writeString(this.description);
            dest.writeInt(this.status);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public ExperienceBean() {
        }

        protected ExperienceBean(Parcel in) {
            this.id = in.readInt();
            this.providerId = in.readInt();
            this.title = in.readString();
            this.experienceType = in.readString();
            this.company = in.readString();
            this.location = in.readString();
            this.currentlyWorking = in.readInt();
            this.startDate = in.readString();
            this.endDate = in.readString();
            this.description = in.readString();
            this.status = in.readInt();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Parcelable.Creator<ExperienceBean> CREATOR = new Parcelable.Creator<ExperienceBean>() {
            @Override
            public ExperienceBean createFromParcel(Parcel source) {
                return new ExperienceBean(source);
            }

            @Override
            public ExperienceBean[] newArray(int size) {
                return new ExperienceBean[size];
            }
        };
    }
}
