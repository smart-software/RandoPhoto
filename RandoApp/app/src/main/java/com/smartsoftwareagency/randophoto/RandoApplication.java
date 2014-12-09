package com.smartsoftwareagency.randophoto;

import android.app.Application;
import android.content.Context;
import com.rando.library.LibManager;

/**
 * Created by SERGant on 09.12.2014.
 */
public class RandoApplication extends Application {

    @Override
    public void onCreate() {
        Context appContext = this.getApplicationContext();
        LibManager.InitializeLibraryLight(appContext);
    }
}
