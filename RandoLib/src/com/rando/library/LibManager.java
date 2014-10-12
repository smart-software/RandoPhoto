package com.rando.library;
/**
 * Created by SERGant on 11.10.2014.
 */

import android.app.Application;
import android.util.Log;
import com.parse.Parse;

public final class LibManager {
    private static final String LIBVERSION = "0.1";
    private static final String LIBTAG = "[RandoLib]";

    public static void InitializeLibrary(Application app)
    {
        // enable datastore (sd and internal filesystem)
        Parse.enableLocalDatastore(app.getApplicationContext());
        // keys of RandoApp in Parse.com database. Client keys.
        Parse.initialize(app, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B");
    }

    public static void LogText(String text) {
        Log.d(LIBTAG, text);
    }

    public static String GetLibraryVersion() {
        return LIBVERSION;
    }
}
