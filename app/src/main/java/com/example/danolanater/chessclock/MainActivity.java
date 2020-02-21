package com.example.danolanater.chessclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// todo: flip the white clock, verify valid times before starting new activity

public class MainActivity extends AppCompatActivity {

    private EditText whiteTime, blackTime, increment;
    private String whiteTimeString, blackTimeString, incrementString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(retreiveViews()) {
                    Bundle b = new Bundle();

                    b.putString("whiteTime", whiteTimeString);
                    b.putString("blackTime", blackTimeString);
                    b.putString("increment", incrementString);

                    Intent clockActivity = new Intent(getApplicationContext(), ClockActivity.class);
                    clockActivity.putExtras(b);
                    startActivity(clockActivity);
                }
            }
        });

    }

    private void initializeViews() {
        whiteTime = (EditText) findViewById(R.id.whiteTime);
        blackTime = (EditText) findViewById(R.id.blackTime);
        increment = (EditText) findViewById(R.id.incrementTime);
    }

    private boolean retreiveViews() {

        whiteTimeString = whiteTime.getText().toString();
        blackTimeString = blackTime.getText().toString();
        incrementString = increment.getText().toString();

        if(isValidTimeString(whiteTimeString) && isValidTimeString(blackTimeString) && isValidTimeString(incrementString)) {
            return true;
        }
        else
            return false;
    }

    private boolean isValidTimeString(String s) {
        int secs;
        long count;
        if(s != null) {
            count = s.chars().filter(ch -> ch == ':').count();
            String[] minsAndSecs = s.split(":");
            secs = Integer.parseInt(minsAndSecs[1]);
            Log.d("shit", String.valueOf(secs));
            if (minsAndSecs.length != 2 || count != 1) {
                Toast.makeText(this, "Make sure your times are in XX:XX format", Toast.LENGTH_LONG).show();
                return false;
            } else if (secs > 59 || secs < 0) {
                Toast.makeText(this, "Make sure you have under 60 seconds in the seconds", Toast.LENGTH_LONG).show();
                return false;
            }
            else {

            }
        }
        return true;
    }

}
