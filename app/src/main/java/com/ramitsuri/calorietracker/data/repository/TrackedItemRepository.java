package com.ramitsuri.calorietracker.data.repository;

import com.ramitsuri.calorietracker.AppExecutors;
import com.ramitsuri.calorietracker.data.CalorieTrackerDatabase;
import com.ramitsuri.calorietracker.entities.TrackedItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TrackedItemRepository {

    private AppExecutors mExecutors;
    private CalorieTrackerDatabase mDatabase;

    private LiveData<List<TrackedItem>> mTrackedItems;

    public TrackedItemRepository(AppExecutors appExecutors,
            CalorieTrackerDatabase database) {
        mExecutors = appExecutors;
        mDatabase = database;

        mTrackedItems = mDatabase.trackedItemDao().getAll();
    }

    public LiveData<List<TrackedItem>> getTrackedItems() {
        return mTrackedItems;
    }

    public LiveData<List<TrackedItem>> getAllUnsynced() {
        final MutableLiveData<List<TrackedItem>> trackedItems = new MutableLiveData<>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<TrackedItem> values = mDatabase.trackedItemDao().getAllUnsynced();
                trackedItems.postValue(values);
            }
        });
        return trackedItems;
    }

    public LiveData<Boolean> insertTrackedItem(final TrackedItem trackedItem) {
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long insertSuccess = mDatabase.trackedItemDao().insert(trackedItem);
                success.postValue(insertSuccess > 0);
            }
        });
        return success;
    }

    public LiveData<Boolean> insertTrackedItems(@NonNull final List<TrackedItem> trackedItems) {
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Long> insertedTrackedItems =
                        mDatabase.trackedItemDao().insertAll(trackedItems);
                success.postValue(insertedTrackedItems.size() > 0);
            }
        });
        return success;
    }

    public void updateUnsynced() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.trackedItemDao().updateUnsynced();
            }
        });
    }

    public void deleteAll() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.trackedItemDao().deleteAll();
            }
        });
    }

    public void deleteSynced() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.trackedItemDao().deleteSynced();
            }
        });
    }
}
