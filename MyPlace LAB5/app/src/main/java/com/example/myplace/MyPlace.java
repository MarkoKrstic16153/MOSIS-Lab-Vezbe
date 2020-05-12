package com.example.myplace;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
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

    public String name;
    public String description;
    public String longitude;
    public String latitude;

    @Exclude
    public String key;
    public MyPlace(){ }
    public String getLongitude() {
        return longitude;
    }

    public MyPlace(String name,String description){
        this.name = name;
        this.description = description;
    }
    public MyPlace(String name){
        this.name = name;
        this.description = "";
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

   /* public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }*/

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString(){
        return this.name;
    }

}
