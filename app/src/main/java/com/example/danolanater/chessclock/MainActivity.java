package com.example.danolanater.chessclock;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button standardButton, fideButton;
    private boolean standardMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        standardButton = (Button) findViewById(R.id.standardButton);
        fideButton = (Button) findViewById(R.id.fideButton);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, new StandardGameFragment());
        fragmentTransaction.commit();

        standardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (standardMode) {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentLayout, new StandardGameFragment());
                    fragmentTransaction.commit();
                }
            }
        });

        fideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLayout, new FideGameFragement());
                fragmentTransaction.commit();
            }
        });

    }

}
