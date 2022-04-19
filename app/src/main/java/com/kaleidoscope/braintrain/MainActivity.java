package com.kaleidoscope.braintrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   // private TextView textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textViewTimer = findViewById(R.id.textViewTimer);
        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds++;
               // textViewTimer.setText(Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, R.string.time_end_toast, Toast.LENGTH_SHORT).show();
               // textViewTimer.setText(Integer.toString(0));
            }
        };
        timer.start();

    }


}