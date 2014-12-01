package com.rando.library.randomanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveResult;

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
