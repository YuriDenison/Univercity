package com.example;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import com.google.android.maps.*;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Author: Yuri Denison
 * Date: 20.10.2010 1:53:44
 */

public class DenisonGPS extends MapActivity {
    private static final int MENU_ABOUT = 1;

    private TextView currentLocationTV;
    private TextView lastClickLocationTV;
    private TextView compassTV;
    private MapView mapView;
    private MapController mc;

    private MyLocationOverlay myLocationOverlay;
    private MyItemizedOverlay items;

    private Database database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        database = new Database(this);
        currentLocationTV = (TextView) findViewById(R.id.currentLocation);
        lastClickLocationTV = (TextView) findViewById(R.id.lastClick);
        compassTV = (TextView) findViewById(R.id.compass);
        mapView = (MapView) findViewById(R.id.mapView);
        //items = new MyItemizedOverlay(this.getResources().getDrawable(R.drawable.items));

        initMyLocationOverview();
        initViewMembers();

        if (getSystemService(Context.SENSOR_SERVICE) == null) {
            compassTV.setText(Database.NO_COMPASS);
        } else {
            initCompassOverview();
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null ||
                !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            showModalWindow(Database.NO_INTERNET);
        }
        if (!myLocationOverlay.isMyLocationEnabled()) {
            showAlertMessageNoGPS();
        }
        currentLocationTV.setText(Database.NO_LOCATION);

        Log.v(Database.TAG, "On create finished");
    }

    private void initCompassOverview() {
        compassTV.setText("Azimuth: " + myLocationOverlay.getOrientation());
        Log.v(Database.TAG, "Initing compass");
    }

    private void initViewMembers() {
        mapView.setBuiltInZoomControls(true);
        mc = mapView.getController();
        mapView.getOverlays().clear();
        mapView.getOverlays().add(myLocationOverlay);
        database.overlays.add(myLocationOverlay);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        database.currentCenter = mapView.getMapCenter();
        database.currentZoom = mapView.getZoomLevel();
        database.overlays = mapView.getOverlays();

        database.currentCompassTV = compassTV.getText().toString();
        database.currentClickTV = lastClickLocationTV.getText().toString();
        database.currentLocationTV = currentLocationTV.getText().toString();
        mapView.getOverlays().clear();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentLocationTV.setText(database.currentCompassTV);
        compassTV.setText(database.currentCompassTV);
        lastClickLocationTV.setText(database.currentClickTV);

        mapView.getOverlays().addAll(database.overlays);

    }

    /* Handles item selections */

    @Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ABOUT:
                showAbout();
                return true;
        }
        return false;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocationOverlay.disableMyLocation();
        myLocationOverlay.disableCompass();

        database.currentCenter = mapView.getMapCenter();
        database.currentZoom = mapView.getZoomLevel();
        database.overlays = mapView.getOverlays();

        database.currentCompassTV = compassTV.getText().toString();
        database.currentClickTV = lastClickLocationTV.getText().toString();
        database.currentLocationTV = currentLocationTV.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mc.setCenter(database.currentCenter);
        mc.animateTo(database.currentCenter);
        mc.setZoom(database.currentZoom);
        mapView.getOverlays().clear();
        mapView.getOverlays().addAll(database.overlays);
                
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableCompass();
        if (getSystemService(Context.SENSOR_SERVICE) == null) {
            compassTV.setText(Database.NO_COMPASS);
        } else {
            initCompassOverview();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add(0, MENU_ABOUT, 0, "About");
        return true;
    }

    private void showAbout() {
        showModalWindow(Database.ABOUT_AUTHOR);
    }

    private void initMyLocationOverview() {
        myLocationOverlay = new MyLocationOverlay(this, mapView) {
            private GeoPoint locationPoint;
            private GeoPoint tapPoint;
            
            @Override
            public void onSensorChanged(int sensor, float[] values) {
                if(sensor != Sensor.TYPE_ORIENTATION && sensor != Sensor.TYPE_MAGNETIC_FIELD) {
                    return;
                }
                compassTV.setText("Azimuth: " + values[0]);
            }

            @Override
            public void onLocationChanged(Location location) {
                if (location == null) {
                    currentLocationTV.setText(Database.NO_LOCATION);
                    return;
                }
                double locationLat = format(location.getLatitude(), Database.COORDINATE_ACCURACY);
                double locationLng = format(location.getLongitude(), Database.COORDINATE_ACCURACY);

                Log.d(Database.TAG, "Starting updateLocation " + locationLat + ", " + locationLng);
                Log.d(Database.TAG, "Mc " + mc.toString());
                Log.d(Database.TAG, "My Location: " + myLocationOverlay.getMyLocation());
                locationPoint = new GeoPoint(
                        (int) (locationLat * 1E6),
                        (int) (locationLng * 1E6));
                currentLocationTV.setText("Current location: " + locationConvert(locationLat, locationLng));

                mc.animateTo(locationPoint);
                mc.setCenter(locationPoint);
                mc.setZoom(Database.DEFAULT_ZOOM_VALUE);

                database.currentCenter = locationPoint;
                database.currentZoom = Database.DEFAULT_ZOOM_VALUE;
                database.currentLocation = location;
            }

            @Override
            public boolean draw(android.graphics.Canvas canvas, MapView mapView,
                                boolean shadow, long when) {
                super.draw(canvas, mapView, shadow);

                //---translate the GeoPoint to screen pixels---
                if (locationPoint != null) {
                    android.graphics.Point screenPts = new android.graphics.Point();

                    mapView.getProjection().toPixels(locationPoint, screenPts);

                    //---add the marker---
                    Bitmap bmp = Database.locationPin;
                    canvas.drawBitmap(bmp, screenPts.x - bmp.getWidth() / 4, screenPts.y - bmp.getHeight(), null);
                }
                if (tapPoint != null) {
                    android.graphics.Point screenPts = new android.graphics.Point();

                    mapView.getProjection().toPixels(tapPoint, screenPts);

                    //---add the marker---
                    Bitmap bmp = Database.pushPin;
                    canvas.drawBitmap(bmp, screenPts.x - bmp.getWidth() / 2, screenPts.y - bmp.getHeight(), null);
                }
                return true;
            }

            @Override
            public boolean onTouchEvent(android.view.MotionEvent event, MapView mapView) {
                //---when user lifts his finger---
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    tapPoint = mapView.getProjection().fromPixels(
                            (int) event.getX(),
                            (int) event.getY());
                    database.clickPoint = tapPoint;
                    lastClickLocationTV.setText("Last click location: " +
                            locationConvert(format(tapPoint.getLatitudeE6() / 1E6, Database.COORDINATE_ACCURACY),
                                    format(tapPoint.getLongitudeE6() / 1E6, Database.COORDINATE_ACCURACY)));
                }
                return false;
            }
        };
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableCompass();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                myLocationOverlay.onLocationChanged(myLocationOverlay.getLastFix());
            }
        });
    }

    private void showModalWindow(String str) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlertMessageNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Database.GPS_SETTING)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        launchOptions();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void launchOptions() {
        //default is GPS launch
        try {
            android.content.ComponentName toLaunch = new android.content.ComponentName("com.android.settings",
                    "com.android.settings.SecuritySettings");
            String param = Settings.ACTION_LOCATION_SOURCE_SETTINGS;

            final Intent intent = new Intent(param);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(toLaunch);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            showModalWindow(Database.ERROR_GPS_SETTINGS);
        }
    }

    private String locationConvert(double lat, double lng) {
        if (lat < 0) {
            if (lng < 0) {
                return (-1.0 * lat) + " S, " + (-1.0 * lng) + " W";
            } else {
                return (-1.0 * lat) + " S, " + lng + " E";
            }
        } else {
            if (lng < 0) {
                return lat + " N, " + (-1.0 * lng) + " W";
            } else {
                return lat + " N, " + lng + " E";
            }
        }
    }

    private static double format(double num, int col) {
        NumberFormat aFormat = NumberFormat.getNumberInstance(Locale.UK);
        aFormat.setMaximumFractionDigits(col);
        return Double.parseDouble(aFormat.format(num));
    }
}

