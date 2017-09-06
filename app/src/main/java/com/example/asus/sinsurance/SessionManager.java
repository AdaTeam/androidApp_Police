package com.example.asus.sinsurance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SessionManager {

    static final String PREF_USER_NAME = "username";
    static final String PREF_PASSWORD = "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserPass(Context ctx, String userName , String password) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }

    public static String getUser(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }


    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
