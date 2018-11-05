package com.enggmartservices.enggmart.models;


public class ModelUserClass {

    private String phone = "";
    private String email = "";
    private String name = "";
    private String image = "";
    private String status = "";
    private String thumbImage = "";

    public ModelUserClass() {
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelUserClass(String phone, String email, String name, String image, String status) {
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.image = image;
        this.status = status;
    }

    public String getChatWithuser() {
        return chatWithuser;
    }

    public void setChatWithuser(String chatWithuser) {
        this.chatWithuser = chatWithuser;
    }

    private String chatWithuser = "";

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }
}
