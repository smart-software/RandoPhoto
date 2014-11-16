package com.rando.library.randomanager;

import java.util.Date;

import com.rando.library.randomanager.IRandoManagerInterfaces.IComment;

public class Comment implements IComment{
	
	private String mId;
	private Date mCreatedAt;
	private String mParentId;
	private String mCommentString;
	private String mLocale;
	private String mCreatedBy;
	
	public Comment(String parentId, String createdBy,String commentString, String locale){
		mParentId = parentId;
		mCommentString = commentString;
		mLocale = locale;
		mCreatedBy = createdBy;
	}
	
	public Comment(String id, Date createdAt, String parentId, String createdBy,String commentString, String locale){
		mId = id;
		mCreatedAt = createdAt;
		mParentId = parentId;
		mCommentString = commentString;
		mLocale = locale;
		mCreatedBy = createdBy;
	}

	@Override
	public String GetCommentId() {
		return mId;
	}

	@Override
	public Date GetCreatedAt() {
		return mCreatedAt;
	}

	@Override
	public String GetParentId() {
		return mParentId;
	}

	@Override
	public String GetCommentString() {
		return mCommentString;
	}

	@Override
	public String GetLocale() {
		return mLocale;
	}

	@Override
	public String GetCreatedBy() {
		return mCreatedBy;
	}

}
