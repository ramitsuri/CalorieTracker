package com.ramitsuri.calorietracker;

import android.accounts.Account;
import android.app.Application;

import com.ramitsuri.calorietracker.data.CalorieTrackerDatabase;
import com.ramitsuri.calorietracker.data.DummyData;
import com.ramitsuri.calorietracker.data.repository.ItemRepository;
import com.ramitsuri.calorietracker.data.repository.SheetRepository;
import com.ramitsuri.calorietracker.data.repository.TrackedItemRepository;
import com.ramitsuri.calorietracker.logging.ReleaseTree;

import java.util.List;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class MainApplication extends Application {

    private TrackedItemRepository mTrackedItemRepository;
    private ItemRepository mItemRepository;

    private SheetRepository mSheetRepository;

    private static MainApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        initTimber();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    public static MainApplication getInstance() {
        return sInstance;
    }

    public void initDataRepos() {
        AppExecutors appExecutors = AppExecutors.getInstance();
        CalorieTrackerDatabase database = CalorieTrackerDatabase.getInstance();

        mTrackedItemRepository = new TrackedItemRepository(appExecutors, database);

        mItemRepository = new ItemRepository(appExecutors, database);

        // TODO debug
        //mTrackedItemRepository.deleteAll();
        //mTrackedItemRepository.insertTrackedItems(DummyData.getTrackedItems());
    }

    public void initSheetRepo(@NonNull Account account,
            @NonNull String spreadsheetId,
            @NonNull List<String> scopes) {
        AppExecutors appExecutors = AppExecutors.getInstance();
        String appName = getString(R.string.app_name);

        mSheetRepository =
                new SheetRepository(this, appName, account, spreadsheetId, scopes, appExecutors);
    }

    public TrackedItemRepository getTrackedItemRepository() {
        return mTrackedItemRepository;
    }

    public ItemRepository getItemRepository() {
        return mItemRepository;
    }

    public SheetRepository getSheetRepository() {
        return mSheetRepository;
    }
}
