package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoGetCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoPhotoGetResult;

public class PhotoGetCallback implements IPhotoGetCallback{
	String TAG = this.getClass().getName();
	@Override
	public void onIPhotoGet(IRandoPhotoGetResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");
		if (result.getPhotoGetResult()==GENERALERROR.SUCCESS & result.getPhoto()!=null){
			callbackText = "Received photo by " + result.getPhoto().GetCreatedBy()+ " with title "+ result.getPhoto().GetTitle()+ " (id: " +result.getPhoto().GetCreatedBy()+")";
			MainActivity.mImageCallback.setImageBitmap(result.getPhoto().GetPhoto());
		}
		else {
			callbackText = "Error "+result.getPhotoGetResult().toString()+ " at: " +TAG;
		}
		callbackTextView.setText(callbackText);
	}

}
