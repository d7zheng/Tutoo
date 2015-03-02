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
    private String postId;

    public Reply(ParseUser replyOwner, String description, String postId) {
        this.replyOwner = replyOwner;
        this.description = description;
        this.postId = postId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostId(String postId) { this.postId = postId;}

    public String getDescription() {
        return this.description;
    }

    public String getPostId () { return this.postId;}

    public void setAttributes(ParseUser replyOwner, String description, String postId) {
        this.replyOwner = replyOwner;
        this.description = description;
        this.postId = postId;
    }
}