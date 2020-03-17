package com.example.danolanater.chessclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Stage> stages;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onSetTime(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    RecyclerViewAdapter(Context context, ArrayList<Stage> stages) {
        this.mInflater = LayoutInflater.from(context);
        this.stages = stages;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        int lastItem = getItemCount() - 1;
        if(position == lastItem)
            viewType = 0;
        else
            viewType = 1;
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 0) {
            return new ViewHolder2(mInflater.inflate(R.layout.final_stage_layout, parent,false), listener);
        } else {
            return new ViewHolder(mInflater.inflate(R.layout.stage_layout, parent,false), listener, stages.get(viewType));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Stage stage = stages.get(position);

        if(holder.getItemViewType() == 0) {

            ViewHolder2 vh2 = (ViewHolder2) holder;

            vh2.finalTimeButton.setText(stage.getTime());

            vh2.finalTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = vh2.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSetTime(position);
                        }
                    }
                }
            });
        } else {

            ViewHolder vh = (ViewHolder) holder;

            vh.stageNameText.setText(stage.getName());
            vh.moveButton.setText("" + stage.getMoves());
            vh.timeButton.setText(stage.getTime());

            vh.moveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    new MoveDialog(v.getContext(), vh.moveButton);
                }
            });

            vh.timeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    new TimeNumberDialog(v.getContext(), vh.timeButton);
                }
            });

            vh.deleteImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = vh.getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return stages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stageNameText;
        Button moveButton;
        Button timeButton;
        ImageView deleteImage;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener, Stage stage) {
            super(itemView);
            stageNameText = itemView.findViewById(R.id.stageName);
            moveButton = itemView.findViewById(R.id.moveButton);
            timeButton = itemView.findViewById(R.id.timeButton);
            deleteImage = itemView.findViewById(R.id.deleteStageButton);

        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        private Button finalTimeButton;

        public ViewHolder2(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            finalTimeButton = (Button)  itemView.findViewById(R.id.finalTimeButton);
        }
    }
}
