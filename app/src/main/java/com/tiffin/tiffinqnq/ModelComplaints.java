package com.tiffin.tiffinqnq;

public class ModelComplaints {
    String dateComplaints,category,description,resolution,dateResolution;

    public ModelComplaints() {
    }
    public ModelComplaints(String dateComplaints, String category, String description, String resolution, String dateResolution){
        this.dateComplaints=dateComplaints;
        this.category=category;
        this.description=description;
        this.resolution=resolution;
        this.dateResolution=dateResolution;
    }

    public String getDateComplaints() {
        return dateComplaints;
    }

    public void setDateComplaints(String dateComplaints) {
        this.dateComplaints = dateComplaints;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDateResolution() {
        return dateResolution;
    }

    public void setDateResolution(String dateResolution) {
        this.dateResolution = dateResolution;
    }
}
