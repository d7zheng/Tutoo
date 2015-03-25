package com.parse.tutoo.model;

import android.location.Location;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by hilary on 25/02/2015.
 */
@ParseClassName("Post")
public class Post extends ParseObject {

    public Post(String title, String description, Category category) {
        put("title", title);
        put("desc", description);
        put("category",category.toString());
    }

    public Post(String title, String description, String skills, Category category) {
        put("title", title);
        put("desc", description);
        put("category",category.toString());
        put("skills", skills.toLowerCase());
    }

    public Post() { super(); }

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

    public void setSkills(String skills) {
        put("skills", skills.toLowerCase());
    }

    public void setCategory(Category category) {
        put("category", category.toString());
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

    public String getSkills() {
        return getString("skills");
    }


    public String getPostId () { return getObjectId();}

    public void setGeoPoints(Location location) {
        if (location == null) {
            put ("location", null);
        } else {
            ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
            put("location", point);
        }
    }

    public Category getCategory () {
        String a = getString("category");
        return Category.valueOf(a);
    }

    public void setAttributes(String title, String description, Category category) {
        put("title", title);
        put("desc", description);
        put("category",category.toString());
    }
}
