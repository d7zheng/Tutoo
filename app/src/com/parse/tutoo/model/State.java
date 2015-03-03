package com.parse.tutoo.model;

/**
 * Created by hilary on 02/03/2015.
 */
public class State {
    private static int count = 0;
    public static int getCount () {
        int ans = count;
        count++;
        return ans;
    }
}
