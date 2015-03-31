package com.parse.tutoo.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.parse.tutoo.R;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.view.fragment.CategoryListFragment;
import com.parse.tutoo.view.fragment.NotificationFragment;
import com.parse.tutoo.view.fragment.SlidingMenuFragment;

import java.util.List;

//import android.app.Activity;
//import android.app.ActionBar.Tab;
//import android.app.ActionBar;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the actual content frame
        setContentView(R.layout.activity_base);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_frame, new CategoryListFragment())
                .commit();

    }
}
