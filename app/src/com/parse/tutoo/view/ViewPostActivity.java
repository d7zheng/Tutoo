package com.parse.tutoo.view;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.Reply;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ViewPostActivity extends ActionBarActivity {

    private Dispatcher dispatcher = new Dispatcher();
    private Post post;
    Vector<Reply> replyList = new Vector<Reply>();

    public void addListenerSelectTutor(View button) {
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
                /*
                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
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

        });

    }


    public void getReplies(){
        Intent intent = getIntent();

        ParseQuery query=new ParseQuery("Reply");
        query.whereEqualTo("postId", post.getPostId());

        try {
            //post = (Post)query.getFirst();
            List<Reply> replyObjects = query.find();
            for (int i = 0; i < replyObjects.size(); i++) {
                Reply r = (Reply)replyObjects.get(i);
                replyList.add(r);
            }
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

    }

    public void replyAction(View button){
        final EditText replyET = (EditText)findViewById(R.id.editText);
        String replyMessage = replyET.getText().toString();
        ParseUser currentUser = ParseUser.getCurrentUser();

        Reply reply = new Reply(currentUser.get("name").toString(), replyMessage, post.getPostId());

        reply.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                } else {
                    System.out.println("HELLO TEST");
                    replyET.setText("Replied!");
                }
            }
        });
    }


    public void addListenerReply() {
        Button thisTutorButton = (Button) findViewById(R.id.button1);

        thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText replyET = (EditText)findViewById(R.id.editText);
                String replyMessage = replyET.getText().toString();
                // TODO: Save this message
            }

        });

    }


    public void addListenerEdit() {
        Button thisTutorButton = (Button) findViewById(R.id.button3);

        thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: Go to new post view
            }

        });

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        //ParseObject.registerSubclass(Reply.class);
        //Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

        //TextView textView = (TextView) findViewById(R.id.viewpost1);


        TextView titleTV = (TextView)findViewById(R.id.textView1);
/*
        ArrayList<Post> posts = new ArrayList<Post>();

        List<ParseObject> parseObjects;
        ParseQuery query=new ParseQuery("Post");
        query.whereEqualTo("category", "All");
        try {
            parseObjects = query.find();
            titleTV.setText(parseObjects.size());
            for (int i = 0; i < parseObjects.size(); i++) {
                ParseObject parseObject = parseObjects.get(i);
                //ParsePost parsePost =new Post(parseObject.getObjectId(), parseObject.getString("title"), "test", parseObject.getString("category"));
                posts.add(new Post(parseObject.getObjectId(), parseObject.getString("title"), parseObject.getString("desc"), "All"));
            }
        }
        catch (  com.parse.ParseException e) {
            e.printStackTrace();
        }
*/

        Intent intent = getIntent();

        ParseQuery query=new ParseQuery("Post");
        query.whereEqualTo("objectId", intent.getStringExtra("post_id"));

        try {
           post = (Post)query.getFirst();
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

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
        boolean owner = false;


        // Hiding stuff according to user role
        if (owner) {
            LinearLayout replyLL = (LinearLayout) findViewById(R.id.linearLayout3);
            replyLL.setVisibility(View.GONE);
            //addListenerSelectTutor();
            addListenerEdit();
        } else {
            Button thisTutorB = (Button) findViewById(R.id.button1);
            thisTutorB.setVisibility(View.GONE);
            Button editB = (Button) findViewById(R.id.button3);
            editB.setVisibility(View.GONE);
            addListenerReply();
        }


        if (owner) {
            RadioButton[] tv = new RadioButton[size];
            RadioButton temp;

            for (int i = 0; i < size; i++)
            {
                temp = new RadioButton(this);
                // Replace this with actual skills later
                Reply r = replyList.get(i);
                String user = r.getReplyOwnerId();
                String description = r.getDescription();

                temp.setText(user + " " +description);
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radioGroup.addView(temp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(50,10,20,10);
                temp.setTextSize(20);
                temp.setLayoutParams(params);
                //temp.setBackgroundColor(Color.parseColor("CCFFCC"));
                tv[i] = temp;
            }
        } else {
            TextView[] tv = new TextView[size];
            TextView tempTV;

            LinearLayout[] ll = new LinearLayout[size];
            LinearLayout tempLL;
            boolean userOwnsThisReply = false;
            for (int i = 0; i < size; i++) {
                tempLL = new LinearLayout(this);
                tempLL.setOrientation(LinearLayout.HORIZONTAL);

                tempTV = new TextView(this);


                Reply r = replyList.get(i);
                String user = r.getReplyOwnerId();
                String description = r.getDescription();



                tempTV.setText(user + ": " +description);
                tempTV.setTextColor(getResources().getColor(R.color.white_opaque));
                tempTV.setBackgroundColor(R.drawable.border);
                tempTV.setWidth(900);
                //tempTV.setText("Tutor " + i);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);
                tempLL.addView(tempTV);
                linearLayout.addView(tempLL);
                if (i == 0) {
                    userOwnsThisReply = true;
                } else {
                    userOwnsThisReply = false;
                }
                /*
                if (userOwnsThisReply) {
                    Button editReplyB = new Button(this);
                    editReplyB.setText("Edit Reply");
                    tempLL.addView(editReplyB);

                }*/


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(80, 10, 20, 10);
                tempTV.setTextSize(20);
                tempTV.setLayoutParams(params);
                //TODO: Color? temp.setBackgroundColor(Color.parseColor("CCFFCC"));
                tv[i] = tempTV;
            }

        }


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
                dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                dispatcher.openSearch(getApplicationContext(),this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
