package com.rando.library.randomanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentSaveResult;

public class CommentSaveResult implements ICommentSaveResult {
	
	private GENERALERROR mSaveResult;

	public CommentSaveResult(GENERALERROR saveResult) {
		mSaveResult = saveResult;
	}
	
	@Override
	public GENERALERROR GetCommentSaveResult() {
		return mSaveResult;
	}
	
}
