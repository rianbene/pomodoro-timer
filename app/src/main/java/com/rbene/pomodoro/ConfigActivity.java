package com.rbene.pomodoro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    private EditText edtFocus, edtShortBreak, edtLongBreak;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        edtFocus = findViewById(R.id.edtFocus);
        edtShortBreak = findViewById(R.id.edtShortBreak);
        edtLongBreak = findViewById(R.id.edtLongBreak);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            int focus = Integer.parseInt(edtFocus.getText().toString());
            int shortBreak = Integer.parseInt(edtShortBreak.getText().toString());
            int longBreak = Integer.parseInt(edtLongBreak.getText().toString());

            Intent i = new Intent(this, TimerActivity.class);
            i.putExtra("focus", focus);
            i.putExtra("shortBreak", shortBreak);
            i.putExtra("longBreak", longBreak);
            startActivity(i);
        });
    }
}
