package com.ramitsuri.calorietracker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramitsuri.calorietracker.R;
import com.ramitsuri.calorietracker.adapter.ListPickerAdapter;
import com.ramitsuri.calorietracker.utils.Constants;
import com.ramitsuri.calorietracker.utils.DateHelper;
import com.ramitsuri.calorietracker.viewModel.AddDataViewModel;

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
        implements View.OnClickListener, DatePickerDialog.DatePickerDialogCallback {

    private FloatingActionButton mBtnDone;

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

        // Date
        mLayoutDate = view.findViewById(R.id.layout_date);
        mLayoutDate.setOnClickListener(this);
        mTextDate = view.findViewById(R.id.text_date);

        LocalDate localDate = DateHelper.getLocalDate(mViewModel.getDate());
        int year = DateHelper.getYearFromDate(localDate);
        int month = DateHelper.getMonthFromDate(localDate);
        int day = DateHelper.getDayFromDate(localDate);
        onDatePicked(year, month, day);
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
        mEditItemName.clearFocus();
        if (getActivity() != null) { // close keyboard
            hideKeyboardFrom(getActivity(), mEditItemName);
        }
        if (TextUtils.isEmpty(getItemName())) {
            Timber.i("Item name is empty");
            return;
        }
        mViewModel.setItemName(getItemName());
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

    @Override
    public void onDatePicked(int year, int month, int day) {
        Timber.i("Date picked: %s/%s/%s", month, day, year);
        long pickedDate = DateHelper.getDateFromYearMonthDay(year, month, day);
        mTextDate.setText(DateHelper.getFriendlyDate(pickedDate));
        mViewModel.setDate(pickedDate);
    }
}
