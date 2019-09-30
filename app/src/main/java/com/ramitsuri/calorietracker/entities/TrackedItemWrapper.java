package com.ramitsuri.calorietracker.entities;

import java.math.BigDecimal;
import java.util.List;

public class TrackedItemWrapper {

    private String mDate;
    private List<TrackedItem> mTrackedItems;
    private BigDecimal mCalories;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public List<TrackedItem> getTrackedItems() {
        return mTrackedItems;
    }

    public void setTrackedItems(
            List<TrackedItem> trackedItems) {
        mTrackedItems = trackedItems;
    }

    public BigDecimal getCalories() {
        return mCalories;
    }

    public void setCalories(BigDecimal calories) {
        mCalories = calories;
    }
}
