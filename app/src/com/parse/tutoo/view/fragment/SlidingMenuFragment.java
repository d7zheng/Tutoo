package com.parse.tutoo.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.SlidingMenuListAdapter;
import com.parse.tutoo.view.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlidingMenuFragment extends Fragment {

    private List<String> menuTitles;
    private Context context;
    private SlidingMenu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sliding_menu_list, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        context = getActivity().getApplicationContext();
        //rootView.setBackground(context.getDrawable(R.color.pitch_black));
        //action_feed = context.getResources().getResourceName(R.string.action_feed);

        menu = ((BaseActivity) getActivity()).getMenu(); // ARggg
        menuTitles = Arrays.asList(getResources().getStringArray(R.array.sliding_menu_titles));
        //Resource r = getResources().getResourceName(R.array.sliding_menu_titles);

        SlidingMenuListAdapter adapter = new SlidingMenuListAdapter(menuTitles, getActivity());

        final ListView listview = (ListView) rootView.findViewById(R.id.list_menu);
        listview.setAdapter(adapter);

        // ListView Item Click Listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //String actionName = menuTitles.get(position);
                switchView(position);
            }
        });

        return rootView;
    }

    private void switchView(int action) {
        Fragment fragment = null;
        switch (action) {
            case 0: // action_feed
                break;
            case 1: // action_browse
                fragment = new CategoryListFragment();
                break;
            case 2: // action_search
                Dispatcher.openSearch(context, this.getActivity());
                break;
            case 3: // action_match
                break;
            case 4: // action_new_post
                Dispatcher.openNewPost(context, this.getActivity());
                break;
            case 5: // action_profile
                Dispatcher.openProfile(context,this.getActivity());
                break;
            case 6: // action_notification
               fragment = new NotificationFragment();
                break;
            case 7: // action_settings
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_frame, fragment).commit();
            menu.toggle(); // Close menu
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}