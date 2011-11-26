package com.example;

/**
 * Author: Yuri Denison
 * Date: 24.10.2010 17:33:32
 */

import android.graphics.drawable.Drawable;
import android.util.Log;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;

public class MyItemizedOverlay extends
        com.google.android.maps.ItemizedOverlay<OverlayItem> {
    private ArrayList<OverlayItem> overlays;

    public MyItemizedOverlay(Drawable defaultMarker) {
        super(defaultMarker);
        overlays = new ArrayList<OverlayItem>();
    }

    public void addOverlay(OverlayItem overlay) {
        overlays.add(overlay);
        populate();
    }

    protected OverlayItem createItem(int i) {
        return overlays.get(i);
    }

    @Override
    public int size() {
        return overlays.size();
    }

    protected OverlayItem update(OverlayItem overlay, GeoPoint point) {
        if ((overlay == null) || (point == null)) {
            return null;
        }
        OverlayItem overlay_new = new OverlayItem(point, overlay.getTitle(),
                overlay.getSnippet());
        overlays.remove(overlay);
        addOverlay(overlay_new);
        Log.d(Database.TAG, "itemized overlay: update overlay");
        populate();
        return overlay_new;
    }

}

