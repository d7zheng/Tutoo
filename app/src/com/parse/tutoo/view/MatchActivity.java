package com.parse.tutoo.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.util.PostListAdapter;

import java.util.List;
import java.util.Vector;

public class MatchActivity extends ActionBarActivity {
    private ListView listView;
    private Context context;
    private Vector<Post> posts = new Vector<Post>();
    PostListAdapter postListAdapter;
    private LocationManager manager;
    private boolean isNetworkEnabled = false;

    public void initData() {
        posts.clear();
        // get user location
        Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = new Location("myLocation");
            location.setLatitude(43.4710983);
            location.setLongitude(-80.5366939);
        }

        String tableName = "Post";
        ParseQuery query = new ParseQuery(tableName);

        // query for all posts within certain location
        // whereWithinKilometers(String key, ParseGeoPoint point, double maxDistance)
        ParseGeoPoint myLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        query.whereWithinKilometers("location", myLocation, 10);

        try {
            List<Post> postObjects = query.find();
            for (int i = 0; i < postObjects.size(); i++) {
                posts.add(postObjects.get(i));
            }
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        setContentView(R.layout.activity_match);
        context = getApplicationContext();
        manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE );
        listView = (ListView) findViewById(R.id.match_list);

        postListAdapter = new PostListAdapter(posts, this);
        listView.setAdapter(postListAdapter);

        isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isNetworkEnabled) {
            // show alert
            showSettingsAlert();
        }
        else if (isNetworkEnabled) {
            initData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkEnabled) {
            initData();
        }
    }

    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Location settings");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}