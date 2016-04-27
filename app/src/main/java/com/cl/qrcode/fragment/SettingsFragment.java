package com.cl.qrcode.fragment;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.cl.qrcode.R;
import com.cl.qrcode.provider.Settings;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    public static final String KEY_SHARE = "share";
    public static final String KEY_FEEDBACK = "feedback";
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.settings);
        SwitchPreference ring = (SwitchPreference) findPreference(Settings.KEY_RING);
        ring.setChecked(Settings.getBoolean(getActivity(), Settings.KEY_RING));
        ring.setOnPreferenceChangeListener(this);
        SwitchPreference scanVibrate = (SwitchPreference) findPreference(Settings.KEY_SCAN_VIBRATE);
        scanVibrate.setChecked(Settings.getBoolean(getActivity(), Settings.KEY_SCAN_VIBRATE));
        scanVibrate.setOnPreferenceChangeListener(this);
        findPreference(KEY_SHARE).setOnPreferenceClickListener(this);
        findPreference(KEY_FEEDBACK).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (KEY_SHARE.equals(preference.getKey())){
            //TODO
            Toast.makeText(getActivity(),"share",Toast.LENGTH_LONG).show();
        }else if(KEY_FEEDBACK.equals(preference.getKey())){
            //TODO
            Toast.makeText(getActivity(),"feedback",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
            Settings.putBoolean(getActivity(),preference.getKey(), (Boolean) newValue);
            return true;
    }

}
