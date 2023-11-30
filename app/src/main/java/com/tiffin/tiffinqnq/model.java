package com.tiffin.tiffinqnq;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

public class model {
    RecyclerView ProfRecView;
    String name;
    String address;
    String pincode;
    String sex;
    String imageUrl;
    String profession;
    String userid;
    String phoneNo;
    String token;
    public model() {
    }
    public model(RecyclerView ProfRecView, String name, String address, String pincode, String sex,  String imageUrl,String profession, String userid,String phoneNo,String token) {
        this.ProfRecView=ProfRecView;
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.sex=sex;
        this.imageUrl = imageUrl;
        this.profession=profession;
        this.userid=userid;
        this.phoneNo=phoneNo;
        this.token=token;
    }

    public RecyclerView getProfRecView() {
        return ProfRecView;
    }

    public void setProfRecView(RecyclerView profRecView) {
        ProfRecView = profRecView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
