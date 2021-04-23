package com.thils.webApiApp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.thils.webApiApp.BuildConfig;

public class Settings {

    private static final String PREF_NAME = BuildConfig.APPLICATION_ID + ".settings";
    private Context context;

    public Settings(Context context) {
        this.context = context;
    }

    public SharedPreferences getPref() {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    public void setUser(String value) {
        getPref().edit().putString("user", value).commit();
    }

    public String getUser() {
        return getPref().getString("user", "");
    }
}
