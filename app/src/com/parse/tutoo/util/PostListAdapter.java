package com.parse.tutoo.util;

import android.content.Context;

import com.parse.tutoo.model.Post;

import java.util.List;

/**
 * Created by Owner on 3/1/2015.
 */
public class PostListAdapter extends MenuListAdapter<Post>{

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
}
