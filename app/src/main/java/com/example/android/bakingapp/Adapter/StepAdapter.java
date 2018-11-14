package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    final static String LOG_TAG = Step.class.getSimpleName();
    @SuppressWarnings("unused")
    private static final String STEP_LIST = "stepList: ";
    public final List<Step> stepList;
    public final Context context;
    final private StepClickListener stepClickListener;

    public interface StepClickListener {
        void onStepListItemClick(int clickItemIndex);
    }

    public StepAdapter(Context context, List<Step> stepList, StepClickListener stepClickListener) {
        this.context = context;
        this.stepList = stepList;
        this.stepClickListener = stepClickListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View stepItemLayout;
        stepItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .activity_detail_step_item, parent, false);
        return new StepViewHolder(stepItemLayout);
    }

    @Override
    public int getItemCount() {
        if (stepList != null) {
            return stepList.size();
        }
        Log.e(LOG_TAG, STEP_LIST + 0);
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.stepIdTextView.setText(String.valueOf(step.getStepId()));
        holder.stepShortDescTextView.setText(step.getShortDescription());
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.detail_step_id)
        TextView stepIdTextView;
        @BindView(R.id.detail_step_sh_descr)
        TextView stepShortDescTextView;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            stepClickListener.onStepListItemClick(getAdapterPosition());
        }
    }
}
