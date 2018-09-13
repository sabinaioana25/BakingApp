package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> stepList;


    public Context context;

    public StepAdapter(Context context, List<Step> stepList) {
        this.context = context;
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View stepItemLayout;
        stepItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .activity_detail_step_item, parent, false);
        return new StepViewHolder(stepItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

        Step step = stepList.get(position);
        holder.stepButton.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        Button stepButton;

        public StepViewHolder(View itemView) {
            super(itemView);
            stepButton = (Button) itemView.findViewById(R.id.detail_button_step);
        }
    }
}
