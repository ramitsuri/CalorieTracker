package com.ramitsuri.calorietracker.data.dao;

import com.ramitsuri.calorietracker.entities.Item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getAll();

    @Insert
    List<Long> insertAll(List<Item> items);

    @Query("DELETE FROM Item")
    void deleteAll();
}
