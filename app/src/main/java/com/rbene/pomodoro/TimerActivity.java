package com.rbene.pomodoro;

import android.content.Context;
import android.media.MediaPlayer;
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
    private PomodoroManager manager;
    private CountDownTimer timer;
    private boolean estaAtivo = false;
    private long milisegRestante;
    private long milisegTotal;
    private final Context thisActivity = this;

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

        iniciaCiclo();

        btnPlayPause.setOnClickListener(v -> {
            if (estaAtivo) pausarTimer();
            else iniciaTimer();
        });

        btnSkipCycle.setOnClickListener(v -> {
            pularCiclo();
        });
    }

    private void iniciaCiclo() {
        milisegRestante = manager.getCurrentDurationMinutes() * 60_000L;
        milisegTotal = milisegRestante;
        txtStatus.setText(getStateText(manager.getState())); // traduzido
        iniciaTimer();
    }
    private String getStateText(PomodoroManager.State state) {
        switch (state) {
            case FOCUS:
                return "Pomodoro";
            case SHORT_BREAK:
                return "Descanso curto";
            case LONG_BREAK:
                return "Descanso longo";
            default:
                return "";
        }
    }
    private void iniciaTimer() {
        timer = new CountDownTimer(milisegRestante, 50) {
            public void onTick(long millisUntilFinished) {
                milisegRestante = millisUntilFinished;
                atualizaInterface();
            }
            public void onFinish() {
                manager.proximoEstado();
                iniciaCiclo();
                pausarTimer();
                atualizaInterface();
                MediaPlayer mp = MediaPlayer.create(thisActivity, R.raw.beep);
                mp.start();
            }
        }.start();
        estaAtivo = true;
        btnPlayPause.setText(R.string.pausar);
    }

    private void pausarTimer() {
        timer.cancel();
        estaAtivo = false;
        btnPlayPause.setText(R.string.continuar);
    }

    //Metodo pular ciclo.
    private void pularCiclo(){
        if(estaAtivo) {
            timer.cancel();
            manager.proximoEstado();
            iniciaCiclo();
        }
    }

    private void atualizaInterface() {
        int minutos = (int) (milisegRestante / 1000) / 60;
        int segundos = (int) (milisegRestante / 1000) % 60;
        txtTime.setText(String.format("%02d:%02d", minutos, segundos));

        float progress = 1f - (float) milisegRestante / milisegTotal;
        circularView.setProgress(progress);
    }
}
