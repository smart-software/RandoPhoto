package com.rando.library.randomanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetLastRandoResult;

public class GetLastRandoResult implements IGetLastRandoResult{
	
	IRandoPhoto mRandoPhoto;
	GENERALERROR mError;

	public GetLastRandoResult(IRandoPhoto randoPhoto, GENERALERROR error){
		mRandoPhoto = randoPhoto;
		mError = error;
	}
	
	@Override
	public IRandoPhoto getRando() {
		return mRandoPhoto;
	}

	@Override
	public GENERALERROR getError() {
		return mError;
	}

}
