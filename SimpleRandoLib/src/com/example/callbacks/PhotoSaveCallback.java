package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveResult;

public class PhotoSaveCallback implements IPhotoSaveCallback{
	String TAG = this.getClass().getName();
	@Override
	public void onPhotoSave(IPhotoSaveResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");
			callbackText = "Result Photo Save: "+result.GetPhotoSaveResult().toString()+ " at: " +TAG;
		callbackTextView.setText(callbackText);
	}

}
