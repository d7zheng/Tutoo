package com.parse.tutoo.util;

import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Notification;
import com.parse.tutoo.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Owner on 3/1/2015.
 */
public class TestData {

    // Create an array of category strings based on the enumerated Category
    public static String[] getCategoryData() {
        List<String> lists = new ArrayList<>();
        for (Category element : Category.values()) {
            lists.add(element.toString());
        }
        String[] array = new String[lists.size()];
        lists.toArray(array); // fill the array

        return array;
    }

    // Create a vector of posts
    public static Vector<Post> getPostData() {
        Vector<Post> posts = new Vector<Post>();
        posts.add(new Post("Looking for Math Tutor", "My name is Jessie, I'm looking for a math tutor for Grade 5 Math","Math",Category.Math));
        posts.add(new Post("Seek tutor for 2 hours", "My name is Emily, seeking 2 hours tutor for French", "French", Category.Others));
        posts.add(new Post("Math Tutor!", "My name is Danny, I'm looking for a math tutor for Grade 5", "Javascript", Category.Math));
        posts.add(new Post("Seek Help: CS446", "Looking for tutor for Kitchener-Waterloo area", Category.Others));
        posts.add(new Post("Seek Help: MATH135", "looking for a math tutor for calculus", Category.Math));
        return posts;
    }

    // Create a vector of notifications
    public static ArrayList<Notification> getNotificationData() {
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("P3LYiW1btd","o4KAEDIzlh", Notification.notificationType.REPLY, "QMqp63e4lF")); // test user 2 and 2
        notifications.add(new Notification("8KaExErub4", "o4KAEDIzlh", Notification.notificationType.REPLY, "QMqp63e4lF")); // test user 3 and 1
        notifications.add(new Notification("o4KAEDIzlh", "P3LYiW1btd", Notification.notificationType.SELECTED, "QMqp63e4lF")); // test user 1 and 2
        notifications.add(new Notification("8KaExErub4", "P3LYiW1btd", Notification.notificationType.REPLY, "JdHrvz5li8")); // test user 3 and 2
        notifications.add(new Notification("o4KAEDIzlh", "P3LYiW1btd", Notification.notificationType.REPLY, "JdHrvz5li8")); // test user 1 and 2
        return notifications;
    }

    public static Post[] getSearchData() {
        Vector<Post> postV = getPostData();
        Post[] posts = new Post[postV.size()];
        postV.toArray(posts);
        return posts;
    }

}
