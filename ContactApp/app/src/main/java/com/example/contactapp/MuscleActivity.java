package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.contactapp.data.models.Days;
import com.example.contactapp.data.models.Workout;
import com.example.contactapp.fragments.FragAMuscle;


public class MuscleActivity extends BaseActivity {

    Days day;
    public Workout workout;
    public int dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getString(R.string.Fitness));
        workout = getIntent().getExtras().getParcelable("currentWorkout");
        dayNum = getIntent().getExtras().getInt("currentDay");
        FragAMuscle simpleA = FragAMuscle.newInstance();
        setContentView(R.layout.activity_muscle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, simpleA)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment f = MuscleActivity.this.getSupportFragmentManager().findFragmentById(R.id.content);
        if (f instanceof FragAMuscle) {
            Intent intent = new Intent();
            intent.putExtra("currentWorkout", workout);
            setResult(RESULT_OK, intent);
            finish();
        }
        super.onBackPressed();
    }
}