package com.ramitsuri.calorietracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramitsuri.calorietracker.R;
import com.ramitsuri.calorietracker.entities.TrackedItemWrapper;
import com.ramitsuri.calorietracker.utils.FormatHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    @Nullable
    private List<TrackedItemWrapper> mTrackedItemsWrappers;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtDate, txtCalories, txtBreakdown;

        ViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.text_date);
            txtCalories = itemView.findViewById(R.id.text_calories);
            txtBreakdown = itemView.findViewById(R.id.text_breakdown);
        }

        private void bind(final TrackedItemWrapper wrapper) {
            txtDate.setText(wrapper.getDate());
            txtCalories.setText(String.format("%sCal", wrapper.getCalories()));
            txtBreakdown.setText(FormatHelper.getFormattedBreakdown(wrapper.getTrackedItems()));
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void setHistory(List<TrackedItemWrapper> trackedItemWrappers) {
        if (mTrackedItemsWrappers != null) {
            if (mTrackedItemsWrappers != trackedItemWrappers) {
                mTrackedItemsWrappers = trackedItemWrappers;
                notifyDataSetChanged();
            }
        } else {
            // first initialization
            mTrackedItemsWrappers = trackedItemWrappers;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrackedItemsWrappers != null) {
            TrackedItemWrapper wrapper = mTrackedItemsWrappers.get(position);
            holder.bind(wrapper);
        }
    }

    @Override
    public int getItemCount() {
        if (mTrackedItemsWrappers == null) {
            return 0;
        }
        return mTrackedItemsWrappers.size();
    }
}
