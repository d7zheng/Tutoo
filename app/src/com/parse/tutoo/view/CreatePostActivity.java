package com.parse.tutoo.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Post;


public class CreatePostActivity extends ActionBarActivity {

    private String title;
    private String description;
    private String feedbackType;
    private Context context;
    private Location location;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE );
        setContentView(R.layout.new_post);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createPost(View button) {
        // Do click handling here
        finish();

        final EditText nameField = (EditText) findViewById(R.id.inputSearchEditText);
        title = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.inputTitle);
        description = emailField.getText().toString();

        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        String category = feedbackSpinner.getSelectedItem().toString();
        Category enumCategory = Category.valueOf(category);
        //ParseObject testobject = new ParseObject("Test"); testobject.put("customerName", "John"); testobject.saveInBackground();

        final Post userPost = new Post();

        userPost.setTitle(title);
        userPost.setDescription(description);
        userPost.setCategory(enumCategory);
        userPost.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                }
                System.out.println("HELLO TEST");
            }
        });
       /* new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();*/

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // get user location

        //CustomLocationManager customLocationManager = new CustomLocationManager(context);
        //if (!customLocationManager.isEnable()) {
        // show alert
        //showSettingsAlert();
        //}



        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {}

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {
                    location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    System.out.println("Location is " + location.getAltitude());
                }

                public void onProviderDisabled(String provider) {

                }

            };

            showSettingsAlert();
        } else {
            // store location
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    public void showSettingsAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
