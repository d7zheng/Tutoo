package com.parse.tutoo.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Market;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfileActivity extends ActionBarActivity {
    private ParseUser curUser;
    private ParseFile bitMapPO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        curUser = ParseUser.getCurrentUser();
        EditText editName = (EditText)findViewById(R.id.name);
        editName.setText(curUser.getString("name"));
        EditText editEmail = (EditText)findViewById(R.id.email);
        editEmail.setText(curUser.getString("email"));
        EditText editPhone = (EditText)findViewById(R.id.phoneNumber);
        editPhone.setText(curUser.getString("phoneNumber"));

        curUser = ParseUser.getCurrentUser();
        ParseFile profilePic = (ParseFile) curUser.getParseFile("profile_pic");
        if (profilePic != null) {/*
                byte[] data = profilePic.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, com.parse.ParseException e) {
                    if (data != null) {

                        Bitmap bmp = BitmapFactory
                                .decodeByteArray(data, 0, data.length);
                        ImageView pic;
                        pic = (ImageView) findViewById(R.id.imageView);
                        pic.setImageBitmap(bmp);

                    } else {
                        System.out.println("nope nope");
                    }}
                });
                */
            profilePic.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null) {

                        Bitmap bmp = BitmapFactory
                                .decodeByteArray(data, 0, data.length);
                        ImageView pic;
                        pic = (ImageView) findViewById(R.id.imageView);
                        pic.setImageBitmap(bmp);

                    } else {
                        System.out.println("No profile data found.");
                    }
                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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

    public void selectProfilePicture(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();

            try {
                Bitmap mBitmap = null;
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
                bitMapPO = new ParseFile(stream.toByteArray());

                ImageView pic;
                pic = (ImageView) findViewById(R.id.imageView);
                pic.setImageBitmap(mBitmap);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void editProfile(View button) {
        // Do click handling here
        finish();

        final EditText editName = (EditText)findViewById(R.id.name);
        String name = editName.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.email);
        String email = emailField.getText().toString();

        final EditText phone = (EditText) findViewById(R.id.phoneNumber);
        String phoneNumber = phone.getText().toString();
        if (phoneNumber.equals("") || phoneNumber == null) {
            curUser.remove("phoneNumber");
        } else {
            curUser.put("phoneNumber", phoneNumber);
        }

        curUser.put("name", name);
        curUser.setEmail(email);

        if (bitMapPO != null) {
         curUser.put("profile_pic", bitMapPO);
        }

        curUser.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
