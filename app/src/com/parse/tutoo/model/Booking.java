package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Owner on 3/30/2015.
 */
@ParseClassName("Booking")
public class Booking extends ParseObject{

    public enum status {
        REQUEST,
        ACCEPTED,
        RESCHEDULE,
        DECLINED
    }

    public Booking() {
        super();
    }

    public Booking(String from, String to, status status, Date start, Date end, String postId) {
        put("requester", from);
        put("requested", to);
        put("startTime", start);
        put("endTime",end);
        put("postId", postId);
        put("checked", false);
    }

    // The user that the Booking is from
    public String getRequester() {
        return getString("requester");
    }
    public String getRequesterName() {
        return getString("requesterName");
    }

    public void setRequester(String from) {
        put("requester", from);
    }

    public void setRequester(String userId, String name) {
        put("requester", userId);
        put("requesterName", name);
    }

    // The user that the Booking is sent to
    public String getRequested() {
        return getString("requested");
    }
    public String getRequestedName() {
       return getString("requestedName");
    }

    public void setRequested(String requested) {
        put("requested", requested);
    }

    public void setRequested(String userId, String name) {
        put("requested", userId);
        put("requestedName", name);
    }

    public void setDateTime(Date start, Date end) {
        put("startTime", start);
        put("endTime", end);
    }


    public Date[] getDateTime() {
        Date[] datetime = { getDate("startTime"),
                getDate("endTime")};
        return datetime;
    }

    // The status of Booking
    public String getStatus() {
        return getString("status");
    }
    public void setStatus(status status) {
        put("status", status.toString());
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
