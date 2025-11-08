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
            Intent i = new Intent(this, TimerActivity.class);

            String focusText = edtFocus.getText().toString().trim();
            String shortBreakText = edtShortBreak.getText().toString().trim();
            String longBreakText = edtLongBreak.getText().toString().trim();

            if (!focusText.isEmpty()) {
                i.putExtra("focus", Integer.parseInt(focusText));
            }
            if (!shortBreakText.isEmpty()) {
                i.putExtra("shortBreak", Integer.parseInt(shortBreakText));
            }
            if (!longBreakText.isEmpty()) {
                i.putExtra("longBreak", Integer.parseInt(longBreakText));
            }

            startActivity(i);
        });
    }
}
