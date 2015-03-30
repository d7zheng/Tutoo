package com.parse.tutoo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Image;
import com.parse.tutoo.model.Notification;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.Reply;
import com.parse.tutoo.util.Dispatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ViewPostActivity extends ActionBarActivity {

    Context context;
    private Post post;
    Vector<Reply> replyList = new Vector<Reply>();
    TextView[] tv;
    ArrayList<ImageView> picList;

    public void addListenerSelectTutor(final View button, final String replyOwner) {
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        Button thisTutorButton = (Button) findViewById(R.id.button1);

        thisTutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioSelected = (RadioButton) findViewById(selectedId);

                radioSelected.setText("selected");

                Notification note = new Notification(ParseUser.getCurrentUser().getObjectId(),
                        replyOwner,Notification.notificationType.SELECTED, post.getPostId());
                note.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });

    }


    public void getReplies(){
        Intent intent = getIntent();

        ParseQuery query=new ParseQuery("Reply");
        query.whereEqualTo("postId", post.getPostId());
        query.orderByAscending("createdAt");

        try {
            //post = (Post)query.getFirst();
            List<Reply> replyObjects = query.find();
            for (int i = 0; i < replyObjects.size(); i++) {
                Reply r = (Reply)replyObjects.get(i);
                replyList.add(r);
            }
            picList = new ArrayList<ImageView>();
            for (int i = 0; i < replyList.size(); i++)
                picList.add(null);
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

    }

    public void replyAction(View button){
        final EditText replyET = (EditText)findViewById(R.id.editText);
        String replyMessage = replyET.getText().toString();
        ParseUser currentUser = ParseUser.getCurrentUser();

        Reply reply = new Reply(currentUser.get("name").toString(), replyMessage, post.getPostId(),
                                currentUser.getObjectId());


        try {
            reply.save();
        } catch(ParseException e){

        }
        /*
        reply.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                } else {
                    replyET.setText("");
                }
            }
        });*/

        Notification note = new Notification(currentUser.getObjectId(), post.getUser(),
                Notification.notificationType.REPLY, post.getPostId());
        note.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                }
            }
        });

        finish();
        startActivity(getIntent());
    }


    public void addListenerReply() {
        final Button thisTutorButton = (Button) findViewById(R.id.button2);

        thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText replyET = (EditText)findViewById(R.id.editText);
                String replyMessage = replyET.getText().toString();
                replyAction(thisTutorButton);
            }

        });
    }


    public void addListenerEdit() {
        Button editButton = (Button) findViewById(R.id.button3);

       /* thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: Go to new post view
            }

        });*/

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent i = new Intent(context, EditPostActivity.class);
                i.putExtra("post_id",  post.getObjectId());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        //ParseObject.registerSubclass(Reply.class);
        //Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

        //TextView textView = (TextView) findViewById(R.id.viewpost1);

        // Get current user


        TextView titleTV = (TextView)findViewById(R.id.textView1);

        Intent intent = getIntent();

        ParseQuery query=new ParseQuery("Post");
        query.whereEqualTo("objectId", intent.getStringExtra("post_id"));

        try {
           post = (Post)query.getFirst();
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        if(post == null) {
            finish();
            return;
        }

        final String userID = post.getUser();
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", userID);
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    ParseObject user = objects.get(0);
                    TextView userName = (TextView)findViewById(R.id.userName);
                    userName.setText("   " + user.getString("name"));

                    ParseFile profilePic = (ParseFile) user.getParseFile("profile_pic");
                    if (profilePic != null) {
                        profilePic.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null) {
                                    Bitmap bmp = BitmapFactory
                                            .decodeByteArray(data, 0, data.length);
                                    ImageView pic;
                                    pic = (ImageView) findViewById(R.id.poster);
                                    pic.setImageBitmap(bmp);

                                } else {
                                    System.out.println("No profile data found.");
                                }
                            }
                        });
                    }
                } else {
                    // Something went wrong.
                }
            }
        });
        TextView createdDate = (TextView)findViewById(R.id.creationTime);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        createdDate.setText("   " + df.format(post.getCreatedAt()));

        getReplies();
        titleTV.setText(post.getTitle());

        TextView textTV = (TextView)findViewById(R.id.textView2);

        //titleTV.setText(parseObjects.size());
        titleTV.setTextSize(20);

        textTV.setText(post.getDescription());
        textTV.setTextSize(20);

        // Replace this with number of skills later
        int size = replyList.size();//replyList.size(); // total number of TextViews to add

        //TODO: check if current user is the owner of the post, if yes display radio buttons
        String currentUser = ParseUser.getCurrentUser().getObjectId();
        boolean isOwner = userID.equals(currentUser);

        // Hiding stuff according to user role
        if (isOwner) {
            //LinearLayout replyLL = (LinearLayout) findViewById(R.id.linearLayout3);
            //replyLL.setVisibility(View.GONE);
            //addListenerSelectTutor();
            addListenerEdit();
            addListenerReply();

            Button closeB = (Button) findViewById(R.id.closeButton);
            closeB.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    post.put("closed", true);
                    post.saveInBackground();
                    finish();
                }
            });

        } else {
            Button editB = (Button) findViewById(R.id.button3);
            editB.setVisibility(View.GONE);
            Button closeB = (Button) findViewById(R.id.closeButton);
            closeB.setVisibility(View.GONE);
            addListenerReply();

        }

        tv = new TextView[size];
        TextView tempTV;
        TextView date;
        TextView userName;

        LinearLayout tempLL;

        LinearLayout userLayout;
        LinearLayout userNameLayout;
        boolean userOwnsThisReply = false;
        for (int i = 0; i < size; i++) {
            final int index = i;

            final Reply r = replyList.get(i);

            String userId = r.getUserId();
            ParseQuery<ParseUser> parseUserQuery = ParseUser.getQuery();
            parseUserQuery.whereEqualTo("objectId", userID);
            parseUserQuery.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        displayPicture(objects.get(0), index);
                    } else {
                        // Something went wrong.
                        System.out.println(e.getMessage());
                    }
                }
            });
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
                                    LinearLayout LLForImage = (LinearLayout) findViewById(R.id.linearLayoutImage);
                                    ImageView image = new ImageView(ViewPostActivity.this);
                                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(500,500);
                                    parms.gravity= Gravity.CENTER;
                                    image.setLayoutParams(parms);
                                    //image.setBackgroundResource(R.drawable.ic_launcher);
                                    LLForImage.addView(image);
                                    image.setImageBitmap(bmp);
                                    image.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {
                                            /*WebView wv = new WebView(ViewPostActivity.this);
                                            byte[] imageRaw = yourImage;
                                            String image64 = Base64.encodeToString(imageRaw, Base64.DEFAULT);
                                            String pageData = "<img src=\"data:image/jpeg;base64," + image64 + "\" />";*/
                                            //view.setVisibility(View.GONE);
                                            //int id = view.getId();
                                            //imageViewsGone.add(id);

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

    public void displayPicture(ParseObject user, int i) {
        final Reply r = replyList.get(i);

        picList.set(i, new ImageView(this));
        final int index = i;
        ParseFile profilePic = (ParseFile) user.getParseFile("profile_pic");
        if (profilePic != null) {
            profilePic.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (data != null) {
                        Bitmap bmp = BitmapFactory
                                .decodeByteArray(data, 0, data.length);
                        ImageView curPic = picList.get(index);
                        curPic.setImageBitmap(bmp);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
                        curPic.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        curPic.setLayoutParams(layoutParams);
                        picList.set(index, curPic);
                        setNewReply(index);
                       // postPic.setImageBitmap(bmp);

                    } else {
                        System.out.println("No profile data found.");
                    }
                }
            });
        }

}

    public void setNewReply(int i) {
        final Reply r = replyList.get(i);
        TextView tempTV;
        TextView date;
        TextView userName;

        LinearLayout tempLL;

        LinearLayout userLayout;
        LinearLayout userNameLayout;
        boolean userOwnsThisReply = false;

        tempLL = new LinearLayout(this);
        tempLL.setOrientation(LinearLayout.VERTICAL);
        userLayout = new LinearLayout(this);
        userLayout.setOrientation(LinearLayout.HORIZONTAL);

        date = new TextView(this);
        userName = new TextView(this);
        tempTV = new TextView(this);

        DateFormat dateF = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        date.setText(dateF.format(r.getCreatedAt()));
        date.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));
       // date.setWidth(600);

        String replyOwner = r.getReplyOwnerId();
        String description = r.getDescription();


        userName.setText(replyOwner);
        tempTV.setText(replyOwner + ": " + description);
        tempTV.setTextColor(getResources().getColor(R.color.green_opaque));
        //tempTV.setBackgroundColor(R.drawable.border);
        // tempTV.setWidth(900);

        tempTV.setClickable(true);
        tempTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                context = getApplicationContext();
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("id",  r.getUserId());
                startActivity(i);
            }
        });


        //tempTV.setText("Tutor " + i);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);
        tempLL.addView(date);
        tempLL.addView(tempTV);

        //linearLayout.addView(tempLL);
        if (i == 0) {
            userOwnsThisReply = true;
        } else {
            userOwnsThisReply = false;
        }

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        tempTV.setTextSize(20);
        params1.setMargins(20, 0, 0, 0);
        date.setLayoutParams(params1);

        params2.setMargins(60, 0, 60,30);
        tempTV.setLayoutParams(params1);
        userLayout.setLayoutParams(params2);
        userLayout.addView(picList.get(i));
        userLayout.addView(tempLL);
        userLayout.setBackgroundColor(Color.parseColor("#559059"));
        linearLayout.addView(userLayout);
        //TODO: Color? temp.setBackgroundColor(Color.parseColor("CCFFCC"));
        tv[i] = tempTV;
    }

    public void userProfile(View v) {
        context = getApplicationContext();
        Intent i = new Intent(context, ProfileActivity.class);
        i.putExtra("id", post.getUser());
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_posts, menu);
        return super.onCreateOptionsMenu(menu);
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
}
