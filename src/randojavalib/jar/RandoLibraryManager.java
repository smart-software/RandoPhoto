package randojavalib.jar;

import com.parse.Parse;

import android.app.Application;

public class RandoLibraryManager {
public static void InitializeLibrary(Application app) {
Parse.enableLocalDatastore(app);
Parse.initialize(app, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B");
}
}
