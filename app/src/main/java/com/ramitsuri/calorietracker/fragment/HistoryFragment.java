package com.ramitsuri.calorietracker.fragment;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramitsuri.calorietracker.MainApplication;
import com.ramitsuri.calorietracker.R;
import com.ramitsuri.calorietracker.adapter.HistoryAdapter;
import com.ramitsuri.calorietracker.constants.Constants;
import com.ramitsuri.calorietracker.entities.TrackedItemWrapper;
import com.ramitsuri.calorietracker.utils.PrefHelper;
import com.ramitsuri.calorietracker.viewModel.HistoryViewModel;
import com.ramitsuri.calorietracker.work.BackupWorker;
import com.ramitsuri.sheetscore.googleSignIn.AccountManager;
import com.ramitsuri.sheetscore.googleSignIn.SignInResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import timber.log.Timber;

public class HistoryFragment extends BaseFragment {
    private HistoryViewModel mViewModel;
    private HistoryAdapter mAdapter;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBackup = view.findViewById(R.id.btn_backup);
        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        FloatingActionButton btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HistoryFragment.this)
                        .navigate(R.id.nav_action_add_data, null);
            }
        });

        setupList(view);
        String workTag = Constants.TAG_SCHEDULED_BACKUP;
        logWorkStatus(workTag);
    }

    private void setupList(View view) {
        if (mAdapter == null) {
            mAdapter = new HistoryAdapter();
        }

        RecyclerView listExpenses = view.findViewById(R.id.list_tracked_items);
        listExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        listExpenses.setAdapter(mAdapter);

        // NOTE: Observer is triggered multiple times because of
        // 1. lifecycle state changing from inactive to active and
        // 2. data update
        mViewModel.getTrackedItems().observe(getViewLifecycleOwner(), mObserver);
    }

    private Observer<List<TrackedItemWrapper>> mObserver =
            new Observer<List<TrackedItemWrapper>>() {
                @Override
                public void onChanged(List<TrackedItemWrapper> wrappers) {
                    Timber.i("Refreshing calorie days, count is %s", wrappers.size());
                    if (mAdapter != null) {
                        mAdapter.setHistory(wrappers);
                    }
                }
            };

    private void signIn() {
        String accountName = PrefHelper.get(getString(R.string.settings_key_account_name), null);
        String accountType = PrefHelper.get(getString(R.string.settings_key_account_type), null);
        if (accountName != null && accountType != null) {
            initiateBackup(accountName, accountType, false);
            return;
        }

        AccountManager accountManager = new AccountManager();
        SignInResponse response =
                accountManager.prepareSignIn(MainApplication.getInstance(), Constants.SCOPES);
        if (response.getGoogleSignInAccount() != null) {
            Account account = response.getGoogleSignInAccount().getAccount();
            if (account != null) {
                saveAccountDetailsIfNecessary(account);
                initiateBackup(account.name, account.type, false);
            } else {
                Timber.w("SignIn() -> Account is null");
            }
        } else if (response.getGoogleSignInIntent() != null) {
            // request account access
            startActivityForResult(response.getGoogleSignInIntent(),
                    Constants.RequestCode.GOOGLE_SIGN_IN);
        }
    }

    private void initiateBackup(String accountName, String accountType, boolean periodic) {
        Timber.i("Initiating backup");
        String workTag = Constants.TAG_SCHEDULED_BACKUP;

        // Input data
        String spreadsheetId =
                PrefHelper.get(getString(R.string.settings_key_spreadsheet_id), null);
        String sheetId = PrefHelper.get(getString(R.string.settings_key_sheet_id), null);
        Data.Builder builder = new Data.Builder();
        builder.putString(Constants.Work.APP_NAME, getString(R.string.app_name));
        builder.putString(Constants.Work.ACCOUNT_NAME, accountName);
        builder.putString(Constants.Work.ACCOUNT_TYPE, accountType);
        builder.putString(Constants.Work.SPREADSHEET_ID, spreadsheetId);
        builder.putString(Constants.Work.SHEET_ID, sheetId);
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        if (periodic) {
            // Request
            PeriodicWorkRequest.Builder periodicWorkRequestBuilder =
                    new PeriodicWorkRequest.Builder(BackupWorker.class, 12, TimeUnit.HOURS)
                            .setConstraints(myConstraints)
                            .setInputData(builder.build())
                            .addTag(workTag);
            PeriodicWorkRequest request = periodicWorkRequestBuilder.build();

            // Enqueue
            WorkManager.getInstance(MainApplication.getInstance())
                    .enqueueUniquePeriodicWork(workTag,
                            ExistingPeriodicWorkPolicy.REPLACE, request);
        } else {
            OneTimeWorkRequest backupRequest = new OneTimeWorkRequest.Builder(BackupWorker.class)
                    .addTag(Constants.TAG_ONE_TIME_BACKUP)
                    .setInputData(builder.build())
                    .build();
            WorkManager.getInstance(MainApplication.getInstance()).enqueue(backupRequest);
        }

        // Status
        logWorkStatus(workTag);
    }

    private void logWorkStatus(String workTag) {
        WorkManager.getInstance(MainApplication.getInstance()).getWorkInfosByTagLiveData(workTag)
                .observe(this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(List<WorkInfo> workInfos) {
                        if (workInfos != null && !workInfos.isEmpty()) {
                            Timber.i("Work status %s", workInfos.get(0).toString());
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.GOOGLE_SIGN_IN) {
            Account account = AccountManager.getSignInAccountFromIntent(data);
            if (account != null) {
                saveAccountDetailsIfNecessary(account);
                initiateBackup(account.name, account.type, false);
            } else {
                Timber.i("Sign-in failed.");
            }
        }
    }

    private void saveAccountDetailsIfNecessary(Account account) {
        String accountName = PrefHelper.get(getString(R.string.settings_key_account_name), null);
        String accountType = PrefHelper.get(getString(R.string.settings_key_account_type), null);
        if (accountName == null && accountType == null) {
            PrefHelper.set(getString(R.string.settings_key_account_name), account.name);
            PrefHelper.set(getString(R.string.settings_key_account_type), account.type);
        }
    }
}
