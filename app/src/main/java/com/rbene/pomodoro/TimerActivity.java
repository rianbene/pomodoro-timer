package com.rbene.pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private CircularTimerView circularView;
    private TextView txtTime, txtStatus;
    private Button btnPlayPause;
    private Button btnSkipCycle;

    private PomodoroManager manager;s
    private CountDownTimer timer;
    private boolean isRunning = false;
    private long millisRemaining;
    private long totalMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        circularView = findViewById(R.id.circularView);
        txtTime = findViewById(R.id.txtTime);
        txtStatus = findViewById(R.id.txtStatus);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnSkipCycle = findViewById(R.id.btnSkipCycle);
        //Valores padrão do pomodoro
        int focus = getIntent().getIntExtra("focus", 25);
        int shortBreak = getIntent().getIntExtra("shortBreak", 5);
        int longBreak = getIntent().getIntExtra("longBreak", 15);

        manager = new PomodoroManager(focus, shortBreak, longBreak);

        startCycle();

        btnPlayPause.setOnClickListener(v -> {
            if (isRunning) pauseTimer();
            else resumeTimer();
        });

        btnSkipCycle.setOnClickListener(v -> {
            skipCycle();
        });
    }

    private void startCycle() {
        millisRemaining = manager.getCurrentDurationMinutes() * 60_000L;
        totalMillis = millisRemaining;
        txtStatus.setText(manager.getState().toString()); // o texto que aparece embaixo do timer
        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(millisRemaining, 50) {
            public void onTick(long millisUntilFinished) {
                millisRemaining = millisUntilFinished;
                updateUI();
            }
            public void onFinish() {
                manager.nextState();
                startCycle();
                pauseTimer();
                updateUI();
            }
        }.start();
        isRunning = true;
        btnPlayPause.setText(R.string.pausar);
    }

    private void pauseTimer() {
        timer.cancel();
        isRunning = false;
        btnPlayPause.setText(R.string.continuar);
    }

    //Metodo pular ciclo.
    private void skipCycle(){
        if(isRunning) {
            timer.cancel();
            manager.nextState();
            startCycle();
        }
    }

    private void resumeTimer() {
        startTimer();
    }

    private void updateUI() {
        int seconds = (int) (millisRemaining / 1000) % 60;
        int minutes = (int) (millisRemaining / 1000) / 60;
        txtTime.setText(String.format("%02d:%02d", minutes, seconds));

        float progress = 1f - (float) millisRemaining / totalMillis;
        circularView.setProgress(progress);
    }
}
