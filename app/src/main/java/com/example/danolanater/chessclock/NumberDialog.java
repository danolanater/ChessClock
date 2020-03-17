package com.example.danolanater.chessclock;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

class NumberDialog extends Dialog{

    private NumberPicker hourPicker, minutePicker, secondPicker;
    private int hours, mins, secs;

    public NumberDialog(@NonNull Context context, Button button, Button buttonPair, boolean mirrorState) {
        super(context);

        this.setContentView(R.layout.number_picker_dialog);

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

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    button.setText(createButtonString());

                    if(mirrorState){
                        buttonPair.setText(button.getText());
                    }

                    dismiss();
                }
        });

        this.show();

    }

    private String createButtonString() {
        if(hourPicker.getValue() == 0) {
            if (secondPicker.getValue() == 0)
                return minutePicker.getValue() + ":00";
            else if (secondPicker.getValue() < 10)
                return minutePicker.getValue() + ":0" + secondPicker.getValue();
            else
                return minutePicker.getValue() + ":" + secondPicker.getValue();
        } else {
            return hourPicker.getValue() + "hr " + minutePicker.getValue() + "mins";
        }

    }

    private void parseButtonString(String s) {
        String[] time = s.split(":");
        if(time.length == 2) {
            mins = Integer.parseInt(time[0]);
            secs = Integer.parseInt(time[1]);
        } else {
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

        }
    }


}
