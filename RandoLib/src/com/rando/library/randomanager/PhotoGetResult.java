package com.rando.library.randomanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoPhotoGetResult;

public class PhotoGetResult implements IRandoPhotoGetResult{

	GENERALERROR mSaveResult;
	IRandoPhoto mPhoto;
	
	public PhotoGetResult(IRandoPhoto photo, GENERALERROR getResult){
		mSaveResult = getResult;
		mPhoto = photo;
	}
	
	@Override
	public GENERALERROR getPhotoGetResult() {
		return mSaveResult;
	}

	@Override
	public IRandoPhoto getPhoto() {
		return mPhoto;
	}

}
