package com.rando.library.usermanager;

import java.io.File;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.usermanager.UserInterfaces.IUserGetAvatarResult;

public class UserGetAvatarResult implements IUserGetAvatarResult{

	File mAvatar;
	GENERALERROR mUserGetAvatarResult;
	
	public UserGetAvatarResult(File avatar, GENERALERROR userGetAvatarResult){
		mAvatar = avatar;
		mUserGetAvatarResult = userGetAvatarResult;
	}
	
	@Override
	public GENERALERROR userGetAvatarResult() {
		return mUserGetAvatarResult;
	}

	@Override
	public File getAvatar() {
		return mAvatar;
	}

}
