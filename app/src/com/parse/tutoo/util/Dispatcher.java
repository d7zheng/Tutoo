package com.parse.tutoo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.parse.tutoo.view.fragment.CategoryListFragment;
import com.parse.tutoo.view.CreatePostActivity;
import com.parse.tutoo.view.MatchActivity;
import com.parse.tutoo.view.ProfileActivity;
import com.parse.tutoo.view.SearchActivity;
import com.parse.tutoo.view.fragment.NotificationFragment;

/**
 * Created by Owner on 3/1/2015.
 */
public class Dispatcher {

    public static void openSearch(Context context, Activity activity) {
        Intent intent = new Intent(context,SearchActivity.class);
        activity.startActivity(intent);
    }

    public static void openProfile(Context context, Activity activity) {
        Intent intent = new Intent(context,ProfileActivity.class);
        activity.startActivity(intent);
    }


    public static void openNewPost(Context context, Activity activity) {
        Intent act = new Intent(context,CreatePostActivity.class);
        activity.startActivity(act);
    }

    public static void openMatch(Context context, Activity activity) {
        Intent intent = new Intent(context, MatchActivity.class);
        activity.startActivity(intent);
    }

}
