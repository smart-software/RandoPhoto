package randojavalib.jar;
// Initializes the Parse.com library
import com.parse.Parse;
import android.app.Application;

public final class RandoLibraryManager {
	public static void InitializeLibrary(Application app) {
		Parse.enableLocalDatastore(app); //enable datastore (sd and internal filesystem)
		Parse.initialize(app, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B"); // keys of RandoApp in Parse.com database. Client keys.
	}
}
