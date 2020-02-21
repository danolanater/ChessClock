package com.example.danolanater.chessclock;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ClockActivity extends AppCompatActivity {

    private String whiteTimeString, blackTimeString, incrementString;
    private Button whiteButton, blackButton;
    private int whiteTimeInt, blackTimeInt, incrementInt;
    private CountDownTimer whiteTimer, blackTimer;
    private int whiteMoveCount = 0, blackMoveCount = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_layout);

        whiteButton = findViewById(R.id.buttonWhite);
        blackButton = findViewById(R.id.buttonBlack);

        // this timer has no use other than to exist so initial calls to blackTimer wont throw errors
        blackTimer = new CountDownTimer(1, 0) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };

        whiteButton.setEnabled(false);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        setValues(b);



        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteTimer = new CountDownTimer(whiteTimeInt, 100) {

                    @Override
                public void onTick(long millisUntilFinished) {
                    whiteTimeInt -= 100;
                    whiteButton.setText(parseString(whiteTimeInt));

                }

                @Override
                public void onFinish() {
                    whiteButton.setClickable(false);
                    blackButton.setClickable(false);
                    whiteButton.setText("Black Wins!");
                }
            };

                blackTimer.cancel();
                blackMoveCount++;
                whiteTimer.start();

                if(incrementInt > 0 && blackMoveCount != 0){
                    blackTimeInt += incrementInt;
                    blackButton.setText(parseString(blackTimeInt));
                }
                whiteButton.setEnabled(true);
                blackButton.setEnabled(false);

            }
        });

        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    blackTimer = new CountDownTimer(blackTimeInt, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            blackTimeInt -= 100;
                            blackButton.setText(parseString(blackTimeInt));

                        }

                        @Override
                        public void onFinish() {
                            blackButton.setClickable(false);
                            whiteButton.setClickable(false);
                            blackButton.setText("White Wins!");
                        }
                    };
                    whiteTimer.cancel();
                    whiteMoveCount++;
                    blackTimer.start();
                if(incrementInt > 0){
                    whiteTimeInt += incrementInt;
                    whiteButton.setText(parseString(whiteTimeInt));
                }
                    whiteButton.setEnabled(false);
                    blackButton.setEnabled(true);

            }
        });
    }


    private void setValues(Bundle b) {
        whiteTimeString = b.getString("whiteTime");
        blackTimeString = b.getString("blackTime");
        incrementString = b.getString("increment");

        whiteButton.setText(whiteTimeString);
        blackButton.setText(blackTimeString);

        whiteTimeInt = parseTime(whiteTimeString);
        blackTimeInt = parseTime(blackTimeString);
        incrementInt = parseTime(incrementString);
        System.out.println(whiteTimeInt + " " + blackTimeInt + " " + incrementInt);
    }

    private int parseTime(String s) {
        try {
            String[] minsAndSecs = s.split(":");
            int mins = Integer.parseInt(minsAndSecs[0]);
            int secs = Integer.parseInt(minsAndSecs[1]);

            return (mins *60 + secs) * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Bundle","likely bundle parsing error, check input values??");
            return 0;
        }
    }

    private String parseString(int value) {
        int mins, sec, tenth;

        int i = value;

        mins = i / (1000*60);
        i -= mins * 1000 * 60;
        sec = i / 1000;
        i -= sec * 1000;
        tenth = i /100;

        // if longer than 1 minute remains...
        if(value > 1000 * 60) {
            if(sec >= 10) {
                if (tenth > 0)
                    return mins + ":" + sec;
                else
                    return mins + ":" + sec;
            } else {
                if (tenth > 0)
                    return mins + ":0" + sec;
                else
                    return mins + ":0" + sec;
            }
        }
        else {
            if (sec >= 10)
                return "0:" + sec + ":" + tenth;
            else
                return "0:0" + sec + ":" + tenth;
        }

    }

}
