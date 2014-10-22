package com.rando.library.randomanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.randomanager.IRandoManagerInterfaces.IComment;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentGetResult;

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
