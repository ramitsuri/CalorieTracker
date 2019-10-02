package com.ramitsuri.calorietracker.utils;

import com.ramitsuri.calorietracker.entities.TrackedItem;

import java.util.List;

public class FormatHelper {
    public static String getFormattedBreakdown(List<TrackedItem> trackedItems) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < trackedItems.size(); i++) {
            TrackedItem trackedItem = trackedItems.get(i);
            sb.append(trackedItem.getItemName())
                    .append(" (x")
                    .append(trackedItem.getPortionSize().stripTrailingZeros())
                    .append(")");
            if (i != trackedItems.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
