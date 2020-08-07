package com.marius.valeyou.data.beans.respbean;

public class ProviderNearMe {

    /**
     * id : 122
     * firstName : Nisha
     * lastName : Rani
     * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
     * image : user.png
     * address : V.P.O Panjoa Kalan teh amb
     * latitude : 30.7046
     * longitude : 76.7179
     * profession : Saloon
     * online : 1
     * state :
     * distance : 1.14
     * avg_rating : 4.5
     * fav : 0
     * total_jobs : 1
     */

    private int id;
    private String firstName;
    private String lastName;
    private String description;
    private String image;
    private String address;
    private double latitude;
    private double longitude;
    private String profession;
    private int online;
    private String state;
    private double distance;
    private double avg_rating;
    private int fav;
    private int total_jobs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getTotal_jobs() {
        return total_jobs;
    }

    public void setTotal_jobs(int total_jobs) {
        this.total_jobs = total_jobs;
    }
}
