package com.parse.tutoo.util;

import android.content.Context;

import com.parse.tutoo.model.Notification;
import com.parse.tutoo.model.Post;

import java.util.List;

/**
 * Created by Owner on 3/1/2015.
 */
public class NotificationListAdapter extends MenuListAdapter<Notification> {


    public NotificationListAdapter(List<Notification> objs, Context context) {
        super(objs, context);
    }

    @Override
    public String getFirstLine(Notification obj) {
        if (obj.getType() == Notification.notificationType.REPLY.toString()) {
            System.out.println("You received replies to your post!");
            return "You received replies to your post!";
        }
        else if (obj.getType() == Notification.notificationType.SELECTED.toString()) {
            System.out.println("You have been selected as a tutor!");
            return "You have been selected as a tutor!";
        }
        return "";
    }

    @Override
    public String getSecondLine(Notification obj) {
        return "Click to see the post";
    }
}
