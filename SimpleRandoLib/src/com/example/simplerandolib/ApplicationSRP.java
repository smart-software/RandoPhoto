package com.example.simplerandolib;

import com.example.callbacks.PushReceiveCallback;
import com.rando.library.LibManager;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveCallback;

import android.app.Application;

public class ApplicationSRP extends Application{

	
	@Override
	public void onCreate() {
		LibManager.InitializeLibraryLight(getApplicationContext());
		LibManager.EnablePushNotifications();
		IPushReceiveCallback callback = new PushReceiveCallback();
		LibManager.setPushCallback(callback);
		super.onCreate();
	}
}
