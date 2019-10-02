package com.ramitsuri.calorietracker.data;

import com.ramitsuri.calorietracker.entities.Item;
import com.ramitsuri.calorietracker.entities.TrackedItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DummyData {

    public static long[] getDates() {
        TimeZone timeZone = TimeZone.getDefault();

        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new long[] {
                /*(1566518400000L + 0 * 86400000L),
                (1566518400000L + 1 * 86400000L),
                (1566518400000L + 2 * 86400000L)*/
                calendar.getTimeInMillis() + 0 * 86400000L,
                calendar.getTimeInMillis() + 1 * 86400000L,
                calendar.getTimeInMillis() + 2 * 86400000L,
                calendar.getTimeInMillis() + 3 * 86400000L,
                calendar.getTimeInMillis() + 4 * 86400000L,
                calendar.getTimeInMillis() + 5 * 86400000L,
                calendar.getTimeInMillis() + 6 * 86400000L,
                calendar.getTimeInMillis() + 7 * 86400000L,
                calendar.getTimeInMillis() + 8 * 86400000L,
                calendar.getTimeInMillis() + 9 * 86400000L
        };
    }

    public static List<TrackedItem> getUnsynced() {
        List<TrackedItem> trackedItems = new ArrayList<>();
        for (TrackedItem trackedItem : getTrackedItems()) {
            if (!trackedItem.isSynced()) {
                trackedItems.add(trackedItem);
            }
        }

        return trackedItems;
    }

    public static List<BigDecimal> getPortions() {
        List<BigDecimal> portions = new ArrayList<>();
        portions.add(new BigDecimal("0.25"));// 0
        portions.add(new BigDecimal("0.33"));// 1
        portions.add(new BigDecimal("0.5"));// 2
        portions.add(new BigDecimal("0.75"));// 3
        portions.add(new BigDecimal("1"));// 4
        portions.add(new BigDecimal("2"));// 5
        portions.add(new BigDecimal("3"));// 6
        portions.add(new BigDecimal("4"));// 7

        return portions;
    }

    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Dal Makhani", new BigDecimal("120.00")));//0
        items.add(new Item("Chicken Tikka Masala", new BigDecimal("100.00")));//1
        items.add(new Item("Pav Bhaji", new BigDecimal("150.00")));//2
        items.add(new Item("Hot Dog", new BigDecimal("25.00")));//3
        items.add(new Item("Apple", new BigDecimal("50.00")));//4
        items.add(new Item("Smoothie", new BigDecimal("75.50")));//5
        items.add(new Item("Aloo Gobhi", new BigDecimal("80.00")));//6
        items.add(new Item("Pizza", new BigDecimal("200.00")));//7
        items.add(new Item("Beer", new BigDecimal("20.00")));//8
        items.add(new Item("Ice Cream", new BigDecimal("20.00")));//9

        return items;
    }

    public static List<TrackedItem> getTrackedItems() {
        List<TrackedItem> trackedItems = new ArrayList<>();

        TrackedItem trackedItem;

        // Day 1
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[0]);
        trackedItem.setItemName(getItems().get(0).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[0]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(1));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[0]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[0]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[0]);
        trackedItem.setItemName(getItems().get(9).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 2
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[1]);
        trackedItem.setItemName(getItems().get(1).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[1]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[1]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[1]);
        trackedItem.setItemName(getItems().get(4).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[1]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 3
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[2]);
        trackedItem.setItemName(getItems().get(6).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[2]);
        trackedItem.setItemName(getItems().get(7).getItemName());
        trackedItem.setPortionSize(getPortions().get(1));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[2]);
        trackedItem.setItemName(getItems().get(8).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[2]);
        trackedItem.setItemName(getItems().get(9).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[2]);
        trackedItem.setItemName(getItems().get(9).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 4
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[3]);
        trackedItem.setItemName(getItems().get(8).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[3]);
        trackedItem.setItemName(getItems().get(7).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[3]);
        trackedItem.setItemName(getItems().get(6).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[3]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[3]);
        trackedItem.setItemName(getItems().get(4).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 5
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[4]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[4]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(1));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[4]);
        trackedItem.setItemName(getItems().get(1).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[4]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[4]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 6
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[5]);
        trackedItem.setItemName(getItems().get(4).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[5]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[5]);
        trackedItem.setItemName(getItems().get(9).getItemName());
        trackedItem.setPortionSize(getPortions().get(1));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[5]);
        trackedItem.setItemName(getItems().get(8).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[5]);
        trackedItem.setItemName(getItems().get(7).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 7
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[6]);
        trackedItem.setItemName(getItems().get(6).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[6]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[6]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[6]);
        trackedItem.setItemName(getItems().get(7).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[6]);
        trackedItem.setItemName(getItems().get(4).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 8
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[7]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(1));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[7]);
        trackedItem.setItemName(getItems().get(1).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[7]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[7]);
        trackedItem.setItemName(getItems().get(4).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[7]);
        trackedItem.setItemName(getItems().get(3).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 9
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[8]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(4));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[8]);
        trackedItem.setItemName(getItems().get(1).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[8]);
        trackedItem.setItemName(getItems().get(4).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[8]);
        trackedItem.setItemName(getItems().get(9).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[8]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(2));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        // Day 10
        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[9]);
        trackedItem.setItemName(getItems().get(2).getItemName());
        trackedItem.setPortionSize(getPortions().get(6));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[9]);
        trackedItem.setItemName(getItems().get(6).getItemName());
        trackedItem.setPortionSize(getPortions().get(1));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[9]);
        trackedItem.setItemName(getItems().get(1).getItemName());
        trackedItem.setPortionSize(getPortions().get(7));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[9]);
        trackedItem.setItemName(getItems().get(7).getItemName());
        trackedItem.setPortionSize(getPortions().get(5));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        trackedItem = new TrackedItem();
          trackedItem.setDate(getDates()[9]);
        trackedItem.setItemName(getItems().get(5).getItemName());
        trackedItem.setPortionSize(getPortions().get(3));
        trackedItem.setIsSynced(false);
        trackedItems.add(trackedItem);

        return trackedItems;
    }
}
