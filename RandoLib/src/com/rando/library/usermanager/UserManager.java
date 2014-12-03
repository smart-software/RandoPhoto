package com.rando.library.usermanager;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.ParseQuery.CachePolicy;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.LibManager;
import com.rando.library.ParseConstants;
import com.rando.library.usermanager.UserInterfaces.ILoggedUser;
import com.rando.library.usermanager.UserInterfaces.IUser;
import com.rando.library.usermanager.UserInterfaces.IUserGetByIdCallback;
import com.rando.library.usermanager.UserInterfaces.IUserGetByIdResult;
import com.rando.library.usermanager.UserInterfaces.IUserLoginCallback;
import com.rando.library.usermanager.UserInterfaces.IUserLoginResult;
import com.rando.library.usermanager.UserInterfaces.IUserManager;
import com.rando.library.usermanager.UserInterfaces.IUserRegisterCallback;
import com.rando.library.usermanager.UserInterfaces.IUserRegisterResult;
import com.rando.library.usermanager.UserInterfaces.IUserSaveCallback;
import com.rando.library.usermanager.UserInterfaces.IUserSaveResult;
import com.rando.library.usermanager.UserInterfaces.LOGINRESULT;
import com.rando.library.usermanager.UserInterfaces.REGISTERRESULT;

public class UserManager implements IUserManager {
    @Override
    public ILoggedUser GetCurrentUser() {
        ILoggedUser loggedUser = null;
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser!=null){
        	if (currentUser.containsKey(ParseConstants.KEY_AVATAR)) {
        		String avatarUrl = currentUser.getParseFile(ParseConstants.KEY_AVATAR).getUrl();
        		loggedUser = new LoggedUser(currentUser.getObjectId(), currentUser.getUsername(), currentUser.getEmail(), avatarUrl);
        	}
        	else {
            loggedUser = new LoggedUser(currentUser.getObjectId(), currentUser.getUsername(), currentUser.getEmail());
        	}
        }
        return loggedUser;
    }

    @Override
    public  void LogInUser(String userName, String userPassword, final IUserLoginCallback loginCallback) {
        ParseUser.logInInBackground(userName, userPassword, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                IUserLoginResult loginResult = null;
                if ((e == null) && (user != null)) {
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
                             String userEmail, File avatar, final IUserRegisterCallback registerCallback) {
        final ParseUser newUser = new ParseUser();
        newUser.setUsername(userName);
        newUser.setPassword(userPassword);
        newUser.setEmail(userEmail);
        
        final SignUpCallback sighUpCallback = new SignUpCallback() {
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
        };
        
        if (avatar.isFile()) {
        		final ParseFile avatarParseFile = LibManager.fileToParseFile(avatar);
        		avatarParseFile.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if (e==null){
							newUser.put(ParseConstants.KEY_FILE, avatarParseFile);
							newUser.signUpInBackground(sighUpCallback);
						}
						else {
							Log.d("Register user", "Error: "+e);
						}
					}
				});
    			
    		}
        else {
        	newUser.signUpInBackground(sighUpCallback);
        }

        
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
                	GENERALERROR error = LibManager.decodeError(e.getCode());
                    saveResult = new UserSaveResult(error);
                }

                if(userSaveCallback != null) userSaveCallback.OnUserSave(saveResult);
			}
		});
		
	}

	@Override
	public void GetUserById(final String userId, final IUserGetByIdCallback userGetCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.getInBackground(userId, new GetCallback<ParseObject>() { //indian code alert
			
			@Override
			public void done(final ParseObject userParse, ParseException e) {
				IUserGetByIdResult getByIdResult = null;
				if (e==null) {
					if (userParse.containsKey(ParseConstants.KEY_FILE)){
						ParseFile avatarParseFile = userParse.getParseFile(ParseConstants.KEY_FILE);
						avatarParseFile.getDataInBackground(new GetDataCallback() {
							
							@Override
							public void done(byte[] byteArray, ParseException e) {
								IUserGetByIdResult getByIdResultWithAvatar = null;
								if (e==null){
									File avatarFile = LibManager.convertByteToFile("avatar"+userId, byteArray);
									IUser user = new User(userParse.getObjectId(), userParse.getString(ParseConstants.KEY_USERNAME),
											avatarFile);
									getByIdResultWithAvatar = new UserGetByIdResult(user, GENERALERROR.SUCCESS);
								}
								else {
									GENERALERROR error = LibManager.decodeError(e.getCode());
									getByIdResultWithAvatar = new UserGetByIdResult(null, error);
								}
								if (userGetCallback!=null) {
									userGetCallback.OnGetUserById(getByIdResultWithAvatar);
								}
							}
						});
					
							
					}
					else {
						IUser user = new User(userParse.getObjectId(), userParse.getString(ParseConstants.KEY_USERNAME));
						getByIdResult = new UserGetByIdResult(user, GENERALERROR.SUCCESS);
					}
					
					
				}
				else {
					GENERALERROR error = LibManager.decodeError(e.getCode());
					getByIdResult = new UserGetByIdResult(null, error);
				}
				
				if (userGetCallback!=null) {
					userGetCallback.OnGetUserById(getByIdResult);
				}
			}
		});
	}


}
