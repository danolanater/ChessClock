package com.example.danolanater.chessclock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Clock;

// todo: create intent for new game, come up with other game modes

public class MainActivity extends AppCompatActivity {

    private Button modeButton, whiteTimeButton, blackTimeButton, whiteIncrementButton, blackIncrementButton, startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modeButton = (Button) findViewById(R.id.modeButton);
        whiteTimeButton = (Button) findViewById(R.id.whiteTimeButton);
        blackTimeButton = (Button) findViewById(R.id.blackTimeButton);
        whiteIncrementButton = (Button) findViewById(R.id.whiteIncrementButton);
        blackIncrementButton = (Button) findViewById(R.id.blackIncrementButton);
        startButton = (Button) findViewById(R.id.startButton);

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        whiteTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberDialog dialog = new NumberDialog(MainActivity.this, whiteTimeButton);
                dialog.show();
            }
        });

        blackTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog dialog = new NumberDialog(MainActivity.this, blackTimeButton);
                dialog.show();
            }
        });

        whiteIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog dialog = new NumberDialog(MainActivity.this, whiteIncrementButton);
                dialog.show();
            }
        });

        blackIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog dialog = new NumberDialog(MainActivity.this, blackIncrementButton);
                dialog.show();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("whiteTime", whiteTimeButton.getText().toString());
                b.putString("blackTime", blackTimeButton.getText().toString());
                b.putString("whiteIncrement", whiteIncrementButton.getText().toString());
                b.putString("blackIncrement", blackIncrementButton.getText().toString());

                Intent clockActivity = new Intent(getApplicationContext(), ClockActivity.class);
                clockActivity.putExtras(b);
                startActivity(clockActivity);
            }
        });
    }

}
