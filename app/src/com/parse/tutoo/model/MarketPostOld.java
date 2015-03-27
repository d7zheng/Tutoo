package com.parse.tutoo.model;

import android.location.Location;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by hilary on 25/02/2015.
 */
@ParseClassName("MarketPostOld")
public class MarketPostOld extends ParseObject {

    public MarketPostOld(String title, String description, Market market) {
        put("title", title);
        put("desc", description);
        put("market",market.toString());
    }

    public MarketPostOld(String title, String description, String skills, Market market) {
        put("title", title);
        put("desc", description);
        put("market",market.toString());
        put("skills", skills.toLowerCase());
    }

    public MarketPostOld() { super(); }

    public void setTitleAndDescription(String title, String description) {
        put("title", title);
        put("desc", description);
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public void setUser(String user) {
        put("user", user);
    }

    public void setDescription(String description) {
        put("desc", description);
    }

    public void setMarket(Market market) {
        put("market", market.toString());
    }

    public String getUser() {
        return getString("user");
    }

    public String getTitle() {
        return getString("title");
    }

    public String getDescription() {
        return getString("desc");
    }

    public String getPostId () { return getObjectId();}

    public void setGeoPoints(Location location) {
        ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        /*
            ParseGeoPoint point = new ParseGeoPoint(30.0, -20.0);
            ParseObject object = new ParseObject("PlaceObject");
            object.put("location", point);
            object.save();
         */
        put("location", point);
    }

    public Market getMarket () {
        String a = getString("market");
        return Market.valueOf(a);
    }

    public void setAttributes(String title, String description, Market market) {
        put("title", title);
        put("desc", description);
        put("market",market.toString());
    }
}
