package com.parse.tutoo.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Notification;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.NotificationListAdapter;
import com.parse.tutoo.util.TestData;
import com.parse.tutoo.view.ViewPostActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    //Vector<Notification> notifications = new Vector<Notification>();
    protected Context context;
    protected ArrayList<Notification> list = new ArrayList<Notification>();
    protected List<Notification> notifications = null;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        //setContentView(R.layout.main_list_view);
        context = getActivity().getApplicationContext();

        //adapter = new MenuListAdapter(notifications, getActivity());

        // Mock up Data
        /*TestData testData = new TestData();
        notifications = testData.getNotificationData();
        ParseUser user = ParseUser.getCurrentUser();
        for (int i = 0; i < notifications.size(); ++i) {
            Notification note = notifications.get(i);
            if (user.getObjectId().equals(note.getToUser())) {
                list.add(notifications.get(i));
            }
        }*/

        ParseQuery query = new ParseQuery("Notification");
        query.whereEqualTo("toUser", ParseUser.getCurrentUser().getObjectId());
        query.orderByAscending("createdAt");
        if (list.size() == 0) {
            try {
                notifications = query.find();
                for (int i = 0; i < notifications.size(); i++) {
                    System.out.println(i);
                    Notification note = (Notification) notifications.get(i);
                    list.add(note);
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }

        // Create List View
        NotificationListAdapter adapter = new NotificationListAdapter(list,getActivity());
        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(context, ViewPostActivity.class);;
                intent.putExtra("post_id", notifications.get(position).getPostId());
                startActivity(intent);
            }
        });

        return rootView;
    }
}
