package com.rando.library.usermanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.usermanager.UserInterfaces.IUser;
import com.rando.library.usermanager.UserInterfaces.IUserGetByIdResult;

public class UserGetByIdResult implements IUserGetByIdResult{
	
	IUser mUser;
	GENERALERROR mResult;
	
	public UserGetByIdResult(IUser user, GENERALERROR result) {
		mUser = user;
		mResult = result;
	}
	

	@Override
	public IUser getUser() {
		return mUser;
	}

	@Override
	public GENERALERROR getUserByIdResult() {
		return mResult;
	}

}
