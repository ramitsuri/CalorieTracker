package com.ramitsuri.calorietracker.utils;

import android.util.LongSparseArray;

import com.ramitsuri.calorietracker.entities.TrackedItem;
import com.ramitsuri.calorietracker.entities.TrackedItemWrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransformationHelper {

    public static List<TrackedItemWrapper> toTrackedItemWrapperList(List<TrackedItem> input) {
        LongSparseArray<List<TrackedItem>> map = new LongSparseArray<>();
        for (TrackedItem trackedItem : input) {
            long commonDate = DateHelper.toSheetsDate(trackedItem.getDate());
            List<TrackedItem> trackedItems = map.get(commonDate);
            if (trackedItems == null) {
                trackedItems = new ArrayList<>();
            }
            trackedItems.add(trackedItem);
            map.put(commonDate, trackedItems);
        }
        List<TrackedItemWrapper> trackedItemWrappers = new ArrayList<>();
        for (int i = map.size() - 1; i >= 0; i--) {
            long date = map.keyAt(i);
            List<TrackedItem> trackedItems = map.get(date);
            if (trackedItems == null) {
                continue;
            }

            BigDecimal calories = BigDecimal.ZERO;
            /*for (TrackedItem trackedItem : trackedItems) {
                if(trackedItem)
            }*/

            TrackedItemWrapper wrapper = new TrackedItemWrapper();
            wrapper.setDate(DateHelper.getFriendlyDate(DateHelper.toDay(date)));
            wrapper.setTrackedItems(trackedItems);
            trackedItemWrappers.add(wrapper);
        }
        return trackedItemWrappers;
    }
}
