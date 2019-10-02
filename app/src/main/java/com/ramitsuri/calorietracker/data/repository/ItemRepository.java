package com.ramitsuri.calorietracker.data.repository;

import com.ramitsuri.calorietracker.AppExecutors;
import com.ramitsuri.calorietracker.data.CalorieTrackerDatabase;
import com.ramitsuri.calorietracker.entities.Item;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ItemRepository {

    private AppExecutors mExecutors;
    private CalorieTrackerDatabase mDatabase;
    private LiveData<List<Item>> mItems;

    public ItemRepository(AppExecutors appExecutors,
            CalorieTrackerDatabase database) {
        mExecutors = appExecutors;
        mDatabase = database;

        mItems = mDatabase.itemDao().getAll();
    }

    public LiveData<List<Item>> getItems() {
        return mItems;
    }

    public LiveData<Boolean> insertItems(@NonNull final List<Item> items) {
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Long> insertedItems =
                        mDatabase.itemDao().insertAll(items);
                success.postValue(insertedItems.size() > 0);
            }
        });
        return success;
    }

    public void deleteAll() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.itemDao().deleteAll();
            }
        });
    }

    public LiveData<Boolean> setAll(final List<Item> items) {
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Long> insertedItems = mDatabase.itemDao().setAll(items);
                success.postValue(insertedItems.size() > 0);
            }
        });
        return success;
    }
}
