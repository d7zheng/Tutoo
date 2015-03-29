package com.parse.tutoo.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Image;
import com.parse.tutoo.model.Market;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.State;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.view.fragment.DatePickerFragment;
import com.parse.tutoo.view.fragment.TimePickerFragment;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class EditPostActivity extends ActionBarActivity {
    private Post post;
    private String title;
    private String description;
    private String feedbackType;
    private String skillsets;
    private Context context;
    private Location location;
    private LocationManager manager;
    private boolean isFirst = true;
    private boolean locationEnabled = false;
    private List<Bitmap> pics = new ArrayList<Bitmap>();
    private List<Integer> imageViewsGone = new ArrayList<Integer>();
    private int imageNumber = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE );
        setContentView(R.layout.activity_edit_post);

        setupUI();
        setCurrentDateOnView();
    }


    private void setDateOnButton(Button b, int year, int month, int day) {
        b.setText(new StringBuilder()
                .append(year).append("-")
                        // Month is 0 based, just add 1
                .append(month + 1).append("-")
                .append(day).append(" "));
        b.setTextAppearance(this, android.R.attr.textAppearanceSmall );
    }

    private void setTimeOnButton(Button b, int hour, int minute) {
        b.setText( new StringBuilder().append(hour)
                .append(":").append(minute));
        b.setTextAppearance(this, android.R.attr.textAppearanceSmall );
    }

    private void setCurrentDateOnView() {
        Button datepicker1Display = (Button) findViewById(R.id.datepicker1Edit);
        Button timepicker1Display = (Button) findViewById(R.id.timepicker1Edit);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // set current date time into textview
        setDateOnButton(datepicker1Display, year, month, day);
        setTimeOnButton(timepicker1Display, hour, minute);
    }

    private void setSubSpinner(Class<?> cls, Spinner spinner) {
        List<String> list = new ArrayList<>();
        if (cls == Category.class) {
            for (Category element : Category.values()) {
                if (element == Category.All) {
                    continue;
                }
                list.add(element.toString());
            }
        }
        else if (cls == Market.class) {
            for (Market element: Market.values()) {
                if (element == Market.All) {
                    continue;
                }
                list.add(element.toString());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void setupUI() {
        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackTypeEdit);
        final Spinner feedbackSubSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackSubTypeEdit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.newPostSpinnerList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        feedbackSpinner.setAdapter(adapter);



        System.out.println("onCreate setupUI");


        Intent intent = getIntent();

        ParseQuery query=new ParseQuery("Post");
        query.whereEqualTo("objectId", intent.getStringExtra("post_id"));

        try {
            post = (Post)query.getFirst();
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        EditText editTitle = (EditText)findViewById(R.id.editTitle);
        editTitle.setText(post.getTitle());
        EditText editDescription = (EditText)findViewById(R.id.editDescription);
        editDescription.setText(post.getDescription());
        EditText editSkillsets = (EditText)findViewById(R.id.editSkillsets);
        editSkillsets.setText(post.getSkills());

        if (post.getGetPoints() != null) {
            CheckBox cb1 = (CheckBox) findViewById(R.id.enable_locationEdit);
            cb1.setChecked(true);
        }

        feedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position=" + parent.getItemAtPosition(position).toString());
                if (parent.getItemAtPosition(position).toString().equals(getString(R.string.spinnerItem1Services))) {
                    // refresh list, service list
                    // setcategory list
                    //EditText editText = (EditText)findViewById(R.id.editSkillsets);
                   // editText.setHint("  Skillsets");
                    System.out.println("++++");
                    setSubSpinner(Category.class, feedbackSubSpinner);
                    String editType = post.getType();
                    if (editType.equals("market")) {
                        Market editMarket = post.getMarket(); //the value you want the position for
                        feedbackSubSpinner.setSelection(editMarket.ordinal() - 1);
                    }
                    else {
                        Category editCategory = post.getCategory(); //the value you want the position for
                        feedbackSubSpinner.setSelection(editCategory.ordinal() -  1);
                    }

                } else if (parent.getItemAtPosition(position).toString().equals(getString(R.string.spinnerItem2Markets))) {
                    // refresh list
                    //EditText editText = (EditText)findViewById(R.id.editSkillsets);
                    // editText.setHint("  Tags");
                    System.out.println("====");
                    setSubSpinner(Market.class, feedbackSubSpinner);
                    String editType = post.getType();
                    if (editType.equals("market")) {
                        feedbackSpinner.setSelection(1);
                        Market editMarket = post.getMarket(); //the value you want the position for
                        editMarket.ordinal();
                        feedbackSubSpinner.setSelection(editMarket.ordinal()-1);
                    }
                    else {
                        feedbackSpinner.setSelection(0);
                        Category editCategory = post.getCategory(); //the value you want the position for
                        editCategory.ordinal();
                        feedbackSubSpinner.setSelection(editCategory.ordinal()-1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        String editType = post.getType();
        if (editType.equals("market")) {
            feedbackSpinner.setSelection(1);
            Market editMarket = post.getMarket(); //the value you want the position for
        }
        else {
            feedbackSpinner.setSelection(0);
            Category editCategory = post.getCategory(); //the value you want the position for
        }


        // Display images

        ParseQuery imageQuery=new ParseQuery("Image");
        imageQuery.whereEqualTo("postId", post.getPostId());
        //imageQuery.orderByAscending("createdAt");

        try {
            //post = (Post)query.getFirst();
            List<Image> imageObjects = imageQuery.find();
            for (int i = 0; i < imageObjects.size(); i++) {
                Image image = (Image)imageObjects.get(i);
                ParseFile picture = (ParseFile) image.getParseFile("bmp");
                if (picture != null) {
                    picture.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (data != null) {

                                Bitmap bmp = BitmapFactory
                                        .decodeByteArray(data, 0, data.length);
                                if (bmp != null) {
                                    LinearLayout LLForImage = (LinearLayout) findViewById(R.id.linearLayoutImageEdit);
                                    ImageView image = new ImageView(EditPostActivity.this);
                                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(500,500);
                                    parms.gravity= Gravity.CENTER;
                                    image.setLayoutParams(parms);
                                    //image.setBackgroundResource(R.drawable.ic_launcher);
                                    LLForImage.addView(image);
                                    image.setImageBitmap(bmp);

                                    pics.add(bmp);
                                    image.setId(imageNumber);
                                    imageNumber++;
                                    image.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {
                                            view.setVisibility(View.GONE);
                                            int id = view.getId();
                                            imageViewsGone.add(id);
                                        }
                                    });


                                }

                            } else {
                                System.out.println("No image data found.");
                            }
                        }
                    });

                }

            }
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_post, menu);
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

    public void editPost(View button) {
        // Do click handling here
        finish();

        final EditText nameField = (EditText) findViewById(R.id.editTitle);
        title = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.editDescription);
        description = emailField.getText().toString();

        // skillsets or tags
        final EditText skillSets = (EditText) findViewById(R.id.editSkillsets);
        skillsets = skillSets.getText().toString();

        boolean isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackTypeEdit);
        final Spinner feedbackSubSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackSubTypeEdit);


        post.setTitle(title);
        post.setDescription(description);
        post.setSkills(skillsets);

        if (feedbackSpinner.getSelectedItem().toString().equals(getString(R.string.spinnerItem1Services))) {
            post.setType("services");
            String category = feedbackSubSpinner.getSelectedItem().toString();
            Category enumCategory = Category.valueOf(category);
            post.setCategory(enumCategory);
        } else if (feedbackSpinner.getSelectedItem().toString().equals(getString(R.string.spinnerItem2Markets))) {
            post.setType("market");
            String market = feedbackSubSpinner.getSelectedItem().toString();
            Market enumMarket = Market.valueOf(market);
            post.setMarket(enumMarket);
        }

        if ((locationEnabled) && (isNetworkEnabled)) {
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            post.setGeoPoints(location);
        }
        else {
            post.remove("location");
        }
        post.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                } else {
                    //delete all images and re-upload current
                    ParseQuery imageQuery=new ParseQuery("Image");
                    imageQuery.whereEqualTo("postId", post.getPostId());

                    try {
                        List<Image> imageObjects = imageQuery.find();
                        for (int i = 0; i < imageObjects.size(); i++) {
                            Image image = (Image)imageObjects.get(i);
                            image.deleteInBackground();
                        }

                        String postID = post.getObjectId();
                        // Save images
                        for (int i = 0; i < pics.size(); i++) {
                            Integer index = i;
                            if (!imageViewsGone.contains(index)) {
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                pics.get(i).compress(Bitmap.CompressFormat.PNG, 10, stream);
                                ParseFile bitMapPO = new ParseFile(stream.toByteArray());
                                Image im = new Image(postID, bitMapPO);
                                im.saveInBackground();
                            }
                        }
                    }
                    catch (com.parse.ParseException ex) {
                        e.printStackTrace();
                    }

                }
            }
        });



    }

    public void showDatePickerDialog(final View v) {
        DatePickerDialog.OnDateSetListener dpListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setDateOnButton((Button) v,year,monthOfYear,dayOfMonth);
            }
        };
        DatePickerFragment dpFragment = DatePickerFragment.newInstance(dpListener);
        dpFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(final View v) {
        TimePickerDialog.OnTimeSetListener tpListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTimeOnButton((Button) v, hourOfDay, minute);
            }
        };
        TimePickerFragment tpFragment = TimePickerFragment.newInstance(tpListener);
        tpFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        locationEnabled = ((CheckBox) view).isChecked();

        // get user location
        boolean isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isNetworkEnabled) {
            // show alert
            showSettingsAlert();
        }
    }

    public void showSettingsAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Location settings");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    public void attachPictureEdit(View button) {
        // Do click handling here
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
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                if (mBitmap != null) {
                    LinearLayout LLForImage = (LinearLayout) findViewById(R.id.linearLayoutImageEdit);
                    ImageView image = new ImageView(this);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(500,500);
                    parms.gravity= Gravity.CENTER;
                    image.setLayoutParams(parms);
                    //image.setBackgroundResource(R.drawable.ic_launcher);
                    LLForImage.addView(image);
                    pics.add(mBitmap);
                    image.setId(imageNumber);
                    imageNumber++;
                    image.setImageBitmap(mBitmap);
                    image.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            view.setVisibility(View.GONE);
                            int id = view.getId();
                            imageViewsGone.add(id);

                        }
                    });


                }

                final ScrollView sv = (ScrollView)findViewById(R.id.scrollView);
                //sv.scrollTo(0, sv.getBottom());

                sv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        sv.post(new Runnable() {
                            public void run() {
                                sv.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                });

            } catch (IOException ex) {
                System.out.println("nope");
            }
        }
    }
}
