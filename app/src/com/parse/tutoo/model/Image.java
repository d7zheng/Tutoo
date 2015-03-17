package com.parse.tutoo.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by xiaohanwu on 2015-03-16.
 */

@ParseClassName("Image")
public class Image extends ParseObject {

    public Image(String postId, ParseFile bmp) {
        put("postId",postId);
        put("bmp",bmp);
    }

    public Image() {
        super();
    }

    public void setBmp(ParseFile bmp) { put("bmp", bmp);}

    public void setPostId(String postId) { put("postId",postId);}

    public String getPostId () { return getString("postId");}
    public ParseFile getBmp() { return getParseFile("bmp");}


    public void setAttributes(String postId, ParseFile bmp) {
        put("postId",postId);
        put("bmp",bmp);
    }
}
