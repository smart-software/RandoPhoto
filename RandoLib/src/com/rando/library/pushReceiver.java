package com.rando.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveResult;
import com.rando.library.randomanager.PushReceiveResult;

public class pushReceiver extends ParsePushBroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		ParseAnalytics.trackAppOpenedInBackground(intent);
		super.onReceive(context, intent);
	}
	 @Override
	protected void onPushOpen(Context context, Intent intent) {
		 Bundle bundle = intent.getExtras();
		 	
			String message = bundle.getString("com.parse.Data");
			IPushReceiveResult pushReceiveResult = new PushReceiveResult(message);
			
			IPushReceiveCallback pushCallback = LibManager.pushCallbackStatic;
			pushCallback.onPushOpen(context, pushReceiveResult);
			Log.d("pushOpen", "push Open work");
	}
	 
	 @Override
	protected Class<? extends Activity> getActivity(Context context, Intent intent) {
		 IPushReceiveCallback pushCallback = LibManager.pushCallbackStatic;
		 return pushCallback.onPushGetActivityForIntent(intent);
	}

}
