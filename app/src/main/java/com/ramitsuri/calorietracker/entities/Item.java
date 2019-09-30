package com.ramitsuri.calorietracker.entities;

import java.math.BigDecimal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "item_name")
    private String mItemName;

    @ColumnInfo(name = "calories")
    private BigDecimal mCalories;

    public Item(String itemName, BigDecimal calories) {
        mItemName = itemName;
        mCalories = calories;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public BigDecimal getCalories() {
        return mCalories;
    }

    public void setCalories(BigDecimal calories) {
        mCalories = calories;
    }
}
