package com.example.callbacks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveResult;

public class PushReceiveCallback implements IPushReceiveCallback{

	String TAG = this.getClass().getName();
	

	@Override
	public Class<? extends Activity> onPushGetActivityForIntent(Intent intent) {
		
		if (intent.getAction().equals("com.parse.push.intent.OPEN")){
			Log.d(TAG, "get Action equals com.parse.push.intent.OPEN");
		}
		
		return MainActivity.class;
	}

	@Override
	public void onPushOpen(Context context, IPushReceiveResult result) {
		Log.d(TAG, "onPushOpen fire");
		
		String callbackText;
		callbackText = "Push message: "+result.getMessage();
		
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("push", callbackText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}


