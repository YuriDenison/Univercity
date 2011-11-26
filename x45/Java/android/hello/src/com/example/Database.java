package com.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Yuri Denison
 * Date: 25.10.2010 0:23:43
 */
public class Database {
    public static final String TAG = "android GPS";
    public static final int COORDINATE_ACCURACY = 5;
    public static final int DEFAULT_ZOOM_VALUE = 14;
    public static final String NO_COMPASS = "Compass is unavailable on your device!";
    public static final String NO_INTERNET = "There is no connection to the internet found. " +
            "May be a problems with downloading maps";
    public static final String NO_LOCATION = "Current location: not found.";
    public static final String ABOUT_AUTHOR = "Application by Denison Yuri";
    public static final String GPS_SETTING = "Your GPS seems to be disabled, do you want to enable it?";
    public static final String ERROR_GPS_SETTINGS = "Error on opening settings window. Please switch GPS on manually!";

    public static Bitmap locationPin;
    public static Bitmap pushPin;


    // map parameters
    public int currentZoom;
    public GeoPoint currentCenter;
    public List<Overlay> overlays;

    public Location currentLocation;
    public GeoPoint clickPoint;

    public String currentLocationTV;
    public String currentCompassTV;
    public String currentClickTV;

    public Database(DenisonGPS app) {
        pushPin = BitmapFactory.decodeResource(app.getResources(), R.drawable.pushpin);
        locationPin = BitmapFactory.decodeResource(app.getResources(), R.drawable.locationpin);
        overlays = new ArrayList<Overlay>();

        currentZoom = DEFAULT_ZOOM_VALUE;
        currentCenter = new GeoPoint(350131, 6648881); // Saint-Petersburg
        currentLocationTV = "Current location: ";
        currentCompassTV = "Azimuth: ";
        currentClickTV = "Last click location: ";
    }
}
