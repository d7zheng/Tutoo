package com.parse.tutoo.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.*;
import android.widget.*;

import com.parse.*;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Booking;
import com.parse.tutoo.model.Notification;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.view.fragment.DatePickerFragment;
import com.parse.tutoo.view.fragment.TimePickerFragment;
import com.parse.ui.ParseLoginBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.media.*;
import android.provider.MediaStore.Images.Media;
import java.io.*;

public class ProfileActivity extends ActionBarActivity {
    private ParseUser user;
    private Context context;

    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button logout = (Button) findViewById(R.id.logout_button);
        Button editProfile = (Button) findViewById(R.id.edit_profile);
        Button bookRequest = (Button) findViewById(R.id.booking_button);
        Button viewCalendar = (Button) findViewById(R.id.calendar_button);

        Bundle b = getIntent().getExtras();
        if (b == null) {
            displayProfile(ParseUser.getCurrentUser());

            logout.setVisibility(View.VISIBLE);
            editProfile.setVisibility(View.VISIBLE);
            bookRequest.setVisibility(View.GONE);
            viewCalendar.setVisibility(View.VISIBLE);
        } else {
            String userID = b.getString("id");
            if (userID.equals(ParseUser.getCurrentUser().getObjectId())) {
                logout.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.VISIBLE);
                bookRequest.setVisibility(View.GONE);
                viewCalendar.setVisibility(View.VISIBLE);
            }
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", userID);
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        user = objects.get(0);
                        displayProfile(user);
                    } else {
                        // Something went wrong.
                        System.out.println(e.getMessage());
                    }
                }
            });
        }

    }
    public void displayProfile(ParseObject user) {

        if (user == null) {
            finish();
            return;
        }

        ParseFile parseProfilePic = user.getParseFile("profile_pic");

        ParseImageView profilePic = (ParseImageView) findViewById(R.id.imageView);
        profilePic.setPlaceholder(getResources().getDrawable(R.drawable.ic_launcher));
        profilePic.setParseFile(parseProfilePic);
        if (profilePic != null) {
            profilePic.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null) {
                        // loaded successful
                    }
                    else {
                        // unsuccessful loading
                    }
                }
            });
        }
        TextView nameTV = (TextView) findViewById(R.id.textView2);
        nameTV.setTextSize(30);
        nameTV.setText(user.getString("name"));

        TextView phoneNumber = (TextView) findViewById(R.id.click);

        //emailTV.setText(curUser.getString("email"));
        if (user.getString("phoneNumber") != null) {
            SpannableString content = new SpannableString(user.getString("phoneNumber"));
            content.setSpan(new UnderlineSpan(), 0, user.getString("phoneNumber").length(), 0);
            phoneNumber.setText(content);
        } else {
            phoneNumber.setClickable(false);
        }

        TextView emailTV = (TextView) findViewById(R.id.textView3);
        emailTV.setText(user.getString("email"));

        // Replace this with number of skills later
        int size = 1; // total number of TextViews to add

        Button calendar = (Button) findViewById(R.id.calendar_button);

        Button logout = (Button) findViewById(R.id.logout_button);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCalendarEvents();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (ParseUser.getCurrentUser()!= null) {
                // Log user out.
                ParseUser.logOut();
                // Launch starter activity.
                startActivity(new Intent(ProfileActivity.this, StarterActivity.class));
            }
            }
        });

        Button bookingButton = (Button) findViewById(R.id.booking_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingRequestDialogue();
            }
        });
    }

    private void viewCalendarEvents() {
        // A date-time specified in milliseconds since the epoch.
        long startMillis = System.currentTimeMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }

    public void showBookingRequestDialogue(){
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.booking_popup,
                (ViewGroup) findViewById(R.id.popup_element));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setView(layout);

        // Setting Dialog Title
        alertDialog.setTitle("Booking Request");

        // Setting Dialog Message
        alertDialog.setMessage("Please Pick a date and time");

        // On pressing Book button
        alertDialog.setPositiveButton("Book", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog,int which) {
                final ParseUser currentUser = ParseUser.getCurrentUser();
                // Create new booking
                final Booking booking = new Booking();
                booking.setRequester(currentUser.getObjectId(),currentUser.getString("name"));
                booking.setRequested(user.getObjectId(),user.getString("name"));
                booking.setStatus(Booking.status.REQUEST);
                Date start = startTime.getTime();
                Date end = endTime.getTime();
                booking.setDateTime(start, end);
                System.out.println("Start time:" + start);
                System.out.println("End time:" + end);
                booking.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            // Create notification
                            Notification note = new Notification();
                            note.setFromUser(currentUser.getObjectId(), currentUser.getString("name"));
                            note.setToUser(user.getObjectId(), user.getString("name"));
                            note.setType(Notification.notificationType.BOOKING_REQUEST);
                            note.setPostId(booking.getObjectId());
                            note.saveInBackground();
                        }
                    }
                });
                dialog.dismiss();
            }
        });

        // On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.create();

        // Showing Alert Message
        alertDialog.show();
    }

    public void editProfile(View button) {
        context = getApplicationContext();
        Intent i = new Intent(context, EditProfileActivity.class);
        startActivity(i);
    }

    private String dateToString(int year, int month, int day) {
        StringBuilder dateString = new StringBuilder()
                .append(year).append("-")
                        // Month is 0 based, just add 1
                .append(month + 1).append("-")
                .append(day).append(" ");
        return dateString.toString();
    }

    private String timeToString(int hour, int minute) {
        StringBuilder timeString = new StringBuilder().append(hour)
                .append(":").append(minute);
        return timeString.toString();
    }

    public void showDatePickerDialog(final View v) {
        DatePickerDialog.OnDateSetListener dpListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Button b = (Button) v;
                String s = dateToString(year, monthOfYear, dayOfMonth);
                b.setText(s);
                b.setTextAppearance(getApplicationContext(), android.R.attr.textAppearanceSmall);
                startTime.set(year, monthOfYear, dayOfMonth);
                endTime.set(year, monthOfYear, dayOfMonth);
            }
        };
        DatePickerFragment dpFragment = DatePickerFragment.newInstance(dpListener);
        dpFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(final View v) {
        TimePickerDialog.OnTimeSetListener tpListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Button b = (Button) v;
                String s = timeToString(hourOfDay, minute);
                b.setText(s);
                b.setTextAppearance(getApplicationContext(), android.R.attr.textAppearanceSmall);
                if (b.getId() == R.id.timepicker1) {
                    startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    startTime.set(Calendar.MINUTE,minute);
                }
                else if (b.getId() == R.id.timepicker2) {
                    endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    endTime.set(Calendar.MINUTE,minute);
                }
            }
        };
        TimePickerFragment tpFragment = TimePickerFragment.newInstance(tpListener);
        tpFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void selectProfilePicture(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri chosenImageUri = data.getData();

            try {
                Bitmap mBitmap = null;
                mBitmap = Media.getBitmap(this.getContentResolver(), chosenImageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
                ParseFile bitMapPO = new ParseFile(stream.toByteArray());

                user.put("profile_pic", bitMapPO);
                user.saveInBackground();

                ImageView pic;
                pic = (ImageView) findViewById(R.id.imageView);
                pic.setImageBitmap(mBitmap);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.action_newpost:
                Dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                Dispatcher.openSearch(getApplicationContext(),this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openMessaging(View v) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + "2269720088"));
        startActivity(smsIntent);


    }

}
