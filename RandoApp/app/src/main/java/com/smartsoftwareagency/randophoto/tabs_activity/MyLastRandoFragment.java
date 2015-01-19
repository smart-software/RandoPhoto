package com.smartsoftwareagency.randophoto.tabs_activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartsoftwareagency.randophoto.R;

/**
 * Created by SERGant on 08.12.2014.
 */
public class MyLastRandoFragment extends android.support.v4.app.Fragment {

    public MyLastRandoFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.tab_my_last_rando);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_my_last_rando, container, false);
        return v;
    }
}
