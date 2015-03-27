package com.parse.tutoo.util;

import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Notification;

import java.util.ArrayList;
import java.util.List;

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

}
