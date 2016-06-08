package com.immedia.assessment.locationview;

/**
 * Created by Rudolph on 2016/06/08.
 */
public class Location {
    private static double lat;
    private static double lng;

    public void setLocation(double latitude, double longitude){
        this.lat = latitude;
        this.lng = longitude;
    }

    public String getGRSString(){
        return lat + "-" + lng;
    }
}
