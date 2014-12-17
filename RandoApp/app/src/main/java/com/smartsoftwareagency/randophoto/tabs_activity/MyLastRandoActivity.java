package com.smartsoftwareagency.randophoto.tabs_activity;

import android.app.Activity;
import android.os.Bundle;

import com.smartsoftwareagency.randophoto.R;

/**
 * Created by SERGant on 08.12.2014.
 */
public class MyLastRandoActivity extends Activity {

    public MyLastRandoActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_my_last_rando);
    }
}
