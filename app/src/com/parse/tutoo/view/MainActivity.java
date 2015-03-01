package com.parse.tutoo.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
//import android.app.Activity;
//import android.app.ActionBar.Tab;
//import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.tutoo.R;
import com.parse.tutoo.util.TabListener;
import com.parse.tutoo.view.fragment.NotificationFragment;
import com.parse.tutoo.view.fragment.PostFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Notice that setContentView() is not used, because we use the root
        // android.R.id.content as the container for each fragment
        setContentView(R.layout.activity_main);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        //ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.title_activity_search)
                .setTabListener(new TabListener(this, "search", PostFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.title_activity_notification)
                .setTabListener(new TabListener(this, "notification", NotificationFragment.class));
        actionBar.addTab(tab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_notification:
                openNotification();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSearch() {
        Context context = getApplicationContext();
        Intent search = new Intent(context,SearchActivity.class);
        startActivity(search);
    }

    public void openNotification() {
        Context context = getApplicationContext();
        Intent notification = new Intent(context,NotificationActivity.class);
        startActivity(notification);
    }
}
