package com.rando.library;

import com.rando.library.UserInterfaces.GENERALERROR;
import com.rando.library.UserInterfaces.ICommentSaveResult;

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
