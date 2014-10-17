package com.rando.library;

import com.rando.library.UserInterfaces.GENERALERROR;
import com.rando.library.UserInterfaces.IComment;
import com.rando.library.UserInterfaces.ICommentGetResult;

public class CommentGetResult implements ICommentGetResult{
	
	private IComment[] mComments;
	private GENERALERROR mResult;

	public CommentGetResult (IComment[] comments, GENERALERROR result){
		mComments = comments;
		mResult = result;
	}
	
	@Override
	public GENERALERROR GetErrorCode() {
		return mResult;
	}

	@Override
	public IComment[] GetComments() {
		return mComments;
	}
	
}
