package com.thuan.fitnessapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thuan.fitnessapp.R;
import com.thuan.fitnessapp.data.ParentAdapter;
import com.thuan.fitnessapp.data.SimpleItemTouchHelperCallback;
import com.thuan.fitnessapp.data.models.Days;



public class ExerciseFragment extends Fragment {
    Days d;
    private ItemTouchHelper mItemTouchHelper;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        d = getArguments().getParcelable("w");
        RecyclerView exRV = view.findViewById(R.id.exerciseRVuser);
        exRV.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        exRV.setLayoutManager(mLayoutManager);
        ParentAdapter adapter = new ParentAdapter(getActivity(), d.getExercises());
        exRV.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(exRV);
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        exRV.startAnimation(bottomUp);
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}