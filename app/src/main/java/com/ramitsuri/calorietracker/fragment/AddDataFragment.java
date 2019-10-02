package com.ramitsuri.calorietracker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramitsuri.calorietracker.R;
import com.ramitsuri.calorietracker.adapter.ListPickerAdapter;
import com.ramitsuri.calorietracker.entities.Item;
import com.ramitsuri.calorietracker.utils.Constants;
import com.ramitsuri.calorietracker.utils.DateHelper;
import com.ramitsuri.calorietracker.viewModel.AddDataViewModel;
import com.ramitsuri.sheetscore.consumerResponse.RangeConsumerResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import timber.log.Timber;

public class AddDataFragment extends BaseFragment
        implements View.OnClickListener, DatePickerDialog.DatePickerDialogCallback,
        View.OnLongClickListener {

    private FloatingActionButton mBtnDone;
    private ChipGroup mGroupSuggestions;

    private AddDataViewModel mViewModel;

    // Views
    private ImageView mBtnClose;
    private EditText mEditItemName;
    private ViewGroup mLayoutDate;
    private TextView mTextDate;

    public AddDataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AddDataViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AddDataViewModel.class);

        setupViews(view);
        setupRecyclerView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideActionBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        showActionBar();
    }

    private void setupViews(View view) {
        // Done
        mBtnDone = view.findViewById(R.id.btn_done);
        mBtnDone.setOnClickListener(this);

        // Close
        mBtnClose = view.findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(this);

        // EditTexts
        mEditItemName = view.findViewById(R.id.edit_text_item_name);
        mEditItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onItemNameChanged(s);
            }
        });

        // Suggestions
        mGroupSuggestions = view.findViewById(R.id.group_suggestions);

        // Date
        mLayoutDate = view.findViewById(R.id.layout_date);
        mLayoutDate.setOnClickListener(this);
        mLayoutDate.setOnLongClickListener(this);
        mTextDate = view.findViewById(R.id.text_date);

        LocalDate localDate = DateHelper.getLocalDate(mViewModel.getDate());
        int year = DateHelper.getYearFromDate(localDate);
        int month = DateHelper.getMonthFromDate(localDate);
        int day = DateHelper.getDayFromDate(localDate);
        onDatePicked(year, month, day);

        initiateGetItems();
    }

    private void initiateGetItems() {
        mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                mViewModel.setItems(items);
            }
        });
    }

    private void onItemNameChanged(Editable s) {
        if (s == null || s.length() <= 1) {
            Timber.i("Not sufficient length in item name");
            mGroupSuggestions.removeAllViews();
            return;
        }
        if (mViewModel.getItems() == null) {
            mGroupSuggestions.removeAllViews();
            return;
        }
        mGroupSuggestions.removeAllViews();
        for (Item item : mViewModel.getItems()) {
            if (!item.getItemName().toLowerCase().contains(s.toString().toLowerCase())) {
                continue;
            }
            Chip suggestion = (Chip)this.getLayoutInflater()
                    .inflate(R.layout.suggestion_chip_item, mGroupSuggestions, false);
            suggestion.setText(item.getItemName());
            suggestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        String name = compoundButton.getText().toString();
                        Timber.i("%s checked", name);
                        setEditItemName(name);
                        mGroupSuggestions.removeAllViews();
                    }
                }
            });
            mGroupSuggestions.addView(suggestion);
        }
    }

    private void setupRecyclerView(View view) {
        // Portions
        RecyclerView listPortions = view.findViewById(R.id.list_portions);
        listPortions.setLayoutManager(new StaggeredGridLayoutManager(
                getResources().getInteger(R.integer.values_grid_view_rows),
                StaggeredGridLayoutManager.HORIZONTAL));
        listPortions.setHasFixedSize(true);
        final ListPickerAdapter portionsAdapter = new ListPickerAdapter(4); // for 1.00
        portionsAdapter.setCallback(new ListPickerAdapter.ListPickerAdapterCallback() {
            @Override
            public void onItemPicked(String value) {
                onPortionPicked(value);
            }
        });
        String[] portions = getResources().getStringArray(R.array.portions);
        List<String> portionsList = Arrays.asList(portions);
        portionsAdapter.setValues(portionsList);
        listPortions.setAdapter(portionsAdapter);
    }

    private void onPortionPicked(String value) {
        if (value != null) {
            mViewModel.setPortionSize(new BigDecimal(value));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnDone) {
            handleDoneClicked();
        } else if (view == mLayoutDate) {
            handleDateClicked();
        } else if (view == mBtnClose) {
            handleCloseFragmentClicked();
        }
    }

    private void handleCloseFragmentClicked() {
        Activity activity = getActivity();
        if (activity != null) {
            ((AppCompatActivity)activity).onSupportNavigateUp();
            mEditItemName.clearFocus();
            // close keyboard
            hideKeyboardFrom(getActivity(), mEditItemName);
        } else {
            Timber.i("handleCloseFragmentClicked() -> Activity is null");
        }
    }

    private void handleDateClicked() {
        LocalDate localDate = DateHelper.getLocalDate(mViewModel.getDate());
        int year = DateHelper.getYearFromDate(localDate);
        int month = DateHelper.getMonthFromDate(localDate);
        int day = DateHelper.getDayFromDate(localDate);

        DatePickerDialog dialog = DatePickerDialog.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BundleKeys.DATE_PICKER_YEAR, year);
        bundle.putInt(Constants.BundleKeys.DATE_PICKER_MONTH, month);
        bundle.putInt(Constants.BundleKeys.DATE_PICKER_DAY, day);
        dialog.setArguments(bundle);
        dialog.setCallback(this);
        if (getActivity() != null) {
            dialog.show(getActivity().getSupportFragmentManager(), DatePickerDialog.TAG);
        } else {
            Timber.e(
                    "handleDateClicked -> getActivity() returned null when showing date picker dialog");
        }
    }

    private String getItemName() {
        if (mEditItemName != null) {
            return mEditItemName.getText().toString().trim();
        }
        return null;
    }

    private void handleDoneClicked() {
        Timber.i("Attempting save");
        if (TextUtils.isEmpty(getItemName())) {
            Timber.i("Item name is empty");
            return;
        }
        setEditItemName(getItemName());
        LiveData<Boolean> success = mViewModel.insertTrackedItem();
        success.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean insertResult) {
                if (insertResult) {
                    Timber.i("Tracked item inserted successfully");
                    goUp();
                }
            }
        });
    }

    private void setEditItemName(String itemName) {
        if (itemName == null) {
            return;
        }
        itemName = itemName.trim();
        mEditItemName.setText(itemName);
        mEditItemName.clearFocus();
        if (getActivity() != null) { // close keyboard
            hideKeyboardFrom(getActivity(), mEditItemName);
        }
        mViewModel.setItemName(itemName);
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        Timber.i("Date picked: %s/%s/%s", month, day, year);
        long pickedDate = DateHelper.getDateFromYearMonthDay(year, month, day);
        mTextDate.setText(DateHelper.getFriendlyDate(pickedDate));
        mViewModel.setDate(pickedDate);
    }

    @Override
    public boolean onLongClick(View v) {
        if (v == mLayoutDate) {
            onGetItemsRequested();
            return true;
        }
        return false;
    }

    private void onGetItemsRequested() {
        LiveData<RangeConsumerResponse> response = mViewModel.getItemsFromSheet();
        if (response == null) {
            return;
        }
        response.observe(getViewLifecycleOwner(), new Observer<RangeConsumerResponse>() {
            @Override
            public void onChanged(RangeConsumerResponse rangeConsumerResponse) {
                Timber.i(rangeConsumerResponse.toString());
                if (rangeConsumerResponse.getObjectLists() != null) {
                    onSaveItemsRequested(rangeConsumerResponse.getObjectLists());
                }
            }
        });
    }

    private void onSaveItemsRequested(List<List<Object>> objectsList) {
        LiveData<Boolean> setSuccess = mViewModel.saveSheetItems(objectsList);
        if (setSuccess != null) {
            setSuccess.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean saved) {
                    Timber.i("Item save %s", (saved ? "successful" : "unsuccessful"));
                }
            });
        }
    }
}
