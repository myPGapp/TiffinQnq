package com.tiffin.tiffinqnq;

public class ProfileImageModel {
    private String imageUrl,Name,Phone,Designation,Assignment;

    public ProfileImageModel() {

    }
    public ProfileImageModel(String imageurl, String name, String phone, String designation, String assignment) {
        imageUrl = imageurl;
        Name=name;
        Phone=phone;
        Designation=designation;
        Assignment=assignment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String carImageUrl) {
        imageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getAssignment() {
        return Assignment;
    }

    public void setAssignment(String assignment) {
        Assignment = assignment;
    }

}

