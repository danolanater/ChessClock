package com.example.danolanater.chessclock;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.sql.Time;

public class StandardGameFragment extends Fragment{

    private Button whiteTimeButton, blackTimeButton, whiteIncrementButton, blackIncrementButton, whiteDelayButton, blackDelayButton, startButton;
    private EditText p1Name, p2Name;
    private RadioGroup radioGroup;
    private RadioButton[] rb = new RadioButton[5];
    private CheckBox mirrorTimeBox, hourglassBox, stopwatchBox;
    private View view;
    private LinearLayout timeRow, incrementRow, delayRow;
    private boolean mirrorTimeSettings = true, hourglassSettings = false, stopwatchSettings = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.standard_game, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioTimeSelector);

        p1Name = (EditText) view.findViewById(R.id.p1Name);
        p2Name = (EditText) view.findViewById(R.id.p2Name);

        timeRow = (LinearLayout) view.findViewById(R.id.timeRow);
        whiteTimeButton = (Button) view.findViewById(R.id.whiteTimeButton);
        blackTimeButton = (Button) view.findViewById(R.id.blackTimeButton);

        incrementRow = (LinearLayout) view.findViewById(R.id.incrementRow);
        whiteIncrementButton = (Button) view.findViewById(R.id.whiteIncrementButton);
        blackIncrementButton = (Button) view.findViewById(R.id.blackIncrementButton);

        delayRow = (LinearLayout) view.findViewById(R.id.delayRow);
        whiteDelayButton = (Button) view.findViewById(R.id.whiteDelayButton);
        blackDelayButton = (Button) view.findViewById(R.id.blackDelayButton);

        mirrorTimeBox = (CheckBox) view.findViewById(R.id.mirrorTimeBox);
        hourglassBox = (CheckBox) view.findViewById(R.id.hourglassBox);
        stopwatchBox = (CheckBox) view.findViewById(R.id.stopwatchBox);
        startButton = (Button) view.findViewById(R.id.startButton);

        rb[0] = (RadioButton) view.findViewById(R.id.time1);
        rb[1] = (RadioButton) view.findViewById(R.id.time2);
        rb[2] = (RadioButton) view.findViewById(R.id.time3);
        rb[3] = (RadioButton) view.findViewById(R.id.time4);
        rb[4] = (RadioButton) view.findViewById(R.id.time5);


        whiteTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mirrorTimeSettings)
                    new TimeNumberDialog(getContext(), whiteTimeButton, blackTimeButton, mirrorTimeSettings, "Time");

                else
                    new TimeNumberDialog(getContext(), whiteTimeButton, blackTimeButton, mirrorTimeSettings, "White Time");
            }
        });

        blackTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mirrorTimeSettings)
                    new TimeNumberDialog(getContext(), blackTimeButton, whiteTimeButton, mirrorTimeSettings, "Time");
                else
                    new TimeNumberDialog(getContext(), blackTimeButton, whiteTimeButton, mirrorTimeSettings, "Black Time");
            }
        });

        whiteIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mirrorTimeSettings)
                    new TimeNumberDialog(getContext(), whiteIncrementButton, blackIncrementButton, mirrorTimeSettings, "Increment");
                else
                    new TimeNumberDialog(getContext(), whiteIncrementButton, blackIncrementButton, mirrorTimeSettings, "White Increment");
            }
        });

        blackIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mirrorTimeSettings)
                    new TimeNumberDialog(getContext(), blackIncrementButton, whiteIncrementButton, mirrorTimeSettings, "Increment");
                else
                    new TimeNumberDialog(getContext(), blackIncrementButton, whiteIncrementButton, mirrorTimeSettings, "Black Increment");
            }
        });

        whiteDelayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mirrorTimeSettings)
                    new TimeNumberDialog(getContext(), whiteDelayButton, blackDelayButton, mirrorTimeSettings, "Delay");
                else
                    new TimeNumberDialog(getContext(), whiteDelayButton, blackDelayButton, mirrorTimeSettings, "White Delay");
            }
        });

        blackDelayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mirrorTimeSettings)
                    new TimeNumberDialog(getContext(), blackDelayButton, whiteDelayButton, mirrorTimeSettings, "Delay");
                else
                    new TimeNumberDialog(getContext(), blackDelayButton, whiteDelayButton, mirrorTimeSettings, "Black Delay");
            }
        });

        for(RadioButton button : rb) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mirrorTimeBox.setChecked(true);
                    presetSelection(button);
                }
            });
        }

        mirrorTimeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mirrorTimeSettings = isChecked;

                if(isChecked){
                    mirrorTimes();
                }
            }
        });

        hourglassBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hourglassSettings = isChecked;
            }
        });

        stopwatchBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stopwatchSettings = isChecked;

                if(isChecked) {
                    radioGroup.setVisibility(View.GONE);
                    timeRow.setVisibility(View.GONE);
                    incrementRow.setVisibility(View.GONE);
                    delayRow.setVisibility(View.GONE);
                    mirrorTimeBox.setVisibility(View.GONE);
                    hourglassBox.setVisibility(View.GONE);
                } else {
                    radioGroup.setVisibility(View.VISIBLE);
                    timeRow.setVisibility(View.VISIBLE);
                    incrementRow.setVisibility(View.VISIBLE);
                    delayRow.setVisibility(View.VISIBLE);
                    mirrorTimeBox.setVisibility(View.VISIBLE);
                    hourglassBox.setVisibility(View.VISIBLE);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();

                b.putString("whiteName", p1Name.toString());
                b.putString("blackName", p2Name.toString());

                b.putString("whiteTime", whiteTimeButton.getText().toString());
                b.putString("blackTime", blackTimeButton.getText().toString());

                b.putString("whiteIncrement", whiteIncrementButton.getText().toString());
                b.putString("blackIncrement", blackIncrementButton.getText().toString());

                b.putString("whiteDelay", whiteDelayButton.getText().toString());
                b.putString("blackDelay", blackDelayButton.getText().toString());

                b.putBoolean("hourglassMode", hourglassSettings);
                b.putBoolean("stopwatchMode", stopwatchSettings);

                Intent clockActivity = new Intent(getContext(), ClockActivity.class);
                clockActivity.putExtras(b);
                startActivity(clockActivity);
            }
        });

        return view;
    }

    void mirrorTimes() {
        blackTimeButton.setText(whiteTimeButton.getText());
        blackIncrementButton.setText(whiteIncrementButton.getText());
        blackDelayButton.setText(whiteDelayButton.getText());
    }


    // takes in a string of # | #
    void presetSelection(RadioButton b) {
        String input = (String) b.getText();
        String[] splitput = input.split(" ");

        String minutes = splitput[0];
        String increment = splitput[2];

        whiteTimeButton.setText(minutes + ":00");
        if(Integer.parseInt(increment) > 9){
            whiteIncrementButton.setText("0:" + increment);
        } else {
            whiteIncrementButton.setText("0:0" + increment);
        }

        mirrorTimes();
    }
}
