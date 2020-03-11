package com.example.danolanater.chessclock;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StandardGameFragment extends Fragment{

    private Button whiteTimeButton, blackTimeButton, whiteIncrementButton, blackIncrementButton, startButton;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.standard_game, container, false);

        whiteTimeButton = (Button) view.findViewById(R.id.whiteTimeButton);
        blackTimeButton = (Button) view.findViewById(R.id.blackTimeButton);
        whiteIncrementButton = (Button) view.findViewById(R.id.whiteIncrementButton);
        blackIncrementButton = (Button) view.findViewById(R.id.blackIncrementButton);
        startButton = (Button) view.findViewById(R.id.startButton);

        whiteTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberDialog dialog = new NumberDialog(getContext(), whiteTimeButton);
                dialog.show();
            }
        });

        blackTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog dialog = new NumberDialog(getContext(), blackTimeButton);
                dialog.show();
            }
        });

        whiteIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog dialog = new NumberDialog(getContext(), whiteIncrementButton);
                dialog.show();
            }
        });

        blackIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog dialog = new NumberDialog(getContext(), blackIncrementButton);
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

                Intent clockActivity = new Intent(getContext(), ClockActivity.class);
                clockActivity.putExtras(b);
                startActivity(clockActivity);
            }
        });

        return view;
    }
}
