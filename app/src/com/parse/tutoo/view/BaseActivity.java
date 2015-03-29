package com.parse.tutoo.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.actionbar.ActionBarSlideIcon;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.view.fragment.CategoryListFragment;
import com.parse.tutoo.view.fragment.SlidingMenuFragment;

public class BaseActivity extends ActionBarActivity {
    private SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.navigation_menu_title);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        initSlidingMenu();
    }

    public SlidingMenu getMenu() {
        return menu;
    }

    public void initSlidingMenu() {

        // Set the actual content frame
        setContentView(R.layout.activity_base);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_frame, new CategoryListFragment())
                .commit();

        // Configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setBackground(this.getResources().getDrawable(R.color.bright_foreground_material_dark));
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setActionBarSlideIcon(new ActionBarSlideIcon(this,
                R.drawable.ic_drawer,R.string.open_content_desc,R.string.close_content_desc));

        // Set sliding menu frame
        menu.setMenu(R.layout.sliding_menu_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.sliding_menu_frame, new SlidingMenuFragment())
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                menu.toggle();
                return true;
            case R.id.action_newpost:
                Dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                Dispatcher.openSearch(getApplicationContext(),this);
                return true;
            case R.id.action_profile:
                Dispatcher.openProfile(getApplicationContext(), this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_slide, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
    }

}