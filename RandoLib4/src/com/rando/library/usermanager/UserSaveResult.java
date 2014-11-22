package com.rando.library.usermanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.usermanager.UserInterfaces.IUserSaveResult;

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
