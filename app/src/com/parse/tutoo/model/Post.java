package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by hilary on 25/02/2015.
 */
@ParseClassName("Post")
public class Post extends ParseObject {
    private String title;
    private String description;
    private String postId;
    private Category category;

    public Post(String title, String description, String postId, Category category) {
        this.title = title;
        this.description = description;
        this.postId = postId;
        this.category = category;
    }

    public Post( String postId, String title, String description,String category) {
        this.title = title;
        this.description = description;
        this.postId = postId;
        this.category = Category.valueOf(category);
    }
    public Post() {

    }
    public void setTitleAndDescription(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostId(String postId) { this.postId = postId;}

    public void setCategory(Category category) {this.category = category; }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPostId () { return this.postId;}

    public Category getCategory () {return this.category;}

    public void setAttributes(String title, String description, String postId, Category category) {
        this.title = title;
        this.description = description;
        this.postId = postId;
        this.category = category;
    }
}
