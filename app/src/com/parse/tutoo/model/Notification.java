package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Owner on 3/1/2015.
 */
@ParseClassName("Notification")
public class Notification extends ParseObject {

    public static final int recencyDayLimit = 1;

    public enum notificationType {
        REPLY,
        SELECTED
    }

    public Notification() {
        super();
    }

    public Notification(String from, String to, notificationType type, String postId) {
        put("fromUser", from);
        put("toUser", to);
        put("type", type.toString());
        put("postId", postId);
        put("checked", false);
    }

    // The user that the notification is from
    public String getFromUser() {
        return getString("fromUser");
    }

    public void setFromUser(String from) {
        put("fromUser", from);
    }

    // The user that the notification is sent to
    public String getToUser() {
        return getString("toUser");
    }

    public void setToUser(String toUser) {
        put("toUser", toUser);
    }

    // The type of notification
    public String getType() {
        return getString("type");
    }
    public void setType(notificationType type) {
        put("type", type.toString());
    }

    // The post id
    public String getPostId() {
        return getString("postId");
    }
    public void setPostId(String id) {
        put("postId",id);
    }

    // Checked
    public boolean isChecked() { return getBoolean("checked");}
    public void setChecked(boolean checked) { put("checked", checked);}

}
