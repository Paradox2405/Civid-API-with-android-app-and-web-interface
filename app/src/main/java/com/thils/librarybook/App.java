package com.thils.librarybook;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        Stetho.initializeWithDefaults(this);
        AndroidNetworking.initialize(getApplicationContext());
    }
}
