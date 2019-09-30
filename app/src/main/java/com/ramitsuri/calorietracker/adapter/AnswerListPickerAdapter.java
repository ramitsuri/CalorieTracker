package com.ramitsuri.calorietracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.ramitsuri.calorietracker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class AnswerListPickerAdapter{
        /*extends RecyclerView.Adapter<AnswerListPickerAdapter.ViewHolder> {

    @Nullable
    private List<Integer> mValues;
    @Nullable
    private ListPickerAdapterCallback mCallback;
    private int mSelectedValue;

    public interface ListPickerAdapterCallback {
        void onItemPicked(@AnswerType Integer value);
    }

    public AnswerListPickerAdapter() {
        mSelectedValue = 0;
    }

    public void setValues(@NonNull List<Integer> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public void setSelectedValue(@AnswerType int selectedValue) {
        mSelectedValue = selectedValue;
        notifyDataSetChanged();
    }

    @AnswerType
    public int getSelectedValue() {
        return mSelectedValue;
    }

    public void setCallback(@NonNull ListPickerAdapterCallback callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_picker_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            holder.bind(mValues.get(position));
        } else {
            Timber.w("mValues is null");
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        } else {
            Timber.w("mValues is null");
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Chip txtValue;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtValue = itemView.findViewById(R.id.value);
            txtValue.setOnClickListener(this);
        }

        private void bind(final Integer value) {
            switch (value) {
                case AnswerType.NO:
                    txtValue.setText(R.string.no);
                    break;

                case AnswerType.YES:
                    txtValue.setText(R.string.yes);
                    break;
            }
            if (mValues != null) {
                txtValue.setChecked(mSelectedValue == mValues.get(getAdapterPosition()));
            }
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (mValues != null) {
                    mSelectedValue = mValues.get(getAdapterPosition());
                }
                if (mCallback != null) {
                    if (mValues != null) {
                        mCallback.onItemPicked(mValues.get(getAdapterPosition()));
                    } else {
                        Timber.w("mValues is null");
                    }
                } else {
                    Timber.w("mCallback is null");
                }
                notifyDataSetChanged();
            } else {
                Timber.w("getAdapterPosition returned -1");
            }
        }
    }*/
}
