package com.rando.library.randomanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;
import com.rando.library.LibManager;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.ParseConstants;
import com.rando.library.randomanager.IRandoManagerInterfaces.IComment;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentGetCommentCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentGetResult;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentSaveResult;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetLastRandoCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetLastRandoResult;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetTotalNumberOfCommentsCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetTotalNumberOfCommentsResult;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoGetCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveResult;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoManager;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoPhotoGetResult;

public class RandoManager implements IRandoManager{
	
	private String mRandomUserId1;
	private String mRandomUserId2;
	private int mUserIndex2;

	@Override
	public IRandoPhoto GetLastRando(final IGetLastRandoCallback getLastRandoCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.whereEqualTo(ParseConstants.KEY_CREATED_BY, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject rando, ParseException e) {
				IGetLastRandoResult getLastRandoResult;
				if(e==null){
					int likesNumber = rando.getList(ParseConstants.KEY_LIKES_ID).size();
					List<String> listOfReviewers = rando.getList(ParseConstants.KEY_REVIEWERS);
					IRandoPhoto randoPhoto = new RandoPhoto(rando.getObjectId(), rando.getCreatedAt(),
							rando.getString(ParseConstants.KEY_TITLE), rando.getParseFile(ParseConstants.KEY_FILE).getUrl(), 
							likesNumber, rando.getString(ParseConstants.KEY_CREATED_BY), 
							rando.getDate(ParseConstants.KEY_LASTLIKEDAT), listOfReviewers);
					getLastRandoResult = new GetLastRandoResult(randoPhoto, GENERALERROR.SUCCESS);
				}
				else {
					getLastRandoResult = new GetLastRandoResult(null, LibManager.decodeError(e.getCode()));
				}
				if(getLastRandoCallback!=null) {getLastRandoCallback.OnGetLastRando(getLastRandoResult);}
			}
		});
		return null;
	}

	@Override
	public void SaveIPhoto(final IRandoPhoto photo, final IPhotoSaveCallback photoSaveCallback) {
		
		ParseQuery<ParseObject> queryPickRandomUsers = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
		queryPickRandomUsers.addAscendingOrder(ParseConstants.KEY_CREATED_AT);
		String[] excludeCurrentUser = {ParseUser.getCurrentUser().getObjectId()};
		queryPickRandomUsers.whereNotContainedIn(ParseConstants.KEY_OBJECT_ID, Arrays.asList(excludeCurrentUser));
		queryPickRandomUsers.countInBackground(new CountCallback() { // indian code alert!
			@Override
			public void done(int count, ParseException e) {
				if (e==null) {
					Random r = new Random();
					int userIndex1 = r.nextInt(count)+1;
					int userIndex2 = 1;
					
					for (int i = 0; i<100;i++){
						userIndex2 = r.nextInt(count)+1;
						if (userIndex1!=userIndex2){
							break;
						}
					}
					
					ParseQuery<ParseObject> queryGetIdRandomUsers = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
					queryGetIdRandomUsers.addAscendingOrder(ParseConstants.KEY_CREATED_AT);
					queryGetIdRandomUsers.setSkip(userIndex1-1);
					queryGetIdRandomUsers.setLimit(1);
					queryGetIdRandomUsers.findInBackground(new FindCallback<ParseObject>() {
						
						@Override
						public void done(List<ParseObject> list, ParseException e) {
							if (e==null) {
								mRandomUserId1 = list.get(0).getObjectId();
								ParseQuery<ParseObject> queryGetId2RandomUsers = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
								queryGetId2RandomUsers.addAscendingOrder(ParseConstants.KEY_CREATED_AT);
								queryGetId2RandomUsers.setSkip(mUserIndex2-1);
								queryGetId2RandomUsers.setLimit(1);
								queryGetId2RandomUsers.findInBackground(new FindCallback<ParseObject>() {
									
									@Override
									public void done(List<ParseObject> list, ParseException e) {
										if (e==null){
											mRandomUserId2 = list.get(0).getObjectId();
											simpleSavePhotoByUsersId(photo, mRandomUserId1, mRandomUserId2, photoSaveCallback);
											sendPushUser(mRandomUserId1, "You have one new Rando to review!");
											sendPushUser(mRandomUserId2, "You have one new Rando to review!");
										}
									}
								});
							}
							else {
								if (photoSaveCallback!=null ){photoSaveCallback.onPhotoSave(new PhotoSaveResult(LibManager.decodeError(e.getCode())));}
							}
						}
					});
				}
				else {
					if (photoSaveCallback!=null ){photoSaveCallback.onPhotoSave(new PhotoSaveResult(LibManager.decodeError(e.getCode())));}
				}
			}
		});
				
				
	}

	@Override
	public void SaveIComment(IComment comment,
			final ICommentSaveCallback commentSaveCallback) {
		ParseObject commentParse = new ParseObject(ParseConstants.CLASS_COMMENT);
		
		commentParse.put(ParseConstants.KEY_COMMENT_STRING, comment.GetCommentString());
		commentParse.put(ParseConstants.KEY_LOCALE, comment.GetLocale());
		commentParse.put(ParseConstants.KEY_PARENT, comment.GetParentId());
		commentParse.put(ParseConstants.KEY_CREATED_BY, ParseUser.getCurrentUser().getObjectId());
		
		commentParse.saveEventually(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				ICommentSaveResult commentSaveResult;
				if (e==null) {
					commentSaveResult = new CommentSaveResult(GENERALERROR.SUCCESS);
				}
				else {
					commentSaveResult = new CommentSaveResult(LibManager.decodeError(e.getCode()));
				}
				if (commentSaveCallback!=null) {
					commentSaveCallback.onCommentSave(commentSaveResult);
				}
			}
		});
		
	}
	
	@Override
	public void GetRecentPhotoComments(String photoId, 
			int numberOfRecentComments, final ICommentGetCommentCallback commentGetCallback) {
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
			query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
			query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
			query.setLimit(numberOfRecentComments);
			query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> list, ParseException e) {
					
					ICommentGetResult getCommentResult;
					if(e==null){
						IComment[] commentArray = new Comment[list.size()];
						int i = 0;
						IComment tempComment;
						for (ParseObject comment : list) {
							tempComment = new Comment(comment.getObjectId(), comment.getCreatedAt(), 
									comment.getString(ParseConstants.KEY_PARENT), comment.getString(ParseConstants.KEY_CREATED_BY),comment.getString(ParseConstants.KEY_COMMENT_STRING),
									comment.getString(ParseConstants.KEY_LOCALE));
							commentArray[i] = tempComment;
						}
						getCommentResult = new CommentGetResult(commentArray, GENERALERROR.SUCCESS);
					}
					else{
						getCommentResult = new CommentGetResult(null, LibManager.decodeError(e.getCode()));
					}
					
						if (commentGetCallback!=null) {
							commentGetCallback.onGetComment(getCommentResult);
						}
				}
			});
		
	}
	
	@Override
	public void GetComments(String photoId, int fromComment, int toComment,
			final ICommentGetCommentCallback commentGetCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
		query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.setSkip(fromComment-1);
		query.setLimit(toComment-fromComment);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				
				ICommentGetResult getCommentResult;
				if(e==null){
					IComment[] commentArray = new Comment[list.size()];
					int i = 0;
					IComment tempComment;
					for (ParseObject comment : list) {
						tempComment = new Comment(comment.getObjectId(), comment.getCreatedAt(), 
								comment.getString(ParseConstants.KEY_PARENT), comment.getString(ParseConstants.KEY_CREATED_BY),comment.getString(ParseConstants.KEY_COMMENT_STRING),
								comment.getString(ParseConstants.KEY_LOCALE));
						commentArray[i] = tempComment;
					}
					getCommentResult = new CommentGetResult(commentArray, GENERALERROR.SUCCESS);
				}
				else{
					getCommentResult = new CommentGetResult(null, LibManager.decodeError(e.getCode()));
				}
				
					if (commentGetCallback!=null) {
						commentGetCallback.onGetComment(getCommentResult);
					}
			}
		});
	}


	@Override
	public void GetPhotoById(String photoId, final IPhotoGetCallback photoGetResult) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.getInBackground(photoId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject photo, ParseException e) {
				IRandoPhotoGetResult getPhotoResult;
				if(e==null){
					List<String> reviwers = photo.getList(ParseConstants.KEY_REVIEWERS);
					int likesNumber = 0;
					if (photo.containsKey(ParseConstants.KEY_LIKES_ID)) {
						likesNumber = photo.getList(ParseConstants.KEY_LIKES_ID).size();
					}
					IRandoPhoto getPhoto = new RandoPhoto(photo.getObjectId(), photo.getCreatedAt(), 
							photo.getString(ParseConstants.KEY_TITLE), photo.getParseFile(ParseConstants.KEY_FILE).getUrl(),
							likesNumber, photo.getString(ParseConstants.KEY_CREATED_BY),
							photo.getDate(ParseConstants.KEY_LASTLIKEDAT), reviwers);
					getPhotoResult = new PhotoGetResult(getPhoto, GENERALERROR.SUCCESS);
				}
				else{
					getPhotoResult = new PhotoGetResult(null, LibManager.decodeError(e.getCode()));
				}
				if(photoGetResult!=null) {photoGetResult.onIPhotoGet(getPhotoResult);}
			}
		});
	}
	
	private void simpleSavePhotoByUsersId(IRandoPhoto photo, String userId1,String userId2, final IPhotoSaveCallback photoSaveCallback){
		final ParseObject photoParse = new ParseObject(ParseConstants.CLASS_PHOTO);
		photoParse.put(ParseConstants.KEY_TITLE, photo.GetTitle());
		photoParse.put(ParseConstants.KEY_CREATED_BY, photo.GetCreatedBy());
		photoParse.put(ParseConstants.KEY_REVIEWERS, getReviewersIds());
		
		final ParseFile fileParse = LibManager.fileToParseFile(photo.GetPhoto());
		fileParse.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if (e ==null) {
					photoParse.put(ParseConstants.KEY_FILE, fileParse);
					photoParse.saveEventually(new SaveCallback() {
							@Override
							public void done(ParseException e) {
								IPhotoSaveResult photoSaveResult;
								if (e==null) {
									photoSaveResult = new PhotoSaveResult(GENERALERROR.SUCCESS);
								}
								else {
									photoSaveResult = new PhotoSaveResult(LibManager.decodeError(e.getCode()));
								}
									if (photoSaveCallback!=null) {
										photoSaveCallback.onPhotoSave(photoSaveResult);
									}
							}
						});
				}
				else {
					Log.d("simpleSavePhoto", "Error code:" + e);
				}
			}
		});
		
		
		
	}
	private ArrayList<String> getReviewersIds(){
		ArrayList<String> reviewers = new ArrayList<String>();
		reviewers.add(mRandomUserId1);
		reviewers.add(mRandomUserId2);
		return reviewers;
	}
	private void sendPushUser(String userId, String message){
		ParsePush push = new ParsePush();
		push.setChannel("user_"+userId);
		push.setMessage(message);
		push.sendInBackground(new SendCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// TODO - send callback. this method is also "eventually"
			}
		});
	}

	@Override
	public void GetTotalNumberOfComments(String photoId,
			final IGetTotalNumberOfCommentsCallback callback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
		query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
		query.countInBackground(new CountCallback() {
			
			@Override
			public void done(int count, ParseException e) {
				IGetTotalNumberOfCommentsResult getTotalRandosResult = null;
				if (e==null){
					getTotalRandosResult = new GetTotalNumberOfCommentsResult(count, GENERALERROR.SUCCESS);
				}
				else {
					getTotalRandosResult = new GetTotalNumberOfCommentsResult(0, LibManager.decodeError(e.getCode()));
				}
				if (callback!=null){
					callback.onGetTotalNumberOfComments(getTotalRandosResult);
				}
			}
		});
	}


}
