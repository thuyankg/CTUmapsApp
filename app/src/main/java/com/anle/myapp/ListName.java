package com.anle.myapp;

public class ListName {
    private String Name;
    //image name
    private String image;


    public ListName(String name, String image) {
        this.Name = name;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
