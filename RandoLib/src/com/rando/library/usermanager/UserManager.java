package com.rando.library.usermanager;
/**
 * Created by SERGant on 11.10.2014.
 */
import android.content.Context;
import android.net.Uri;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.LibManager;
import com.rando.library.ParseConstants;
import com.rando.library.usermanager.UserInterfaces.ILoggedUser;
import com.rando.library.usermanager.UserInterfaces.IUser;
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
        if (currentUser != null) {
        	String avatarUrl = currentUser.getParseFile(ParseConstants.KEY_AVATAR).getUrl();
        	if (avatarUrl!=null) {
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
                             String userEmail, Uri avatar, Context context, final IUserRegisterCallback registerCallback) {
        final ParseUser newUser = new ParseUser();
        newUser.setUsername(userName);
        newUser.setPassword(userPassword);
        newUser.setEmail(userEmail);
        if (avatar!=null) {
    			newUser.put(ParseConstants.KEY_FILE, LibManager.uriToParseFile(context, avatar));
    		}

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
                	GENERALERROR error = LibManager.decodeError(e.getCode());
                    saveResult = new UserSaveResult(error);
                }

                if(userSaveCallback != null) userSaveCallback.OnUserSave(saveResult);
			}
		});
		
	}
}
