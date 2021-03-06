package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by xiaohanwu on 2015-03-01.
 */
@ParseClassName("Reply")
public class Reply extends ParseObject {

    public Reply(String replyOwnerId, String description, String postId, String userId) {
        put("replyOwnerId", replyOwnerId);
        put("description", description);
        put("postId",postId);
        put("userId",userId);
    }

    public Reply(String replyOwnerId, String description, String postId) {
        put("replyOwnerId", replyOwnerId);
        put("description", description);
        put("postId",postId);
    }


    public Reply() {
        super();
    }

    public void setUserId(String userId) { put("userId", userId);}

    public void setReplyOwnerId(String replyOwnerId) { put("replyOwnerId", replyOwnerId);}
    public void setDescription(String description) {
        put("description", description);
    }

    public void setPostId(String postId) { put("postId",postId);}

    public String getDescription() {
        return getString("description");
    }

    public String getPostId () { return getString("postId");}
    public String getUserId() { return getString("userId");}

    public String getReplyOwnerId() { return getString("replyOwnerId");}

    public void setAttributes(String replyOwnerId, String description, String postId) {
        put("replyOwnerId", replyOwnerId);
        put("description", description);
        put("postId",postId);
    }
}