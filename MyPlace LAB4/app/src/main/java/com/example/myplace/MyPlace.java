package com.example.myplace;

public class MyPlace {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String name;
    String description;
    String longitude;
    String latitude;
    int ID;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public MyPlace(String name,String description){
        this.name = name;
        this.description = description;
    }
    public MyPlace(String name){
        this.name = name;
        this.description = "";
    }

    @Override
    public String toString(){
        return this.name;
    }

}
