package com.ramitsuri.calorietracker.data.dao;

import com.ramitsuri.calorietracker.entities.TrackedItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TrackedItemDao {
    @Query("SELECT * FROM TrackedItem")
    LiveData<List<TrackedItem>> getAll();

    @Query("SELECT * FROM TrackedItem WHERE is_synced = 0")
    List<TrackedItem> getAllUnsynced();

    @Query("SELECT * FROM TrackedItem WHERE day = :day")
    List<TrackedItem> getForDay(int day);

    @Insert
    Long insert(TrackedItem trackedItem);

    @Insert
    List<Long> insertAll(List<TrackedItem> trackedItems);

    @Query("UPDATE TrackedItem SET is_synced = 1 WHERE is_synced = 0")
    void updateUnsynced();

    @Query("DELETE FROM TrackedItem")
    void deleteAll();

    @Query("DELETE FROM TrackedItem WHERE is_synced = 1")
    void deleteSynced();
}
