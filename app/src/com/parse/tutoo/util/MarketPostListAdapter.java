package com.parse.tutoo.util;

import android.content.Context;

import com.parse.tutoo.model.MarketPost;

import java.util.List;

/**
 * Created by Owner on 3/1/2015.
 */
public class MarketPostListAdapter extends MenuListAdapter<MarketPost>{

    public MarketPostListAdapter(List<MarketPost> objs, Context context) {
        super(objs, context);
    }

    @Override
    public String getFirstLine(MarketPost obj) {
        return obj.getTitle();
    }

    @Override
    public String getSecondLine(MarketPost obj) {
        return obj.getDescription();
    }
}
