package com.parse.tutoo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.*;
import android.widget.*;

import com.parse.*;
import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;
import com.parse.ui.ParseLoginBuilder;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.media.*;
import android.provider.MediaStore.Images.Media;
import java.io.*;

public class ProfileActivity extends ActionBarActivity {
    ParseUser curUser;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b == null) {
            displayProfile(ParseUser.getCurrentUser());

            Button logout = (Button)findViewById(R.id.logout_button);
            logout.setVisibility(View.VISIBLE);
            Button editProfile = (Button)findViewById(R.id.edit_profile);
            editProfile.setVisibility(View.VISIBLE);
        }
        else {
            String userID = b.getString("id");
            if (userID.equals(ParseUser.getCurrentUser().getObjectId())) {
                Button logout = (Button)findViewById(R.id.logout_button);
                logout.setVisibility(View.VISIBLE);
                Button editProfile = (Button)findViewById(R.id.edit_profile);
                editProfile.setVisibility(View.VISIBLE);
            }
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", userID);
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        displayProfile(objects.get(0));
                    } else {
                        // Something went wrong.
                        System.out.println(e.getMessage());
                    }
                }
            });
        }

        curUser = ParseUser.getCurrentUser();
        ParseFile parseProfilePic = curUser.getParseFile("profile_pic");

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
            /*profilePic.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null) {

                        //Bitmap bmp = BitmapFactory
                        //        .decodeByteArray(data, 0, data.length);
                        ImageView pic;
                        pic = (ImageView) findViewById(R.id.imageView);
                        pic.setImageBitmap(bmp);

                    } else {
                        System.out.println("No profile data found.");
                    }
                }
            });*/
        }

        Button bookingButton = (Button) findViewById(R.id.booking_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("Booking");
                createPopup();
            }
        });
    }

    private void createPopup() {
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.activity_profile,
                (ViewGroup) findViewById(R.id.popup_element));
        //LinearLayout layout = new LinearLayout(getApplicationContext());
        PopupWindow popUp = new PopupWindow(layout, 200, 300, true);;
        popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
        //popUp.update(50, 50, 300, 80);
        //layout.setOrientation(LinearLayout.VERTICAL);
        //popUp.setContentView(layout);
        Button buttonRequest = (Button) layout.findViewById(R.id.popup_request);
        Button buttonCancel = (Button) layout.findViewById(R.id.popup_cancel);
        //buttonCanel.setOnClickListener(cancel_button_click_listener);
    }

    public void displayProfile(ParseObject user) {
        TextView nameTV = (TextView) findViewById(R.id.textView2);
        nameTV.setTextSize(30);
        nameTV.setText(user.getString("name"));

        TextView phoneNumber = (TextView) findViewById(R.id.click);

        SpannableString number = new SpannableString("111-111-111");
        number.setSpan(new UnderlineSpan(), 0, number.length(), 0);
        //emailTV.setText(curUser.getString("email"));
        if (user.getString("phoneNumber") != null) {
            phoneNumber.setText(user.getString("phoneNumber"));
        } else {
            phoneNumber.setClickable(false);
        }

        TextView emailTV = (TextView) findViewById(R.id.textView3);
        emailTV.setText(user.getString("email"));

        TextView locationTV = (TextView) findViewById(R.id.textView);
        locationTV.setText("Lives in Waterloo, Canada");

        TextView skills = (TextView) findViewById(R.id.skillsets);
        skills.setText("algorithms, math138, cs245, algebra");

        RatingBar rating = (RatingBar) findViewById(R.id.rating);
        rating.setRating(5);
        // Replace this with number of skills later
        int size = 1; // total number of TextViews to add

        Button calendar = (Button) findViewById(R.id.calendar_button);

        Button logout = (Button) findViewById(R.id.logout_button);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, CalendarActivity.class));
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
    }

    public void editProfile(View button) {
        context = getApplicationContext();
        Intent i = new Intent(context, EditProfileActivity.class);
        startActivity(i);
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        finish();
        startActivity(getIntent());
    }*/

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

                curUser.put("profile_pic", bitMapPO);
                curUser.saveInBackground();

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
