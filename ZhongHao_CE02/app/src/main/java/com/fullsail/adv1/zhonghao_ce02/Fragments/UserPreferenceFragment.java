// Hao Zhong
// AD1 - 202111
// UserPreferenceFragment.java
package com.fullsail.adv1.zhonghao_ce02.Fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.fullsail.adv1.zhonghao_ce02.DataModel.DataBaseHelper;
import com.fullsail.adv1.zhonghao_ce02.R;

public class UserPreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private static final String PREFERENCE_DATEFORMAT_KEY = "date_format_preference";
    private static final String PREFERENCE_BUTTON_KEY = "button_preference";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListPreference datePref = findPreference(PREFERENCE_DATEFORMAT_KEY);
        if (datePref != null) {
            datePref.setOnPreferenceChangeListener(this);
        }

        Preference deletePref = findPreference(PREFERENCE_BUTTON_KEY);
        if (deletePref != null) {
            deletePref.setOnPreferenceClickListener(this);
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage(R.string.warning_delete);
        alert.setNegativeButton("Cancel", null);

        alert.setPositiveButton("Yes", (dialog, id) -> {
            DataBaseHelper database = DataBaseHelper.getInstance(getContext());
            database.deleteAll();
            Toast.makeText(getContext(), R.string.confirmation_deleted, Toast.LENGTH_SHORT).show();
        });

        alert.show();

        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SharedPreferences dPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor edit = dPrefs.edit();
        edit.putString(PREFERENCE_DATEFORMAT_KEY, newValue.toString());
        edit.apply();

        return false;
    }
}
