package com.example.contactapp.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.contactapp.MuscleActivity;
import com.example.contactapp.R;
import com.example.contactapp.data.models.Days;
import com.example.contactapp.data.models.Muscle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragAMuscle extends Fragment {
    public static final String TAG = FragAMuscle.class.getSimpleName();
    private List<Muscle> muscleList;
    Days day;
    public MuscleActivity muscleActivity;

    public FragAMuscle() {
        // Required empty public constructor
    }

    public static FragAMuscle newInstance() {
        return new FragAMuscle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_muscle_a, container, false);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("muscles");
        myRef.keepSynced(true);
        muscleList = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Muscle muscle = childDataSnapshot.getValue(Muscle.class);
                    muscleList.add(muscle);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("NOPE", "Failed to read value.", error.toException());
                throw error.toException();
            }
        });

        final ImageButton abs_view = view.findViewById(R.id.abs);
        final ImageButton chest_view = view.findViewById(R.id.chest);
        final ImageButton back_view = view.findViewById(R.id.back);
        final ImageButton tricep_view = view.findViewById(R.id.tricep);
        final ImageButton bicep_view = view.findViewById(R.id.bicep);
        final ImageButton legs_view = view.findViewById(R.id.legs);
        final ImageButton cardio_view = view.findViewById(R.id.cardio);
        final ImageButton crossfit_view = view.findViewById(R.id.crossfit);
        final ImageButton shoulder_view = view.findViewById(R.id.shoulder);
        abs_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.abs_t);
            bundle.putString("TRANS", abs_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(6));
            bundle.putInt("PHOTO", R.drawable.mabs);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(abs_view, Objects.requireNonNull(ViewCompat.getTransitionName(abs_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });

        chest_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.chest_t);
            bundle.putString("TRANS", chest_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(0));
            bundle.putInt("PHOTO", R.drawable.mchest);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(chest_view, Objects.requireNonNull(ViewCompat.getTransitionName(chest_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        back_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.back_t);
            bundle.putParcelable("MUSCLE", muscleList.get(1));
            bundle.putString("TRANS", back_view.getTransitionName());
            bundle.putInt("PHOTO", R.drawable.mback);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(back_view, Objects.requireNonNull(ViewCompat.getTransitionName(back_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        tricep_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.tricep_t);
            bundle.putString("TRANS", tricep_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(5));
            bundle.putInt("PHOTO", R.drawable.mtriceps);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(tricep_view, Objects.requireNonNull(ViewCompat.getTransitionName(tricep_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        bicep_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.bicep_t);
            bundle.putString("TRANS", bicep_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(4));
            bundle.putInt("PHOTO", R.drawable.mbicep);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(bicep_view, Objects.requireNonNull(ViewCompat.getTransitionName(bicep_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        legs_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.legs_t);
            bundle.putString("TRANS", legs_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(2));
            bundle.putInt("PHOTO", R.drawable.mquad);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(legs_view, Objects.requireNonNull(ViewCompat.getTransitionName(legs_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        cardio_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.cardio_t);
            bundle.putString("TRANS", cardio_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(7));
            bundle.putInt("PHOTO", R.drawable.mtest);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(cardio_view, Objects.requireNonNull(ViewCompat.getTransitionName(cardio_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        crossfit_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.crossfit_t);
            bundle.putString("TRANS", crossfit_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(8));
            bundle.putInt("PHOTO", R.drawable.mc2);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(crossfit_view, Objects.requireNonNull(ViewCompat.getTransitionName(crossfit_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        shoulder_view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PIC", R.drawable.shoulder_t);
            bundle.putString("TRANS", shoulder_view.getTransitionName());
            bundle.putParcelable("MUSCLE", muscleList.get(3));
            bundle.putInt("PHOTO", R.drawable.mdelt);
            FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
            simpleFragmentB.setArguments(bundle);
//            assert getChildFragmentManager() != null;
            getChildFragmentManager()
                    .beginTransaction()
                    .addSharedElement(shoulder_view, Objects.requireNonNull(ViewCompat.getTransitionName(shoulder_view)))
                    .addToBackStack(TAG)
                    .replace(R.id.content, simpleFragmentB)
                    .commit();

        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize(view);
    }
}