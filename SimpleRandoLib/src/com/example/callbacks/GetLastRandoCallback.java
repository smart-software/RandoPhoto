package com.example.callbacks;

import android.util.Log;
import android.widget.TextView;

import com.example.simplerandolib.MainActivity;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetLastRandoCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetLastRandoResult;

public class GetLastRandoCallback implements IGetLastRandoCallback{

	String TAG = this.getClass().getName();
	
	@Override
	public void OnGetLastRando(IGetLastRandoResult result) {
		TextView callbackTextView = MainActivity.getCallbackTExtView();
		String callbackText;
		Log.d(TAG, "wow");
		if (result.getError()==GENERALERROR.SUCCESS & result.getRando()!=null){
			callbackText = "Received photo by " + result.getRando().GetCreatedBy()+ " with title "+ result.getRando().GetTitle()+ " (id: " +result.getRando().GetCreatedBy()+")";
		}
		else {
			callbackText = "Error "+result.getError().toString()+ " at: " +TAG;
		}
		callbackTextView.setText(callbackText);
	}

}
