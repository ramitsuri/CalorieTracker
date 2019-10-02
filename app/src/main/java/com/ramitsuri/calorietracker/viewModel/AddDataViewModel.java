package com.ramitsuri.calorietracker.viewModel;

import android.accounts.Account;

import com.ramitsuri.calorietracker.MainApplication;
import com.ramitsuri.calorietracker.R;
import com.ramitsuri.calorietracker.data.repository.ItemRepository;
import com.ramitsuri.calorietracker.data.repository.SheetRepository;
import com.ramitsuri.calorietracker.data.repository.TrackedItemRepository;
import com.ramitsuri.calorietracker.entities.Item;
import com.ramitsuri.calorietracker.entities.TrackedItem;
import com.ramitsuri.calorietracker.utils.PrefHelper;
import com.ramitsuri.sheetscore.consumerResponse.RangeConsumerResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class AddDataViewModel extends ViewModel {
    private TrackedItemRepository mTrackedItemRepository;
    private ItemRepository mItemRepository;

    @Nullable
    private List<Item> mItems;

    private TrackedItem mTrackedItem;

    private Date mDate;

    public AddDataViewModel() {
        super();

        MainApplication.getInstance().initDataRepos();
        mTrackedItemRepository = MainApplication.getInstance().getTrackedItemRepository();
        mItemRepository = MainApplication.getInstance().getItemRepository();
        reset();
    }

    public LiveData<Boolean> insertTrackedItem() {
        TrackedItem trackedItem = mTrackedItem;
        Timber.i("Inserting ");
        reset();
        if (mItems != null) {
            for (Item item : mItems) {
                if (item.getItemName().equals(trackedItem.getItemName())) {
                    trackedItem.setCalories(item.getCalories());
                    break;
                }
            }
        }
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

    public LiveData<RangeConsumerResponse> getItemsFromSheet() {
        if (MainApplication.getInstance().getSheetRepository() == null) {
            Timber.i("Sheet repo null");

            String accountName = PrefHelper.get(MainApplication.getInstance()
                    .getString(R.string.settings_key_account_name), null);
            String accountType = PrefHelper.get(MainApplication.getInstance()
                    .getString(R.string.settings_key_account_type), null);
            String spreadsheetId = PrefHelper.get(MainApplication.getInstance()
                    .getString(R.string.settings_key_spreadsheet_id), null);
            if (accountName != null && accountType != null) {
                Account account = new Account(accountName, accountType);
                MainApplication.getInstance()
                        .initSheetRepo(account, spreadsheetId, Arrays.asList(
                                com.ramitsuri.calorietracker.constants.Constants.SCOPES));
            } else {
                Timber.w("Account is null");
                return null;
            }
        }

        SheetRepository sheetRepository = MainApplication.getInstance().getSheetRepository();
        String itemsRange = "Entities!C:D";
        return sheetRepository.getRangeData(itemsRange);
    }

    public LiveData<Boolean> saveSheetItems(@Nonnull List<List<Object>> objectsList) {
        if (mItemRepository != null) {
            List<Item> items = new ArrayList<>();
            for (List<Object> objects : objectsList) {
                if (objects.size() != 2) {
                    continue;
                }
                if (objects.get(0) == null || objects.get(1) == null) {
                    continue;
                }
                String itemName = (String)objects.get(0);
                BigDecimal calories = new BigDecimal(String.valueOf(objects.get(1)));
                Item item = new Item(itemName, calories);
                items.add(item);
            }

            mItems = items;
            return mItemRepository.setAll(items);
        }
        return null;
    }

    @Nullable
    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<Item> items) {
        mItems = items;
    }

    public LiveData<List<Item>> getItemsLiveData() {
        return mItemRepository.getItems();
    }
}
