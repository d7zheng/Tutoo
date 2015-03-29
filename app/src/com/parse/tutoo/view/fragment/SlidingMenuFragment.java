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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.util.RoundedImageView;
import com.parse.tutoo.util.SlidingMenuListAdapter;
import com.parse.tutoo.view.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class SlidingMenuFragment extends Fragment {

    private List<String> menuTitles;
    private Context context;
    private SlidingMenu menu;
    private ParseUser currentUser;
    private RoundedImageView avatar;
    private TextView userName;
    private TextView desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();

        View rootView = inflater.inflate(R.layout.sliding_menu_list, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //rootView.setBackground(context.getDrawable(R.color.pitch_black));

        currentUser = ParseUser.getCurrentUser();

        menu = ((BaseActivity) getActivity()).getMenu(); // Arggg
        menuTitles = Arrays.asList(getResources().getStringArray(R.array.sliding_menu_titles));

        avatar = (RoundedImageView) rootView.findViewById(R.id.avatar);
        userName = (TextView) rootView.findViewById(R.id.userName);
        desc = (TextView) rootView.findViewById(R.id.desc);

        // Set profile pic
        ParseFile profilePic = currentUser.getParseFile("profile_pic");
        avatar.setPlaceholder(getResources().getDrawable(R.drawable.ic_launcher));
        avatar.setParseFile(profilePic);
        avatar.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (data != null) {
                    System.out.println("ParseImageView" + "Fetched! Data length: " + data.length);
                }
                else {
                    //System.out.println("Exception: " + e.getMessage());
                }
            }
        });

        // Set username
        String parseUserName = currentUser.getString("name");
        userName.setText(parseUserName);

        // Set profile box listener
        RelativeLayout profileBox = (RelativeLayout) rootView.findViewById(R.id.profile_box);
        profileBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Profile clicked");
                Dispatcher.openProfile(context,getActivity());
            }
        });

        // Customize list view adapter
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
                Dispatcher.openMatch(context, this.getActivity());
                break;
            case 4: // action_new_post
                Dispatcher.openNewPost(context, this.getActivity());
                break;
            case 5: // action_notification
               fragment = new NotificationFragment();
                break;
            case 6: // action_settings
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