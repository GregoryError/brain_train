package com.kaleidoscope.braintrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewQuestion;
    private Button button_0, button_1, button_2, button_3;
    private int answer;
    private int rightButtonNum;
    private CountDownTimer timer;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewScore = findViewById(R.id.textViewScore);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        button_0 = findViewById(R.id.button_0);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);

        generateQuestion();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.contains("win")) {
            prefs.edit().putInt("win", 0).apply();
            prefs.edit().putInt("all", 0).apply();
        }

        textViewScore.setText(Integer.toString(prefs.getInt("win", 0)) + "/" +
                Integer.toString(prefs.getInt("all", 0)));


        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds++;
                if (seconds == 10)
                    textViewTimer.setTextColor(getResources().getColor(R.color.red_for_fimer));
                if (seconds < 10)
                    textViewTimer.setText("0:0" + Integer.toString(seconds));
                else
                    textViewTimer.setText("0:" + Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, R.string.time_end_toast, Toast.LENGTH_SHORT).show();
                textViewTimer.setText(Integer.toString(0));
                prefs.edit().putInt("all", (prefs.getInt("all", 0) + 1)).apply();
                textViewScore.setText(Integer.toString(prefs.getInt("win", 0)) + "/" +
                        Integer.toString(prefs.getInt("all", 0)));
                textViewTimer.setTextColor(getResources().getColor(R.color.black));
                timer.start();
                generateQuestion();
            }
        };
        timer.start();

    }

    void generateQuestion() {
            switch ((int) (Math.random() * 2)) {
                case 0: {
                    // subtract
                    int first = (int) (Math.random() * 1000);
                    int second = (int) (Math.random() * 1000);
                    answer = first - second;
                    textViewQuestion.setText(String.format(getResources().getString(R.string.subtr_quest), Integer.toString(first),
                            Integer.toString(second)));
                    break;
                }

                case 1: {
                    // add
                    int first = (int) (Math.random() * 1000);
                    int second = (int) (Math.random() * 1000);
                    answer = first + second;
                    textViewQuestion.setText(String.format(getResources().getString(R.string.add_quest), Integer.toString(first),
                            Integer.toString(second)));
                    break;
                }

            }

            // choose button

        rightButtonNum = (int)(Math.random() * 4);

        switch (rightButtonNum) {
            case 0:
                button_0.setText(Integer.toString(answer));
                break;
            case 1:
                button_1.setText(Integer.toString(answer));
                break;
            case 2:
                button_2.setText(Integer.toString(answer));
            case 3:
                button_3.setText(Integer.toString(answer));
                break;
        }

        // generate wrongs
        int wrong = -1;
        for (int i = 0; i < 4; ++i) {
            if (i == rightButtonNum) continue;
            wrong = answer - (int)(Math.random() * 10);
            while (wrong == answer)
                wrong = answer - (int)(Math.random() * 10);
            switch (i) {
                case 0:
                    button_0.setText(Integer.toString(wrong));
                    break;
                case 1:
                    button_1.setText(Integer.toString(wrong));
                    break;
                case 2:
                    button_2.setText(Integer.toString(wrong));
                    break;
                case 3:
                    button_3.setText(Integer.toString(wrong));
                    break;
            }
        }
    }


    public void onClickAnswer(View view) {
        Button clickedButton = (Button) view;
        if (Integer.parseInt(clickedButton.getTag().toString()) == rightButtonNum) {
            prefs.edit().putInt("win", (prefs.getInt("win", 0) + 1)).apply();
            prefs.edit().putInt("all", (prefs.getInt("all", 0) + 1)).apply();
            textViewScore.setText(Integer.toString(prefs.getInt("win", 0)) + "/" +
                    Integer.toString(prefs.getInt("all", 0)));
            Toast.makeText(MainActivity.this, "RIGHT!", Toast.LENGTH_SHORT).show();
        }
        else {
            prefs.edit().putInt("all", (prefs.getInt("all", 0) + 1)).apply();
            textViewScore.setText(Integer.toString(prefs.getInt("win", 0)) + "/" +
                    Integer.toString(prefs.getInt("all", 0)));
            Toast.makeText(MainActivity.this, "WRONG!", Toast.LENGTH_SHORT).show();
        }

        textViewTimer.setTextColor(getResources().getColor(R.color.black));
        timer.start();
        generateQuestion();
    }
}


























