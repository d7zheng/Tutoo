package com.parse.tutoo.view;

import android.app.Activity;
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

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.media.*;
import android.provider.MediaStore.Images.Media;
import java.io.*;

public class ProfileActivity extends ActionBarActivity {
    ParseUser curUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle b = getIntent().getExtras();
        if (b == null) {
            displayProfile(ParseUser.getCurrentUser());
        }
        else {
            String userID = b.getString("id");
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", userID);
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        displayProfile(objects.get(0));
                    } else {
                        // Something went wrong.
                    }
                }
            });
        }


        curUser = ParseUser.getCurrentUser();
        try{
            ParseFile profilePic = curUser.getParseFile("profile_pic");
            if (profilePic != null) {
                byte[] data = profilePic.getData();
                if (data != null) {

                    Bitmap bmp = BitmapFactory
                            .decodeByteArray(data, 0, data.length);
                    ImageView pic;
                    pic = (ImageView) findViewById(R.id.imageView);
                    pic.setImageBitmap(bmp);

                } else {
                    System.out.println("nope nope");
                }
            }
        }catch (ParseException e){

            System.out.println("nope nope");
        }

    }

    public void displayProfile(ParseObject user) {
        TextView nameTV = (TextView) findViewById(R.id.textView2);
        nameTV.setTextSize(30);
        nameTV.setText(user.getString("name"));

        TextView phoneNumber = (TextView) findViewById(R.id.click);

        SpannableString number = new SpannableString("111-111-111");
        number.setSpan(new UnderlineSpan(), 0, number.length(), 0);
        //emailTV.setText(curUser.getString("email"));
        phoneNumber.setText(number);

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
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                ParseFile bitMapPO = new ParseFile(stream.toByteArray());


                curUser.put("profile_pic", bitMapPO);
                curUser.save();
            } catch (IOException ex) {
                System.out.println("nope");
            }catch (ParseException e)
            {


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
