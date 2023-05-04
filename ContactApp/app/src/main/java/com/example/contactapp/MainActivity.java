package com.example.contactapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.contactapp.data.models.Days;
import com.example.contactapp.data.models.Workout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.ProgressStartPoint;

import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    TextView workoutName, dayName, name, cals;
//    private static final String ID = "ca-pub-4044061584833483";
//    private static final String TESTAD = "ca-app-pub-3940256099942544/1033173712";
    ImageView next;
    int day;
    private Workout workoutNow;
    private CircleSegmentBar segmentBar;
    private Runnable mTimer;
    protected int progress;
    private Handler mHandler;
    ImageButton btn;
    Workout dzdz;
    private List<Days> daysList;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        day = 0;
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getString(R.string.Fitness));
        segmentBar = findViewById(R.id.segment_bar);
        cals = findViewById(R.id.calsNum);
        btn = findViewById(R.id.start);
        next = findViewById(R.id.next);
        workoutName = findViewById(R.id.workoutName);
        dayName = findViewById(R.id.dayName);
        findViewById(R.id.polygonWorkout).setOnClickListener(this);
        findViewById(R.id.polygonFood).setOnClickListener(this);
        findViewById(R.id.polygonProgress).setOnClickListener(this);
        name = findViewById(R.id.name);
        name.setText(mAuth.getCurrentUser().getDisplayName());
        mHandler = new Handler();
        initSegmentProgressBar();
        final long[] mVibratePattern = new long[]{0, 100000, 0, 100000};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (vibrator != null) {
                        vibrator.vibrate(mVibratePattern, -1);
                    }
                    setTimer();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    progress = 0;
                    segmentBar.setProgress((float) progress);
                    segmentBar.setText("" + progress, 30, Color.GRAY);
                    mHandler.removeCallbacks(mTimer);
                    if (vibrator != null) {
                        vibrator.cancel();
                    }
                    return false;
                }
                return true;
            }
        });

        next.setOnClickListener(v -> {
                int x = daysList.size();
                if (day < x - 1)
                    day++;
                else
                    day = 0;
                dayName.setText(daysList.get(day).getName());

        });
    }
    private void setTimer() {
        mTimer = new Runnable() {
            @Override
            public void run() {
                progress += 5;
                if (progress <= 100)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            segmentBar.setProgress((float) progress);
                            segmentBar.setText("" + progress, 30, Color.GRAY);
                        }
                    });
                mHandler.postDelayed(this, 50);
            }
        };
        mHandler.postDelayed(mTimer, 100);
    }


    private void initSegmentProgressBar() {
        segmentBar.setCircleViewPadding(2);
        segmentBar.setWidthProgressBackground(25);
        segmentBar.setWidthProgressBarLine(25);
        segmentBar.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);
        segmentBar.setLinearGradientProgress(true);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.polygonWorkout: {
                Intent intent = new Intent(MainActivity.this, BuilderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.polygonFood: {
                Intent intent = new Intent(MainActivity.this, NutriActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.polygonProgress: {
                Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.start:
                Toast.makeText(this, "Start Button Pressed", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private int getData3() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("GOAL", 0);
    }

    private int getDefault() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("DEF", 101);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = this.getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        final SharedPreferences.Editor mEditor = sharedPreferences.edit();
        DatabaseReference mRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("workouts");
        mRef.keepSynced(true);
        mRef.orderByChild("def").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot != null)
                        dzdz = childDataSnapshot.getValue(Workout.class);
                }
                if (dzdz != null) {
                    workoutName.setText(dzdz.getName());
                    daysList = dzdz.getDays();
                    workoutNow = dzdz;
                    mEditor.putInt("DEF", workoutNow.getId());
                    dayName.setText(daysList.get(0).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (getData3() == 0)
            cals.setText(getResources().getString(R.string.please_calc));
        else cals.setText(getData3() + "");
        if (getDefault() == 101) {
            workoutName.setText("Pick a workout");
            mEditor.putString("WORK", "Pick a workout");
        } else if (dzdz != null) {
            workoutName.setText(workoutNow.getName());
            dayName.setText(workoutNow.getDays().get(day).getName());
            mEditor.putString("WORK", workoutNow.getName());
        }
        mEditor.apply();
    }
}