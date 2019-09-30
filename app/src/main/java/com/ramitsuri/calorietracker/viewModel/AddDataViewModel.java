package com.ramitsuri.calorietracker.viewModel;

import com.ramitsuri.calorietracker.MainApplication;
import com.ramitsuri.calorietracker.data.repository.ItemRepository;
import com.ramitsuri.calorietracker.data.repository.TrackedItemRepository;
import com.ramitsuri.calorietracker.entities.Item;
import com.ramitsuri.calorietracker.entities.TrackedItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class AddDataViewModel extends ViewModel {
    private ItemRepository mItemRepository;
    private TrackedItemRepository mTrackedItemRepository;

    private List<Item> mItems;

    private TrackedItem mTrackedItem;

    private Date mDate;

    public AddDataViewModel() {
        super();

        MainApplication.getInstance().initDataRepos();
        mItemRepository = MainApplication.getInstance().getItemRepository();
        mTrackedItemRepository = MainApplication.getInstance().getTrackedItemRepository();

        reset();
    }

    public LiveData<Boolean> insertTrackedItem() {
        TrackedItem trackedItem = mTrackedItem;
        Timber.i("Inserting ");
        reset();
        return mTrackedItemRepository.insertTrackedItem(trackedItem);
    }

    public void setDate(long date) {
        mTrackedItem.setDate(date);
    }

    public void setItemName(String name) {
        mTrackedItem.setItemName(name);
    }

    public void setPortionSize(BigDecimal portionSize) {
        mTrackedItem.setPortionSize(portionSize);
    }

    public Date getDate() {
        return mDate;
    }

    private void reset() {
        mTrackedItem = new TrackedItem();
        mDate = new Date();
        mTrackedItem.setPortionSize(BigDecimal.ONE);
    }
}
