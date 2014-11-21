package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetTotalNumberOfCommentsCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetTotalNumberOfCommentsResult;

public class GetTotalNumberOfCommentsCallback implements IGetTotalNumberOfCommentsCallback{

	String TAG = this.getClass().getName();
	
	@Override
	public void onGetTotalNumberOfComments(IGetTotalNumberOfCommentsResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");
		if (result.GetErrorCode()==GENERALERROR.SUCCESS){
			callbackText = "Total number of comments: " + result.getTotalNumberOfComments();
		}
		else {
			callbackText = "Error "+result.GetErrorCode().toString()+ " at: " +TAG;
		}
		callbackTextView.setText(callbackText);
	}

}
