package com.example.danolanater.chessclock;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

class TimeNumberDialog extends Dialog{

    private NumberPicker hourPicker, minutePicker, secondPicker;
    private int hours, mins, secs;


    public TimeNumberDialog(@NonNull Context context, Button button, Button buttonPair, boolean mirrorState) {
        super(context);

        this.setContentView(R.layout.number_picker_dialog);

        setupDialog(button);
        initializeDialog(button, buttonPair, mirrorState);

        this.show();

    }

    public TimeNumberDialog(@NonNull Context context, Button button) {
        super(context);

        this.setContentView(R.layout.number_picker_dialog);

        setupDialog(button);
        initializeDialog(button);

        this.show();
    }

    // hh:mm:ss
    private String createButtonString() {
        hours = hourPicker.getValue();
        mins = minutePicker.getValue();
        secs = secondPicker.getValue();

        if(hours > 0) {
            if(mins > 9) {
                if(secs > 9) { // ##:##:##
                    return hours + ":" + mins + ":" + secs;
                } else { // ##:##:0#
                    return hours + ":" + mins + ":0" + secs;
                }
            } else {
                if(secs > 9) { // ##:0#:##
                    return hours + ":0" + mins + ":" + secs;
                } else { // ##:0#:0#
                    return hours + ":0" + mins + ":0" + secs;
                }
            }
        } else {
            if(mins > 9) {
                if(secs > 9) { // ##:##
                    return mins + ":" + secs;
                } else { // ##:0#
                    return mins + ":0" + secs;
                }
            } else {
                if(secs > 9) { // 0#:##
                    return mins + ":" + secs;
                } else { // 0#:0#
                    return mins + ":0" + secs;
                }
            }
        }

    }

    private void parseButtonString(String s) {
        String[] time = s.split(":");
        if(time.length == 2) {
            mins = Integer.parseInt(time[0]);
            secs = Integer.parseInt(time[1]);
        } else {
            hours = Integer.parseInt(time[0]);
            mins = Integer.parseInt(time[1]);
            secs = Integer.parseInt(time[2]);
        }

    }

    private void initializeDialog(Button button, Button buttonPair, boolean mirrorState) {

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hourPicker.getValue() + minutePicker.getValue() + secondPicker.getValue() == 0) {
                    Toast.makeText(getContext(),"Please choose a valid time", Toast.LENGTH_SHORT).show();
                } else {
                    button.setText(createButtonString());

                    if(mirrorState){
                        buttonPair.setText(button.getText());
                    }

                    dismiss();
                }
            }
        });
    }

    private void initializeDialog(Button button) {

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hourPicker.getValue() + minutePicker.getValue() + secondPicker.getValue() == 0) {
                    Toast.makeText(getContext(),"Please choose a valid time", Toast.LENGTH_SHORT).show();
                } else {
                    button.setText(createButtonString());
                    dismiss();
                }
            }
        });
    }

    private void setupDialog(Button button) {
        hourPicker = (NumberPicker) this.findViewById(R.id.hourPicker);
        minutePicker = (NumberPicker) this.findViewById(R.id.minutePicker);
        secondPicker = (NumberPicker) this.findViewById(R.id.secondPicker);

        parseButtonString(button.getText().toString());

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(99);
        hourPicker.setValue(hours);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(mins);

        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        secondPicker.setValue(secs);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
