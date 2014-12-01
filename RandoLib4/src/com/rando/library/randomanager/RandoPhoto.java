package com.rando.library.randomanager;

import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;

public class RandoPhoto implements IRandoPhoto{
	
	private String mId;
	private Date mCreatedAt;
	private String mTitle;
	private String mPhotoFileUrl;
	private Bitmap mPhotoFile;
	private int mLikesNumber;
	private String mCreatedById;
	private Date mLastLikedAt;
	private List<String> mReviewersIds;
	
	public RandoPhoto(String title, Bitmap photo,String createdById){
		mTitle = title;
		mPhotoFile = photo;
		mCreatedById = createdById;
	}
	
	public RandoPhoto(String id, Date createdAt, String title, String photo, int likesNumber,String createdById, Date lastLikedAt, List<String> reviwers){
		mId = id;
		mCreatedAt = createdAt;
		mTitle = title;
		mPhotoFileUrl = photo;
		mLikesNumber = likesNumber;
		mCreatedById = createdById;
		mLastLikedAt = lastLikedAt;
		mReviewersIds = reviwers;
	}
	
	public RandoPhoto(String id, Date createdAt, String title, Bitmap photoFile, int likesNumber,String createdById, Date lastLikedAt, List<String> reviwers){
		mId = id;
		mCreatedAt = createdAt;
		mTitle = title;
		mPhotoFile = photoFile;
		mLikesNumber = likesNumber;
		mCreatedById = createdById;
		mLastLikedAt = lastLikedAt;
		mReviewersIds = reviwers;
	}


	@Override
	public String GetRandoID() {
		return mId;
	}

	@Override
	public int GetLikesCount() {
		return mLikesNumber;
	}

	@Override
	public String GetCommentsCount() {
		//TODO
		return null;
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
	public Bitmap GetPhoto() {
		return mPhotoFile;
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
