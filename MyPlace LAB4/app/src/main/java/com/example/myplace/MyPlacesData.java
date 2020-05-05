package com.example.myplace;

import java.util.ArrayList;

public class MyPlacesData {
    ArrayList<MyPlace> myPlaces;
    public MyPlacesData(){
        this.myPlaces = new ArrayList<MyPlace>();
        MyPlace mesto = new MyPlace("Nis","Moj Grad");
        mesto.setLatitude("43.3209");
        mesto.setLongitude("21.8958");
        this.myPlaces.add(mesto);
    }
    private  static class SingletonHolder{
        private static final MyPlacesData instance = new MyPlacesData();
    }

    public static MyPlacesData getInstance(){
        return  SingletonHolder.instance;
    }

    public ArrayList<MyPlace> getMyPlaces(){
        return  myPlaces;
    }

    public void addNewPlace(MyPlace newPlace){
        this.myPlaces.add(newPlace);
    }

    public void deletePlace(int index){
        this.myPlaces.remove(index);
    }

    public MyPlace getPlace(int index){
        return this.myPlaces.get(index);
    }
}
