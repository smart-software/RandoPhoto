package com.rando.library;

import com.rando.library.UserInterfaces.GENERALERROR;
import com.rando.library.UserInterfaces.IUserSaveResult;

public class UserSaveResult implements IUserSaveResult{

	GENERALERROR mError;
	
	public UserSaveResult(GENERALERROR error){
		mError = error;
	}
	
	
	@Override
	public GENERALERROR getUserSaveResult() {
		return mError;
	}

}
