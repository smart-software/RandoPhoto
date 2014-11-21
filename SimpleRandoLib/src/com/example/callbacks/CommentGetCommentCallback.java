package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentGetCommentCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentGetResult;

public class CommentGetCommentCallback implements ICommentGetCommentCallback{

	String TAG = this.getClass().getName();
	
	@Override
	public void onGetComment(ICommentGetResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");
		if (result.GetErrorCode()==GENERALERROR.SUCCESS & result.GetComments()!=null){
			callbackText = "Received comments: " + result.GetComments().toString();
		}
		else {
			callbackText = "Error "+result.GetErrorCode().toString()+ " at: " +TAG;
		}
		callbackTextView.setText(callbackText);
	}

}
