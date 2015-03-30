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
        BOOKING_REQUEST,
        SELECTED,
        BOOKING_DECLINE,
        BOOKING_ACCEPT,
        BOOKING_RESCHEDULE;
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

    // The user name of the sender
    public String getFromUserName() {
        return getString("fromUserName");
    };

    public void setFromUser(String from) {
        put("fromUser", from);
    }

    public void setFromUser(String userId, String name) {
        put("fromUser", userId);
        put("fromUserName", name);
    }

    // The user that the notification is sent to
    public String getToUser() {
        return getString("toUser");
    }

    // The user name of the receiver
    public String getToUserName() {
        return getString("toUserName");
    }

    public void setToUser(String toUser) {
        put("toUser", toUser);
    }

    public void setToUser(String userId, String name) {
        put("toUser", userId);
        put("toUserName", name);
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
