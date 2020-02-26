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

    private String whiteTimeString, blackTimeString, whiteIncrementString, blackIncrementString;
    private Button whiteButton, blackButton;
    private int whiteTimeInt, blackTimeInt, whiteIncrementInt, blackIncrementInt;
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

                if(blackIncrementInt > 0 && blackMoveCount != 0){
                    blackTimeInt += blackIncrementInt;
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
                if(whiteIncrementInt > 0){
                    whiteTimeInt += whiteIncrementInt;
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
        whiteIncrementString = b.getString("whiteIncrement");
        blackIncrementString = b.getString("blackIncrement");

        whiteTimeInt = parseTime(whiteTimeString);
        blackTimeInt = parseTime(blackTimeString);
        whiteIncrementInt = parseTime(whiteIncrementString);
        blackIncrementInt = parseTime(blackIncrementString);

        whiteButton.setText(parseString(whiteTimeInt));
        blackButton.setText(parseString(blackTimeInt));
    }

    private int parseTime(String s) {
        if(s.split(":").length == 2) {
            String[] minsAndSecs = s.split(":");
            int mins = Integer.parseInt(minsAndSecs[0]);
            int secs = Integer.parseInt(minsAndSecs[1]);

            return (mins * 60 + secs) * 1000;
        } else {

            int hours, mins;

            if(s.charAt(3) == ' ') {
                hours = Integer.parseInt(s.substring(0,1));
                if(s.charAt(6) == 'm')
                    mins = Integer.parseInt(s.substring(4,6));
                else
                    mins = Integer.parseInt(s.substring(4,5));
            } else {
                hours = Integer.parseInt(s.substring(0,2));
                if(s.charAt(7) == 'm')
                    mins = Integer.parseInt(s.substring(5,7));
                else
                    mins = Integer.parseInt(s.substring(5,6));
            }

            return (hours * 60 + mins) * 60 * 1000;
        }

    }

    private String parseString(int value) {
        int hours, mins, sec, tenth;

        int i = value;
        hours = i / (1000 *60 * 60);
        i -= hours * 1000 * 60 * 60;
        mins = i / (1000*60);
        i -= mins * 1000 * 60;
        sec = i / 1000;
        i -= sec * 1000;
        tenth = i /100;

        // if longer than 1 hour remains...
        if(value >= 1000 * 60 * 60) {
            if(mins >= 10) {
                if(sec >= 10) {
                    if (tenth > 0)
                        return hours + ":" + mins + ":" + sec;
                    else
                        return hours + ":" + mins + ":" + sec;
                } else {
                    if (tenth > 0)
                        return hours + ":" + mins + ":0" + sec;
                    else
                        return hours + ":" + mins + ":0" + sec;
                }
            } else {
                if(sec >= 10) {
                    if (tenth > 0)
                        return hours + ":0" + mins + ":" + sec;
                    else
                        return hours + ":0" + mins + ":" + sec;
                } else {
                    if (tenth > 0)
                        return hours + ":0" + mins + ":0" + sec;
                    else
                        return hours + ":0" + mins + ":0" + sec;
                }
            }

        }

        // if longer than 1 minute remains...
        else if(value >= 1000 * 60) {
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
        // if less than a minute remains
        else {
            if (sec >= 10)
                return "0:" + sec + ":" + tenth;
            else
                return "0:0" + sec + ":" + tenth;
        }

    }

}
