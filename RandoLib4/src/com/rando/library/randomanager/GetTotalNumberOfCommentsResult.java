package com.rando.library.randomanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetTotalNumberOfCommentsResult;

public class GetTotalNumberOfCommentsResult implements IGetTotalNumberOfCommentsResult{

	int mTotalComments;
	GENERALERROR mError;
	
	public GetTotalNumberOfCommentsResult(int totalComments, GENERALERROR error) {
		mTotalComments = totalComments;
		mError = error;
	}
	
	@Override
	public int getTotalNumberOfComments() {
		return mTotalComments;
	}

	@Override
	public GENERALERROR GetErrorCode() {
		return mError;
	}

}
