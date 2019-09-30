package com.ramitsuri.calorietracker.viewModel;

import com.ramitsuri.calorietracker.MainApplication;
import com.ramitsuri.calorietracker.data.repository.TrackedItemRepository;
import com.ramitsuri.calorietracker.entities.TrackedItem;
import com.ramitsuri.calorietracker.entities.TrackedItemWrapper;
import com.ramitsuri.calorietracker.utils.TransformationHelper;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {
    private TrackedItemRepository mTrackedItemRepository;
    private LiveData<List<TrackedItemWrapper>> mTrackedItems;

    public HistoryViewModel() {
        super();

        MainApplication.getInstance().initDataRepos();
        mTrackedItemRepository = MainApplication.getInstance().getTrackedItemRepository();

        mTrackedItems = Transformations.map(mTrackedItemRepository.getTrackedItems(),
                new Function<List<TrackedItem>, List<TrackedItemWrapper>>() {
                    @Override
                    public List<TrackedItemWrapper> apply(List<TrackedItem> input) {
                        return TransformationHelper.toTrackedItemWrapperList(input);
                    }
                });
    }

    public LiveData<List<TrackedItemWrapper>> getTrackedItems() {
        return mTrackedItems;
    }

    public TrackedItemRepository getTrackedItemRepository() {
        return mTrackedItemRepository;
    }
}
