package com.example.contactapp;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapp.data.ParentAdapter;
import com.example.contactapp.data.models.Days;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.Utils;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.github.jinatonic.confetti.confetto.ShimmeringConfetto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WorkingOutActivity extends AppCompatActivity implements ConfettoGenerator {

    private final List<ConfettiManager> activeConfettiManagers = new ArrayList<>();
    public Dialog finished;
    public int total = 0;
    public int count = 0;
    public String[] quotes;
    protected ViewGroup container;
    protected int goldDark, goldMed, gold, goldLight;
    protected int[] colors;
    Button start, pause, done;
    boolean started = false;
    boolean paused = false;
    Chronometer chrono;
    RecyclerView workoutRV;
    private int size;
    private int velocitySlow, velocityNormal;
    private long stopped = 0;
    private List<Bitmap> confettoBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_out);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getString(R.string.Fitness));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bar);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        getSupportActionBar().setBackgroundDrawable(drawable);
        final Resources res = getResources();
        quotes = res.getStringArray(R.array.quotes);
        size = res.getDimensionPixelSize(com.github.jinatonic.confetti.R.dimen.default_confetti_size);
        velocitySlow = res.getDimensionPixelOffset(com.github.jinatonic.confetti.R.dimen.default_velocity_slow);
        velocityNormal = res.getDimensionPixelOffset(com.github.jinatonic.confetti.R.dimen.default_velocity_normal);
        goldDark = res.getColor(R.color.gold_dark);
        goldMed = res.getColor(R.color.gold_med);
        container = findViewById(R.id.container);
        gold = res.getColor(R.color.gold);
        goldLight = res.getColor(R.color.gold_light);
        colors = new int[]{goldDark, goldMed, gold, goldLight};
        confettoBitmaps = Utils.generateConfettiBitmaps(colors, size);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        Days day = b.getParcelable("day");

        done = findViewById(R.id.done);
        start = findViewById(R.id.btnStart);
        chrono = findViewById(R.id.chrono);
        pause = findViewById(R.id.btnPause);
        start.setOnClickListener(v -> {
            if (!started) {
                chrono.setBase(SystemClock.elapsedRealtime() + stopped);
                chrono.start();
                started = true;
                paused = false;
            }
        });

        pause.setOnClickListener(v -> {
            if (!paused && started) {
                chrono.stop();
                stopped = chrono.getBase() - SystemClock.elapsedRealtime();
                started = false;
                paused = true;
            }
        });

        workoutRV = findViewById(R.id.workoutRV);
        workoutRV.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        workoutRV.setLayoutManager(mLayoutManager);
        ParentAdapter adapter = new ParentAdapter(this, day.getExercises(), true);
        workoutRV.setAdapter(adapter);
        pause.setOnClickListener(v -> {
            if (!paused && started) {
                chrono.stop();
                stopped = chrono.getBase() - SystemClock.elapsedRealtime();
                started = false;
                paused = true;
            }
        });

        for (int ex = 0; ex < day.getExercises().size(); ex++)
            for (int setNum = 0; setNum < day.getExercises().get(ex).getSets().size(); setNum++)
                total = total + 1;

        finished = new Dialog(this);
    }

    @Override
    protected void onDestroy() {
        chrono.stop();
        super.onDestroy();
    }

    public void Celebrate() {

        Button btnOK;
        TextView quotez;
        chrono.stop();
        final CoordinatorLayout constraintLayout;
        Random rand = new Random();
        finished.setContentView(R.layout.complete);
        constraintLayout = finished.findViewById(R.id.dialogLayout);
        container = finished.findViewById(R.id.dialogLayout);
        finished.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                constraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                activeConfettiManagers.add(generateStream());
                activeConfettiManagers.add(generateStream());
                container = finished.findViewById(R.id.con);
                activeConfettiManagers.add(generateStream());

            }
        });
        btnOK = finished.findViewById(R.id.ok);
        btnOK.setOnClickListener(v -> {
            finished.dismiss();
            onBackPressed();
        });

        quotez = finished.findViewById(R.id.quote);
        int randomNum = rand.nextInt(11);
        quotez.setText(quotes[randomNum]);
        if (randomNum >= 9)
            quotez.setTextSize(12);
        else
            quotez.setTextSize(14);
        finished.show();

    }

    @Override
    public Confetto generateConfetto(Random random) {
        return new ShimmeringConfetto(
                confettoBitmaps.get(random.nextInt(confettoBitmaps.size())),
                goldLight, goldDark, 1000, random);
    }

    protected ConfettiManager generateStream() {
        return getConfettiManager().setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(50)
                .animate();
    }

    private ConfettiManager getConfettiManager() {
        final ConfettiSource confettiSource = new ConfettiSource(0, -size, container.getWidth(),
                -size);
        return new ConfettiManager(this, this, confettiSource, container)
                .setVelocityX(0, velocitySlow)
                .setVelocityY(velocityNormal, velocitySlow)
                .setInitialRotation(180, 180)
                .setRotationalAcceleration(360, 180)
                .setTargetRotationalVelocity(360);
    }
}