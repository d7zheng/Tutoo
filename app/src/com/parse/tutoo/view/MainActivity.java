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
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.TabListener;
import com.parse.tutoo.view.fragment.CategoryListFragment;
import com.parse.tutoo.view.fragment.NotificationFragment;

public class MainActivity extends ActionBarActivity {

    private Dispatcher dispatcher = new Dispatcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        //ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.title_frag_notification)
                .setTabListener(new TabListener(this, "notification", NotificationFragment.class));
        actionBar.addTab(tab);


        tab = actionBar.newTab()
                .setText(R.string.title_frag_category)
                .setTabListener(new TabListener(this, "category", CategoryListFragment.class));
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
            case R.id.action_newpost:
                dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                dispatcher.openSearch(getApplicationContext(),this);
                return true;
            case R.id.action_profile:
                dispatcher.openProfile(getApplicationContext(), this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
