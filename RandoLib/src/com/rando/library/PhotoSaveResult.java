package com.rando.library;

import com.rando.library.UserInterfaces.GENERALERROR;
import com.rando.library.UserInterfaces.IPhotoSaveResult;

public class PhotoSaveResult implements IPhotoSaveResult{

	GENERALERROR mSaveResult;
	
	public PhotoSaveResult(GENERALERROR saveResult) {
		mSaveResult = saveResult;
	}
	
	@Override
	public GENERALERROR GetPhotoSaveResult(){
		return mSaveResult;
	}
	
}
