package com.ramitsuri.calorietracker.fragment;

import android.os.Bundle;

import com.ramitsuri.calorietracker.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
