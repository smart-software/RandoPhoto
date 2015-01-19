package com.smartsoftwareagency.randophoto.tabs_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.smartsoftwareagency.randophoto.LoginActivity;
import com.smartsoftwareagency.randophoto.R;

/**
 *
 */
public class FragmentMain extends Fragment implements View.OnClickListener {
    private ImageButton m_btnNewRando;
    private FragmentTabHost m_mainTabHost;

    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        m_btnNewRando = (ImageButton) view.findViewById(R.id.btnNewRando);
        m_btnNewRando.setOnClickListener(this);

        // Setup tabhost
        m_mainTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        m_mainTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        m_mainTabHost.addTab(
                m_mainTabHost.newTabSpec("LastRandoTab").setIndicator("Last Rando"),
                MyLastRandoFragment.class,
                null
        );
    }

    @Override
    public void onClick(View v) {
        // New Rando Button CLick
    }
}
