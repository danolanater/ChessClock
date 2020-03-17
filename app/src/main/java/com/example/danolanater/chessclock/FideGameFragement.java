package com.example.danolanater.chessclock;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class FideGameFragement extends Fragment implements RecyclerViewAdapter.OnItemClickListener {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Button addStageButton, incrementButton, delayButton;
    private RadioButton[] rb = new RadioButton[3];
    private ArrayList<Stage> stageArray = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fide_game, container, false);

        rb[0] = (RadioButton) view.findViewById(R.id.tournament1);
        rb[1] = (RadioButton) view.findViewById(R.id.tournament2);
        rb[2] = (RadioButton) view.findViewById(R.id.tournament3);
        incrementButton = (Button) view.findViewById(R.id.tournamentIncrementButton);
        delayButton = (Button) view.findViewById(R.id.tournamentDelayButton);

        for(RadioButton button : rb) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stageArray.removeAll(stageArray);
                    adapter.notifyDataSetChanged();
                    presetSelection(button);
                    adapter.notifyDataSetChanged();
                }
            });
        }

        // data to populate the RecyclerView with

        stageArray.add(new Stage(1, 40, "1:40:00"));
        stageArray.add(new Stage(2, 20, "50:00"));
        stageArray.add(new Stage(0, "15:00"));

        // set up the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setMinimumHeight(500);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(getContext(), stageArray);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                System.out.println("removing position" + position);
                stageArray.remove(position);
                resetStageNames(stageArray);
                adapter.notifyItemRemoved(position);

                for(;position < stageArray.size(); position++)
                    adapter.notifyItemChanged(position);
            }

            @Override
            public void onSetTime(int position) {
                Toast.makeText(getContext(),"onSetTimeCalled", Toast.LENGTH_LONG);
            }
        });

        addStageButton = (Button) view.findViewById(R.id.addStageButton);
        addStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stageArray.size() == 0) {
                    stageArray.add(new Stage(stageArray.size()));
                    adapter.notifyItemInserted(stageArray.size());
                } else if(stageArray.size() < 5) {
                    Stage newStage = new Stage(stageArray.size());
                    stageArray.add(stageArray.size() - 1 , newStage);
                    adapter.notifyItemInserted(stageArray.size() - 2);
                } else {
                    Toast.makeText(getContext(),"Maximum of 5 stages allowed", Toast.LENGTH_LONG).show();
                }
            }
        });

        return  view;
    }

    private void presetSelection(RadioButton button) {
        // CCA
        if(button.getId() == R.id.tournament1) {
            stageArray.add(new Stage(1, 40, "1:40:00"));
            stageArray.add(new Stage(0, "30:00"));
            incrementButton.setText("0:00");
            delayButton.setText("0:30");
            // FIDE short
        } else if (button.getId() == R.id.tournament2) {
            stageArray.add(new Stage(1, 40, "1:40:00"));
            stageArray.add(new Stage(0, "30:00"));
            incrementButton.setText("0:30");
            delayButton.setText("0:00");
            // FIDE long
        } else if (button.getId() == R.id.tournament3) {
            stageArray.add(new Stage(1, 40, "1:40:00"));
            stageArray.add(new Stage(2, 20, "50:00"));
            stageArray.add(new Stage(0, "15:00"));
            incrementButton.setText("0:30");
            delayButton.setText("0:00");
        }
    }

    @Override
    public void onDeleteClick(int position) {
        System.out.println("onDeleteClick called");
    }

    @Override
    public void onSetTime(int position) {
        System.out.println("onSetTime called");
    }

    private void resetStageNames(ArrayList<Stage> stageArray) {
        int i = 1;
        for (Stage stage:stageArray) {
            if(stage.getName() == "Stage 0") {}
            else stage.setName("Stage " + i);
            i++;
        }
    }
}
