package com.rando.library.randomanager;

import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveResult;

public class PushReceiveResult implements IPushReceiveResult{

	private String mMessage;
	
	public PushReceiveResult(String message) {
		mMessage = message;
	}
	
	@Override
	public String getMessage() {
		return mMessage;
	}

}
