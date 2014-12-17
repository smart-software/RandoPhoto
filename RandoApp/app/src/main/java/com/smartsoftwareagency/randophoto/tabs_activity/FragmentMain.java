package com.smartsoftwareagency.randophoto.tabs_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        TabHost tabHost =(TabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = null;

        // Setup My Last Rando tab
        tabSpec = tabHost.newTabSpec("last_rando_tab");
        tabSpec.setIndicator(getString(R.string.last_rando_tab_text));
        tabSpec.setContent(new Intent(view.getContext(), MyLastRandoActivity.class));
        tabHost.addTab(tabSpec);
/*
        // Setup New Rando message
        tabSpec = tabHost.newTabSpec("new_rando_tab");
        tabSpec.setIndicator(getString(R.string.rando_message_tab_text));
        //tabSpec.setContent();
        tabHost.addTab(tabSpec);

        // Setup Most popular randos tab
        tabSpec = tabHost.newTabSpec("top_randos_tab");
        tabSpec.setIndicator(getString(R.string.top_randos_tab_text));
        //tabSpec.setContent();
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        */
    }

    @Override
    public void onClick(View v) {
        // New Rando Button CLick
    }
}
