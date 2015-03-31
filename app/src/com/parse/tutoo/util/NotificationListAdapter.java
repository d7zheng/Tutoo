package com.parse.tutoo.util;

import android.content.Context;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.tutoo.model.Notification;

import java.util.List;

/**
 * Created by Owner on 3/1/2015.
 */
public class NotificationListAdapter extends MenuListAdapter<Notification> {

    private String fromUserName;

    public NotificationListAdapter(List<Notification> objs, Context context) {
        super(objs, context);
    }

    @Override
    public String getFirstLine(Notification obj) {
        fromUserName = obj.getFromUserName();
        if (fromUserName == null) {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.getInBackground(obj.getFromUser(), new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                        fromUserName = parseUser.getString("name");
                    }
                }
            });
        }
        if (obj.getType().equals(Notification.notificationType.REPLY.toString())) {
            if (fromUserName != null) {
                return fromUserName + " has responded to your post.";
            }
            else {
                return "You have new replies to your post.";
            }
        }
        else if (obj.getType().equals(Notification.notificationType.BOOKING_REQUEST.toString())) {
            if (fromUserName != null) {
                return fromUserName + " wishes to book time with you.";
            }
            else {
                return "Your have a booking request.";
            }
        }
        else if (obj.getType().equals(Notification.notificationType.BOOKING_RESCHEDULE.toString())) {
            if (fromUserName != null) {
                return fromUserName + " wishes to reschedule time.";
            }
            else {
                return "Your have a booking reschedule.";
            }
        }
        else if (obj.getType().equals(Notification.notificationType.BOOKING_ACCEPT.toString())) {
            if (fromUserName != null) {
                return fromUserName + " has accepted your booking request.";
            }
            else {
                return "Your booking request has been accepted";
            }
        }
        else if (obj.getType().equals(Notification.notificationType.BOOKING_DECLINE.toString())) {
            if (fromUserName != null) {
                return fromUserName + " has declined your booking request.";
            }
            else {
                return "Your booking request has been declined.";
            }
        }
        return "";
    }

    @Override
    public String getSecondLine(Notification obj) {
        return "Click to view";
    }
}
