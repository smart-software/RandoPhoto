package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentSaveResult;

public class CommentSaveCallback implements ICommentSaveCallback{

	String TAG = this.getClass().getName();
	
	@Override
	public void onCommentSave(ICommentSaveResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");

		callbackText = "Result of comment save "+result.GetCommentSaveResult().toString()+ " at: " +TAG;
		callbackTextView.setText(callbackText);
	}

}
