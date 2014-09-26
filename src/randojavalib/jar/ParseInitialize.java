package randojavalib.jar;

import android.app.Application;

import com.parse.Parse;

public class ParseInitialize extends Application{
	
	@Override
	public void onCreate() {
	super.onCreate();
	Parse.enableLocalDatastore(this);
	Parse.initialize(this, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B");

}}
	
