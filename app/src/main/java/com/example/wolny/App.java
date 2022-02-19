package com.example.wolny;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").setPersistenceEnabled(true);
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
