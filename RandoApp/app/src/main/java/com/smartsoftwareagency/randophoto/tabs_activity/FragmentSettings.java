package com.smartsoftwareagency.randophoto.tabs_activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartsoftwareagency.randophoto.R;

/**
 *
 */
public class FragmentSettings extends Fragment {
    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        return settingsView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //
    }
}
