package com.example.contactapp;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.FirebaseDatabase;


public class Fitz extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // Đăng ký sử dụng Analytics
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);
        // Khởi tạo Firebase Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

    }
}
