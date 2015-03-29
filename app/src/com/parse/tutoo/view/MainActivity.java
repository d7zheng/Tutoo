package com.parse.tutoo.view;

import android.os.Bundle;

import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.view.fragment.SlidingMenuFragment;

//import android.app.Activity;
//import android.app.ActionBar.Tab;
//import android.app.ActionBar;

public class MainActivity extends BaseActivity {

    //private Dispatcher dispatcher = new Dispatcher();

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the Above View
        setContentView(R.layout.activity_base);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_frame, new SlidingMenuFragment())
                .commit();

        //setSlidingActionBarEnabled(true);
    }*/
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        //ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.title_frag_category)
                .setTabListener(new TabListener(this, "category", CategoryListFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.title_frag_markets)
                .setTabListener(new TabListener(this, "markets", MarketsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.title_frag_notification)
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
            case R.id.action_newpost:
                dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                dispatcher.openSearch(getApplicationContext(),this);
                return true;
            case R.id.action_profile:
                dispatcher.openProfile(getApplicationContext(), this);
                return true;
            case R.id.action_match:
                dispatcher.openMatch(getApplicationContext(), this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}
