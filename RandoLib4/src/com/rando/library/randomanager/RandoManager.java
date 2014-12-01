package com.rando.library.randomanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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
import com.rando.library.randomanager.IRandoManagerInterfaces.ILikePhotoCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoGetCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveResult;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoManager;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoPhotoGetResult;

public class RandoManager implements IRandoManager {


	@Override
	public void GetLastRando(final IGetLastRandoCallback getLastRandoCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.whereEqualTo(ParseConstants.KEY_CREATED_BY, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			@Override
			public void done(final ParseObject rando, ParseException e) {
				IGetLastRandoResult getLastRandoResult;
				if (e == null) {
					int likesNumber = getLikes(rando);
					final List<String> listOfReviewers = rando.getList(ParseConstants.KEY_REVIEWERS);
					ParseFile file = rando.getParseFile(ParseConstants.KEY_FILE);
					IRandoPhoto randoPhoto = null;
					if(file.isDataAvailable()){ //indian code alert TODO
						byte[] byteData = null;
						try {
							byteData = file.getData();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						randoPhoto = newFullRando(rando, likesNumber, listOfReviewers, byteData);
					}
					else {
						final int  likesNumberFinal = likesNumber; //indian code alert TODO
						file.getDataInBackground(new GetDataCallback() {
							
							@Override
							public void done(byte[] byteData, ParseException e) {
								IGetLastRandoResult getLastRandoResult;
								if (e==null){
									IRandoPhoto randoPhoto = newFullRando(rando, likesNumberFinal, listOfReviewers, byteData);
									getLastRandoResult = new GetLastRandoResult(randoPhoto, GENERALERROR.SUCCESS);
								}
								else {
									getLastRandoResult = new GetLastRandoResult(null, LibManager.decodeError(e.getCode()));
								}
								if (getLastRandoCallback != null) {
									getLastRandoCallback.OnGetLastRando(getLastRandoResult);
								}
							}
						});
					}
					getLastRandoResult = new GetLastRandoResult(randoPhoto, GENERALERROR.SUCCESS);
				} else {
					getLastRandoResult = new GetLastRandoResult(null, LibManager.decodeError(e.getCode()));
				}
				if (getLastRandoCallback != null) {
					getLastRandoCallback.OnGetLastRando(getLastRandoResult);
				}
			}



			
		}); 
	}

	@Override
	public void SaveIPhoto(final Bitmap photo, final IPhotoSaveCallback photoSaveCallback) {
		simpleSavePhotoByUsersId(photo, photoSaveCallback);

		/*HashMap<String, Object> params = new HashMap<String, Object>(); //empty HashMap, only for compatibility with "callFunctionInBackground"
		ParseCloud.callFunctionInBackground("pick2RandomUsers", params, new FunctionCallback<HashMap<String, Object>>() {

			@Override
			public void done(HashMap<String, Object> usersArray, ParseException e) {
						Log.d("HashMap", (String) usersArray.get("userId1"));
						simpleSavePhotoByUsersId(photo, usersArray.get("userId1").toString(),  usersArray.get("userId2").toString(), photoSaveCallback);
			
			}
		});*/

	}

	@Override
	public void SaveIComment(IComment comment, final ICommentSaveCallback commentSaveCallback) {
		ParseObject commentParse = new ParseObject(ParseConstants.CLASS_COMMENT);

		commentParse.put(ParseConstants.KEY_COMMENT_STRING, comment.GetCommentString());
		commentParse.put(ParseConstants.KEY_LOCALE, comment.GetLocale());
		commentParse.put(ParseConstants.KEY_PARENT, comment.GetParentId());
		commentParse.put(ParseConstants.KEY_CREATED_BY, ParseUser.getCurrentUser().getObjectId());

		commentParse.saveEventually(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				ICommentSaveResult commentSaveResult;
				if (e == null) {
					commentSaveResult = new CommentSaveResult(GENERALERROR.SUCCESS);
				} else {
					commentSaveResult = new CommentSaveResult(LibManager.decodeError(e.getCode()));
				}
				if (commentSaveCallback != null) {
					commentSaveCallback.onCommentSave(commentSaveResult);
				}
			}
		});

	}

	@Override
	public void GetRecentPhotoComments(String photoId, int numberOfRecentComments, final ICommentGetCommentCallback commentGetCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.setLimit(numberOfRecentComments);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException e) {

				ICommentGetResult getCommentResult;
				if (e == null) {
					IComment[] commentArray = new Comment[list.size()];
					int i = 0;
					IComment tempComment;
					for (ParseObject comment : list) {
						tempComment = new Comment(comment.getObjectId(), comment.getCreatedAt(), comment.getString(ParseConstants.KEY_PARENT),
								comment.getString(ParseConstants.KEY_CREATED_BY), comment.getString(ParseConstants.KEY_COMMENT_STRING), comment
										.getString(ParseConstants.KEY_LOCALE));
						commentArray[i] = tempComment;
					}
					getCommentResult = new CommentGetResult(commentArray, GENERALERROR.SUCCESS);
				} else {
					getCommentResult = new CommentGetResult(null, LibManager.decodeError(e.getCode()));
				}

				if (commentGetCallback != null) {
					commentGetCallback.onGetComment(getCommentResult);
				}
			}
		});

	}

	@Override
	public void GetComments(String photoId, int fromComment, int toComment, final ICommentGetCommentCallback commentGetCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.setSkip(fromComment - 1);
		query.setLimit(toComment - fromComment);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException e) {

				ICommentGetResult getCommentResult;
				if (e == null) {
					IComment[] commentArray = new Comment[list.size()];
					int i = 0;
					IComment tempComment;
					for (ParseObject comment : list) {
						tempComment = new Comment(comment.getObjectId(), comment.getCreatedAt(), comment.getString(ParseConstants.KEY_PARENT),
								comment.getString(ParseConstants.KEY_CREATED_BY), comment.getString(ParseConstants.KEY_COMMENT_STRING), comment
										.getString(ParseConstants.KEY_LOCALE));
						commentArray[i] = tempComment;
					}
					getCommentResult = new CommentGetResult(commentArray, GENERALERROR.SUCCESS);
				} else {
					getCommentResult = new CommentGetResult(null, LibManager.decodeError(e.getCode()));
				}

				if (commentGetCallback != null) {
					commentGetCallback.onGetComment(getCommentResult);
				}
			}
		});
	}

	@Override
	public void GetPhotoById(String photoId, final IPhotoGetCallback photoGetCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.getInBackground(photoId, new GetCallback<ParseObject>() {
			@Override
			public void done(final ParseObject rando, ParseException e) {
				IRandoPhotoGetResult getPhotoResult;
				if (e == null) {
					int likesNumber = getLikes(rando);
					final List<String> listOfReviewers = rando.getList(ParseConstants.KEY_REVIEWERS);
					ParseFile file = rando.getParseFile(ParseConstants.KEY_FILE);
					IRandoPhoto randoPhoto = null;
					if(file.isDataAvailable()){ //indian code alert TODO
						byte[] byteData = null;
						try {
							byteData = file.getData();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						randoPhoto = newFullRando(rando, likesNumber, listOfReviewers, byteData);
					}
					else {
						final int  likesNumberFinal = likesNumber; //indian code alert TODO
						file.getDataInBackground(new GetDataCallback() {
							
							@Override
							public void done(byte[] byteData, ParseException e) {
								IRandoPhotoGetResult getPhotoResult;
								if (e==null){
									IRandoPhoto randoPhoto = newFullRando(rando, likesNumberFinal, listOfReviewers, byteData);
									getPhotoResult = new PhotoGetResult(randoPhoto, GENERALERROR.SUCCESS);
								}
								else {
									getPhotoResult = new PhotoGetResult(null, LibManager.decodeError(e.getCode()));
								}
								if (photoGetCallback != null) {
									photoGetCallback.onIPhotoGet(getPhotoResult);
								}
							}
						});
					}
					getPhotoResult = new PhotoGetResult(randoPhoto, GENERALERROR.SUCCESS);
				} else {
					getPhotoResult = new PhotoGetResult(null, LibManager.decodeError(e.getCode()));
				}
				if (photoGetCallback != null) {
					photoGetCallback.onIPhotoGet(getPhotoResult);
				}
			}
		});
	}

	private void simpleSavePhotoByUsersId(Bitmap photo, final IPhotoSaveCallback photoSaveCallback) {
		final ParseObject photoParse = new ParseObject(ParseConstants.CLASS_PHOTO);
		photoParse.put(ParseConstants.KEY_CREATED_BY, ParseUser.getCurrentUser().getObjectId());

		saveNewRando(photo, photoSaveCallback, photoParse);
	}

	private void saveNewRando(Bitmap photo, final IPhotoSaveCallback photoSaveCallback, final ParseObject photoParse) {
		final ParseFile fileParse = LibManager.BitmapToParseFile(photo);
		fileParse.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					saveNewRandoWithPhoto(photoSaveCallback, photoParse, fileParse);
				} else {
					Log.d("simpleSavePhoto", "Error code:" + e);
				}
			}

	private void saveNewRandoWithPhoto(final IPhotoSaveCallback photoSaveCallback, final ParseObject photoParse, final ParseFile fileParse) {
		photoParse.put(ParseConstants.KEY_FILE, fileParse);
		photoParse.put(ParseConstants.KEY_LIKES, 0);
		photoParse.put(ParseConstants.KEY_SEND_COUNT, 0);
		photoParse.saveEventually(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				IPhotoSaveResult photoSaveResult;
				if (e == null) {
					photoSaveResult = new PhotoSaveResult(GENERALERROR.SUCCESS);
				} else {
					photoSaveResult = new PhotoSaveResult(LibManager.decodeError(e.getCode()));
				}
				if (photoSaveCallback != null) {
					photoSaveCallback.onPhotoSave(photoSaveResult);
				}
			}
		});
	}
});
}

	private ArrayList<String> getReviewersIds(String id1, String id2) {
		ArrayList<String> reviewers = new ArrayList<String>();
		reviewers.add(id1);
		reviewers.add(id2);
		return reviewers;
	}

	@Override
	public void GetTotalNumberOfComments(String photoId, final IGetTotalNumberOfCommentsCallback callback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
		query.countInBackground(new CountCallback() {

			@Override
			public void done(int count, ParseException e) {
				IGetTotalNumberOfCommentsResult getTotalRandosResult = null;
				if (e == null) {
					getTotalRandosResult = new GetTotalNumberOfCommentsResult(count, GENERALERROR.SUCCESS);
				} else {
					getTotalRandosResult = new GetTotalNumberOfCommentsResult(0, LibManager.decodeError(e.getCode()));
				}
				if (callback != null) {
					callback.onGetTotalNumberOfComments(getTotalRandosResult);
				}
			}
		});
	}

	@Override
	public void LikePhotoById(final String photoId, final ILikePhotoCallback likePhotoCallback) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(ParseConstants.KEY_PHOTO_ID_CLOUD, photoId);
		ParseCloud.callFunctionInBackground("likePhoto", params, new FunctionCallback<Object>() {


			@Override
			public void done(Object arg0, ParseException e) {
				//Log.d("HashMap", (String) usersArray.get("userId1"));
				if (e!=null){
					if (likePhotoCallback !=null){
						likePhotoCallback.onSuccess(photoId);
					}
				}
				else {
					if (likePhotoCallback !=null){
						likePhotoCallback.onFail(photoId,  LibManager.decodeError(e.getCode()));
					}
				}
			}
		});
	}
	
	private IRandoPhoto newFullRando(final ParseObject rando, int likesNumber, final List<String> listOfReviewers, byte[] byteData) {
		IRandoPhoto randoPhoto;
		randoPhoto = new RandoPhoto(rando.getObjectId(), rando.getCreatedAt(), rando.getString(ParseConstants.KEY_TITLE),
				LibManager.byteToBitmap(byteData), likesNumber, rando.getString(ParseConstants.KEY_CREATED_BY), rando
						.getDate(ParseConstants.KEY_LASTLIKEDAT), listOfReviewers);
		return randoPhoto;
	}
	
	private int getLikes(final ParseObject rando) {
		int likesNumber;
		if (rando.containsKey(ParseConstants.KEY_LIKES_ID)) {
			likesNumber = rando.getList(ParseConstants.KEY_LIKES_ID).size();
		}
		else {
			likesNumber = 0;
		}
		return likesNumber;
	}

}
