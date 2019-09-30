package com.ramitsuri.calorietracker.data;

import com.ramitsuri.calorietracker.MainApplication;
import com.ramitsuri.calorietracker.data.converter.BigDecimalConverter;
import com.ramitsuri.calorietracker.data.dao.ItemDao;
import com.ramitsuri.calorietracker.data.dao.TrackedItemDao;
import com.ramitsuri.calorietracker.entities.Item;
import com.ramitsuri.calorietracker.entities.TrackedItem;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TrackedItem.class, Item.class}, version = 1)
@TypeConverters({BigDecimalConverter.class})
public abstract class CalorieTrackerDatabase extends RoomDatabase {

    private static volatile CalorieTrackerDatabase INSTANCE;
    private static final String DB_NAME = "calorie_tracker_db";

    public static CalorieTrackerDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (CalorieTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MainApplication.getInstance(),
                            CalorieTrackerDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TrackedItemDao trackedItemDao();

    public abstract ItemDao itemDao();
}
