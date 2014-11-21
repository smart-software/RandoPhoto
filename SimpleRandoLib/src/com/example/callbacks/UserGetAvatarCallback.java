package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.usermanager.UserInterfaces.IUserGetAvatarCallback;
import com.rando.library.usermanager.UserInterfaces.IUserGetAvatarResult;

public class UserGetAvatarCallback implements IUserGetAvatarCallback{

	String TAG = this.getClass().getName();

	@Override
	public void OnUserGetAvatar(IUserGetAvatarResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");
		if(result == null) {Log.d(TAG, "result null");}
		if (result.userGetAvatarResult()==GENERALERROR.SUCCESS){
			callbackText = "Avatar received. Filename: " + result.getAvatar().getName();
		}
		else {
			callbackText = "Error "+result.userGetAvatarResult().toString()+ " at: " +TAG;
		}
		callbackTextView.setText(callbackText);
	}

}
