package com.ramitsuri.calorietracker.entities;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrackedItem {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "day")
    private long mDate;

    @ColumnInfo(name = "item_name")
    private String mItemName;

    @ColumnInfo(name = "portion_size")
    private BigDecimal mPortionSize;

    @ColumnInfo(name = "is_synced")
    private boolean mIsSynced;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String text) {
        mItemName = text;
    }

    public BigDecimal getPortionSize() {
        return mPortionSize;
    }

    public void setPortionSize(BigDecimal portionSize) {
        mPortionSize = portionSize;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public boolean isSynced() {
        return mIsSynced;
    }

    public void setIsSynced(boolean synced) {
        mIsSynced = synced;
    }

    @NotNull
    @Override
    public String toString() {
        return "TrackedItem{" +
                "mDate=" + new Date(mDate) +
                ", mItemName='" + mItemName + '\'' +
                ", mPortionSize=" + mPortionSize +
                "}\n";
    }
}
