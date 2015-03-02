package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by xiaohanwu on 2015-03-01.
 */
@ParseClassName("Reply")
public class Reply extends ParseObject {
    private String description;
    private ParseUser replyOwner;
    private int postId;

    public Reply(ParseUser replyOwner, String description, int postId) {
        this.replyOwner = replyOwner;
        this.description = description;
        this.postId = postId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostId(int postId) { this.postId = postId;}

    public String getDescription() {
        return this.description;
    }

    public int getPostId () { return this.postId;}

    public void setAttributes(ParseUser replyOwner, String description, int postId) {
        this.replyOwner = replyOwner;
        this.description = description;
        this.postId = postId;
    }
}