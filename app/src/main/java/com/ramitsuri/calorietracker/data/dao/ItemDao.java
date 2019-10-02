package com.ramitsuri.calorietracker.data.dao;

import com.ramitsuri.calorietracker.entities.Item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class ItemDao {
    @Query("SELECT * FROM Item")
    public abstract LiveData<List<Item>> getAll();

    @Transaction
    public List<Long> setAll(List<Item> items) {
        deleteAll();
        return insertAll(items);
    }

    @Insert
    public abstract List<Long> insertAll(List<Item> items);

    @Query("DELETE FROM Item")
    public abstract void deleteAll();
}
