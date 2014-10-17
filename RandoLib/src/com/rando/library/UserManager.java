package com.rando.library;
/**
 * Created by SERGant on 11.10.2014.
 * Alexey Kazin - alexeykazin@gmail.com
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.rando.library.UserInterfaces.GENERALERROR;
import com.rando.library.UserInterfaces.IComment;
import com.rando.library.UserInterfaces.ICommentGetCommentCallback;
import com.rando.library.UserInterfaces.ICommentGetResult;
import com.rando.library.UserInterfaces.ICommentSaveCallback;
import com.rando.library.UserInterfaces.ICommentSaveResult;
import com.rando.library.UserInterfaces.ILoggedUser;
import com.rando.library.UserInterfaces.IPhoto;
import com.rando.library.UserInterfaces.IPhotoGetCallback;
import com.rando.library.UserInterfaces.IPhotoGetResult;
import com.rando.library.UserInterfaces.IPhotoSaveCallback;
import com.rando.library.UserInterfaces.IPhotoSaveResult;
import com.rando.library.UserInterfaces.IUser;
import com.rando.library.UserInterfaces.IUserLoginCallback;
import com.rando.library.UserInterfaces.IUserLoginResult;
import com.rando.library.UserInterfaces.IUserManager;
import com.rando.library.UserInterfaces.IUserRegisterCallback;
import com.rando.library.UserInterfaces.IUserRegisterResult;
import com.rando.library.UserInterfaces.IUserSaveCallback;
import com.rando.library.UserInterfaces.IUserSaveResult;
import com.rando.library.UserInterfaces.LOGINRESULT;
import com.rando.library.UserInterfaces.REGISTERRESULT;

public class UserManager implements IUserManager {
	
	private String mRandomUserIndex1;
	private String mRandomUserIndex2;
	private int mUserIndex2;
	
    @Override
    public ILoggedUser GetCurrentUser() {
        ILoggedUser loggedUser = null;
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            loggedUser = new LoggedUser(currentUser.getObjectId(), currentUser.getUsername(), currentUser.getEmail());
        }

        return loggedUser;
    }

    @Override
    public  void LogInUser(String userName, String userPassword, final IUserLoginCallback loginCallback) {
        ParseUser.logInInBackground(userName, userPassword, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                IUserLoginResult loginResult = null;
                if ((e == null) && (user != null)) { //indian code alert!
                	
                		ILoggedUser loggedUser = new LoggedUser(user.getObjectId(), user.getUsername(), user.getEmail());
                    	loginResult = new UserLoginResult(loggedUser, LOGINRESULT.SUCCESS);
                    
                }
                else if (user == null) {
                    ILoggedUser loggedUser = null;
                    loginResult = new UserLoginResult(loggedUser, LOGINRESULT.BADPASSWORD);
                }
                else { // Something wrong
                    LOGINRESULT loginRes = LOGINRESULT.UNDEFINED;

                    switch (e.getCode()) {
                        case 201:
                        case 200:
                            loginRes = LOGINRESULT.BADPASSWORD;
                            break;
                        case 205:
                        case 207:
                            loginRes = LOGINRESULT.NOTEXIST;
                            break;
                        default:
                            break;
                    }
                    ILoggedUser loggedUser = null;
                    loginResult = new UserLoginResult(loggedUser, loginRes);
                }

                if(loginCallback != null) loginCallback.OnUserLogin(loginResult);
            }
        });
    }

    @Override
    public void LogOffUser() {
        ParseUser.logOut(); // GetLoggedUser should return null.
    }

    @Override
    public void RegisterUser(String userName, String userPassword,
                             String userEmail, final IUserRegisterCallback registerCallback) {
        final ParseUser newUser = new ParseUser();
        newUser.setUsername(userName);
        newUser.setPassword(userPassword);
        newUser.setEmail(userEmail);

        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                IUserRegisterResult registerResult = null;

                if (e == null) { // User signed up Successfully
                    IUser registerUser = new User(newUser.getObjectId(), newUser.getUsername());
                    registerResult = new UserRegisterResult(registerUser, REGISTERRESULT.SUCCESS);
                }
                else {
                    REGISTERRESULT registerRes = REGISTERRESULT.UNDEFINED;
                    switch (e.getCode()) {
                        case 202:
                            registerRes = REGISTERRESULT.USEREXISTS;
                            break;
                        case 201:
                            registerRes = REGISTERRESULT.BADPASSWORD;
                            break;
                        default:
                            break;
                    }

                    registerResult = new UserRegisterResult(null, registerRes);
                }

                if(registerCallback != null) registerCallback.OnUserRegister(registerResult);
            }
        });
    }

	@Override
	public void SaveCurrentUser(final IUserSaveCallback userSaveCallback) {
		ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				IUserSaveResult saveResult = null;

                if (e == null) { // Current User save Successfully
                    saveResult = new UserSaveResult(GENERALERROR.SUCCESS);
                }
                else {
                	GENERALERROR error = decodeError(e.getCode());
                    saveResult = new UserSaveResult(error);
                }

                if(userSaveCallback != null) userSaveCallback.OnUserSave(saveResult);
			}
		});
		
	}

	@Override
	public void SaveIPhoto(final IPhoto photo, final IPhotoSaveCallback photoSaveCallback) {
		
		ParseQuery<ParseObject> queryPickRandomUsers = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
		queryPickRandomUsers.addAscendingOrder(ParseConstants.KEY_CREATED_AT);
		queryPickRandomUsers.countInBackground(new CountCallback() {
			@Override
			public void done(int count, ParseException e) {
				if (e==null) {
					Random r = new Random();
					int userIndex1 = r.nextInt(count)+1;
					
					boolean segmentLeft = r.nextBoolean();
					if (segmentLeft == true) {
						mUserIndex2 = r.nextInt(userIndex1)+1;
					}
					else {
						mUserIndex2 = r.nextInt(count-userIndex1)+userIndex1;
					}
					
					ParseQuery<ParseObject> queryGetIdRandomUsers = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
					queryGetIdRandomUsers.addAscendingOrder(ParseConstants.KEY_CREATED_AT);
					queryGetIdRandomUsers.setSkip(userIndex1-1);
					queryGetIdRandomUsers.setLimit(1);
					queryGetIdRandomUsers.findInBackground(new FindCallback<ParseObject>() {
						
						@Override
						public void done(List<ParseObject> list, ParseException e) {
							if (e==null) {
								mRandomUserIndex1 = list.get(0).getString(ParseConstants.KEY_USERNAME);
								ParseQuery<ParseObject> queryGetId2RandomUsers = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
								queryGetId2RandomUsers.addAscendingOrder(ParseConstants.KEY_CREATED_AT);
								queryGetId2RandomUsers.setSkip(mUserIndex2-1);
								queryGetId2RandomUsers.setLimit(1);
								queryGetId2RandomUsers.findInBackground(new FindCallback<ParseObject>() {
									
									@Override
									public void done(List<ParseObject> list, ParseException e) {
										if (e==null){
											mRandomUserIndex2 = list.get(0).getString(ParseConstants.KEY_USERNAME);
											simpleSavePhotoByUsersId(photo, mRandomUserIndex1, mRandomUserIndex2, photoSaveCallback);
										}
									}
								});
							}
							else {
								if (photoSaveCallback!=null ){photoSaveCallback.onPhotoSave(new PhotoSaveResult(decodeError(e.getCode())));}
							}
						}
					});
				}
				else {
					if (photoSaveCallback!=null ){photoSaveCallback.onPhotoSave(new PhotoSaveResult(decodeError(e.getCode())));}
				}
			}
		});
				
				
	}

	@Override
	public void SaveIComment(IComment comment,
			final ICommentSaveCallback commentSaveCallback) {
		ParseObject commentParse = new ParseObject(ParseConstants.CLASS_COMMENT);
		
		commentParse.add(ParseConstants.KEY_COMMENT_STRING, comment.GetCommentString());
		commentParse.add(ParseConstants.KEY_LOCALE, comment.GetLocale());
		commentParse.add(ParseConstants.KEY_PARENT, comment.GetParentId());
		
		commentParse.saveEventually(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				ICommentSaveResult commentSaveResult;
				if (e==null) {
					commentSaveResult = new CommentSaveResult(GENERALERROR.SUCCESS);
				}
				else {
					commentSaveResult = new CommentSaveResult(decodeError(e.getCode()));
				}
				if (commentSaveCallback!=null) {
					commentSaveCallback.onCommentSave(commentSaveResult);
				}
			}
		});
		
	}

	@Override
	public void GetPhotoComments(String photoId, boolean recent,
			int numberOfRecentComments, final ICommentGetCommentCallback commentGetCallback) {
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_COMMENT);
			query.whereEqualTo(ParseConstants.KEY_PARENT, photoId);
			query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
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
									comment.getString(ParseConstants.KEY_PARENT), comment.getString(ParseConstants.KEY_COMMENT_STRING),
									comment.getString(ParseConstants.KEY_LOCALE));
							commentArray[i] = tempComment;
						}
						getCommentResult = new CommentGetResult(commentArray, GENERALERROR.SUCCESS);
					}
					else{
						getCommentResult = new CommentGetResult(null, decodeError(e.getCode()));
					}
					
						if (commentGetCallback!=null) {
							commentGetCallback.onGetComment(getCommentResult);
						}
				}
			});
		
	}

	@Override
	public void GetPhotoById(String photoId, IPhotoGetCallback photoGetResult) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.getInBackground(photoId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject photo, ParseException e) {
				IPhotoGetResult getPhotoResult;
				if(e==null){
					List<String> reviwers = photo.getList(ParseConstants.KEY_REVIEWERS);
					IPhoto getPhoto = new Photo(photo.getObjectId(), photo.getCreatedAt(), 
							photo.getString(ParseConstants.KEY_TITLE), photo.getParseFile(ParseConstants.KEY_FILE).getUrl(),
							photo.getList(ParseConstants.KEY_LIKES_ID).size(), photo.getString(ParseConstants.KEY_CREATED_BY),
							photo.getDate(ParseConstants.KEY_LASTLIKEDAT), reviwers);
				}
				else{
					
				}
			}
		});
	}
	
	private GENERALERROR decodeError(int errorCode){
		GENERALERROR error;
		int[] listOfErrors = {-1,1,100,101,102,103,104,105,106,107,108,109,111,112,115,116,119,120,121,122,123,124,125,137,139,140,141,200,201,202,203,204,205,206,207,208,250,251,252};
		int indexError = Arrays.asList(listOfErrors).indexOf(errorCode);
		error = GENERALERROR.values()[indexError];
		return error;
	}
	
	private void simpleSavePhotoByUsersId(IPhoto photo, String userId1,String userId2, final IPhotoSaveCallback photoSaveCallback){
		ParseObject photoParse = new ParseObject(ParseConstants.CLASS_PHOTO);
		photoParse.add(ParseConstants.KEY_TITLE, photo.GetTitle());
		photoParse.add(ParseConstants.KEY_FILE, photo.GetPhoto());
		photoParse.add(ParseConstants.KEY_CREATED_BY, photo.GetCreatedBy());
		photoParse.add(ParseConstants.KEY_REVIEWERS, getReviewersIds());
		photoParse.saveEventually(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				IPhotoSaveResult photoSaveResult;
				if (e==null) {
					photoSaveResult = new PhotoSaveResult(GENERALERROR.SUCCESS);
				}
				else {
					photoSaveResult = new PhotoSaveResult(decodeError(e.getCode()));
				}
					if (photoSaveCallback!=null) {
						photoSaveCallback.onPhotoSave(photoSaveResult);
					}
			}
		});
	}
	
	private ArrayList<String> getReviewersIds(){
		ArrayList<String> reviewers = new ArrayList<String>();
		reviewers.add(mRandomUserIndex1);
		reviewers.add(mRandomUserIndex2);
		return reviewers;
	}

}
