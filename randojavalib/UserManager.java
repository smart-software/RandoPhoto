package randojavalib.jar;


import java.util.ArrayList;

import randojavalib.jar.Interfaces.ILoggedUser;
import randojavalib.jar.Interfaces.IUser;
import randojavalib.jar.Interfaces.IUserLoginCallback;
import randojavalib.jar.Interfaces.IUserLoginListener;
import randojavalib.jar.Interfaces.IUserLoginResult;
import randojavalib.jar.Interfaces.IUserManager;
import randojavalib.jar.Interfaces.IUserRegisterCallback;
import randojavalib.jar.Interfaces.IUserRegisterListener;
import randojavalib.jar.Interfaces.IUserRegisterResult;
import randojavalib.jar.Interfaces.LOGINRESULT;
import randojavalib.jar.Interfaces.REGISTERRESULT;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class UserManager implements IUserManager{
	
	String TAG = "User Manager";


	@Override
	public ILoggedUser GetCurrentUser() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		ILoggedUser loggedUser = null;
		
		if (currentUser!=null) {
		loggedUser = new LoggedUser();
		loggedUser.SetId(currentUser.getObjectId());
		loggedUser.SetName(currentUser.getUsername());
		loggedUser.SetUserEmail(currentUser.getEmail());
		}

		return loggedUser;
	}

	@Override
	public  void LogInUser(String userName, String userPassword, IUserLoginCallback loginCallback) {


			// Login
			//setProgressBarIndeterminateVisibility(true); тут можно послать в презентер "показать прогресс бар"
			
			ParseUser.logInInBackground(userName, userPassword, new LogInCallback() {
				@Override
				public void done(ParseUser user, ParseException e) {
					//setProgressBarIndeterminateVisibility(false);  toPresenter "прогресс бар выключить"
					if (e == null) {
						// Success!
						
						Log.d(TAG, "User logged in.");
						fireLoginResult(LOGINRESULT.SUCCESS);
					}
					else {
						//Login - failed
						LOGINRESULT loginResult;
						switch (e.getCode()) {
						case 201 | 200: // hope this condition will work
							loginResult = LOGINRESULT.BADPASSWORD;
							break;
						case 205 | 207: 
							loginResult = LOGINRESULT.NOTEXIST;
							break;
						default:
							loginResult = LOGINRESULT.UNDEFINED;
							break;
						}
						Log.d(TAG, "User login fail. Error: " + e);
						fireLoginResult(loginResult); 
					}
				}
			});
		}


	@Override
	public void LogOffUser(ILoggedUser user) {
		ParseUser.logOut();
	}
	
	
	
	@Override
	public void RegisterUser(String userName,String userPassword, String userEmail, IUserRegisterCallback registerCallback) {
		

			// create the new user!
			//setProgressBarIndeterminateVisibility(true); 
			final ParseUser newUser = new ParseUser();
			newUser.setUsername(userName);
			newUser.setPassword(userPassword);
			newUser.setEmail(userEmail);
			newUser.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(ParseException e) {
					//setProgressBarIndeterminateVisibility(false);
					
					if (e == null) {
						// Success!
						fireRegisterResult(REGISTERRESULT.SUCCESS);
					}
					else {
						REGISTERRESULT registerResult;
						switch (e.getCode()) {
						case 202:
							registerResult = REGISTERRESULT.USEREXISTS;
							break;
						case 201:
							registerResult = REGISTERRESULT.BADPASSWORD;
							break;
						default:
							registerResult = REGISTERRESULT.UNDEFINED;
							break;
						}
						Log.d(TAG, "New user register fail. Error: " + e);
						fireRegisterResult(registerResult);
					}
				}
			});
		}


	@Override
	public  void GetUserByID(String ID) {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.ClASS_USER);
		query.getInBackground(ID, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				if (e== null){
					User user = new User();
					user.SetId(object.getObjectId());
					user.SetName(object.getString(ParseConstants.KEY_USERNAME));
				}
			}
		});
	}


	
	// --Listeners section--
	private ArrayList<IUserRegisterListener> listenersRegister = new ArrayList<IUserRegisterListener>();
	private ArrayList<IUserLoginListener> listenersLogin = new ArrayList<IUserLoginListener>();
	
	@Override
	public void AddUserRegisterListener(IUserRegisterListener userListener) {
		listenersRegister.add(userListener);
	}
	
	@Override
	public void AddUserLoginListener(IUserLoginListener userListener) {
		listenersLogin.add(userListener);
	}

	@Override
	public void RemoveUserRegisterListener(IUserRegisterListener userListener) {
		listenersRegister.remove(userListener);
	}

	@Override
	public void RemoveUserLoginListener(IUserLoginListener userListener) {
		listenersLogin.remove(userListener);
	}


	private void fireRegisterResult(REGISTERRESULT registerResult) {
		IUserLoginCallback callback = new UserLoginCallback();
		callback.OnUserLogin(registerResult);
	}
	
	private  void fireLoginResult(LOGINRESULT registerResult) {
		IUserRegisterCallback callback = new UserRegisterCallback();
		callback.OnUserRegister(registerResult);
		
	}

}
