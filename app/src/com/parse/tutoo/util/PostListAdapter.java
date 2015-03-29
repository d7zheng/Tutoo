package com.parse.tutoo.util;

import android.content.Context;

import com.parse.tutoo.model.Post;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 3/1/2015.
 */
public class PostListAdapter extends MenuListAdapter<Post> {

    public PostListAdapter(List<Post> objs, Context context) {
        super(objs, context);
    }

    @Override
    public String getFirstLine(Post obj) {
        return obj.getTitle();
    }

    @Override
    public String getSecondLine(Post obj) {
        return obj.getDescription();
    }

    @Override
    public String getThirdLine(Post obj) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date date = obj.getCreatedAt();
        return df.format(date);
    }

    @Override
    public String getDistance(Post obj) {
        return obj.getDistance();
    }
}
