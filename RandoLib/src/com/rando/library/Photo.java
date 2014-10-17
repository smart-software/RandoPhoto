package com.rando.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rando.library.UserInterfaces.IPhoto;

public class Photo implements IPhoto{
	
	private String mId;
	private Date mCreatedAt;
	private String mTitle;
	private String mPhotoFileUrl;
	private File mPhotoFile;
	private int mLikesNumber;
	private String mCreatedById;
	private Date mLastLikedAt;
	private List<String> mReviewersIds;
	
	public Photo(String title, File photo,String createdById){
		mTitle = title;
		mPhotoFile = photo;
		mCreatedById = createdById;
	}
	
	public Photo(String id, Date createdAt, String title, String photo, int likesNumber,String createdById, Date lastLikedAt, List<String> reviwers){
		mId = id;
		mCreatedAt = createdAt;
		mTitle = title;
		mPhotoFileUrl = photo;
		mLikesNumber = likesNumber;
		mCreatedById = createdById;
		mLastLikedAt = lastLikedAt;
		mReviewersIds = reviwers;
	}

	@Override
	public String GetPhotoId() {
		return mId;
	}

	@Override
	public String GetTitle() {
		return mTitle;
	}

	@Override
	public String GetPhotoUrl() {
		return mPhotoFileUrl;
	}
	
	@Override
	public File GetPhoto() {
		return mPhotoFile;
	}

	@Override
	public int GetLikes() {
		return mLikesNumber;
	}

	@Override
	public String GetCreatedBy() {
		return mCreatedById;
	}

	@Override
	public Date GetCreatedAt() {
		return mCreatedAt;
	}

	@Override
	public Date LastLikeAt() {
		return mLastLikedAt;
	}

	@Override
	public List<String> getReviewersIds() {
		return mReviewersIds;
	}

}
