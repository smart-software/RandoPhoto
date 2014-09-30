package randojavalib.jar;


import java.util.ArrayList;

import randojavalib.jar.Interfaces.ICallbacksFormBackground;
import randojavalib.jar.Interfaces.ILoggedUser;
import randojavalib.jar.Interfaces.IUser;
import randojavalib.jar.Interfaces.IUserLoginListener;
import randojavalib.jar.Interfaces.IUserLoginResult;
import randojavalib.jar.Interfaces.IUserManager;
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
	public ILoggedUser GetCurrentLoggedUser() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		ILoggedUser loggedUser = null;
		
		if (currentUser!=null) {
		loggedUser = new LoggedUser();
		loggedUser.SetId(currentUser.getObjectId());
		loggedUser.SetName(currentUser.getUsername());
		loggedUser.SetEmail(currentUser.getEmail());
		}

		return loggedUser;
	}

	@Override
	public  void LogIn(String userName, String userPassword) {

		String username = userName.trim();
		String password = userPassword.trim();
		
		if (username.isEmpty() || password.isEmpty()) {
			// Empty username or password - TODO обработчик ошибки
		}
		else {
			// Login
			//setProgressBarIndeterminateVisibility(true); тут можно послать в презентер "показать прогресс бар"
			
			ParseUser.logInInBackground(username, password, new LogInCallback() {
				@Override
				public void done(ParseUser user, ParseException e) {
					//setProgressBarIndeterminateVisibility(false);  toPresenter "прогресс бар выключить"
					if (e == null) {
						// Success!
						
						ILoggedUser userLogged = new LoggedUser();
						userLogged.SetId(user.getObjectId());
						userLogged.SetName(user.getUsername());
						userLogged.SetEmail(user.getEmail());
						Log.d(TAG, "User logged in.");
						fireLoginResult(userLogged, LOGINRESULT.SUCCESS);
					}
					else {
						//Login - failed
						LOGINRESULT exception;
						switch (e.getCode()) {
						case 201 | 200: // hope this condition will work
							exception = LOGINRESULT.BADPASSWORD;
							break;
						case 205 | 207: 
							exception = LOGINRESULT.NOTEXIST;
							break;
						default:
							exception = LOGINRESULT.UNDEFINED;
							break;
						}
						Log.d(TAG, "User login fail. Error: " + e);
						fireLoginResult(null, exception); 
					}
				}
			});
		}
	}

	@Override
	public void LogOff(ILoggedUser user) {
		ParseUser.logOut();
	}
	
	private ParseUser newUser;
	
	@Override
	public void RegisterUser(String userName,String userPassword, String userEmail) {
		
		String username = userName.trim();
		String password = userPassword.trim();
		String email = userEmail.trim();
		
		if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
			//something missing - check for empty data
			fireRegisterResult(null, REGISTERRESULT.EMPTYDATA);
		}
		else {
			// create the new user!
			//setProgressBarIndeterminateVisibility(true); 
			
			newUser = new ParseUser();
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setEmail(email);
			newUser.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(ParseException e) {
					//setProgressBarIndeterminateVisibility(false);
					
					if (e == null) {
						// Success!
						IUser user = new User();
						user.SetId(newUser.getObjectId());
						user.SetName(newUser.getUsername());
						user.SetEmail(newUser.getEmail());
						Log.d(TAG, "New user registered");
						fireRegisterResult(user, REGISTERRESULT.SUCCESS);
					}
					else {
						REGISTERRESULT exception;
						switch (e.getCode()) {
						case 202:
							exception = REGISTERRESULT.USEREXISTS;
							break;
						case 201:
							exception = REGISTERRESULT.BADPASSWORD;
							break;
						default:
							exception = REGISTERRESULT.UNDEFINED;
							break;
						}
						Log.d(TAG, "New user register fail. Error: " + e);
						fireRegisterResult(null, exception);
					}
				}
			});
		}

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
					user.SetEmail(object.getString(ParseConstants.KEY_EMAIL));
					
					ICallbacksFormBackground callback = new CallbacksFormBackground();
					callback.returnGetUserByID(user);
				}
			}
		});
	}

	@Override
	public void initializeParse(Context context) {
		Intent intent = new Intent(context, ParseInitialize.class); // не уверен, сработатет ли отсюда. Но запустить все равное необходимо.
		context.startActivity(intent);
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


	protected void fireRegisterResult(IUser user, REGISTERRESULT exception) {
		IUserRegisterResult event = new UserRegisterEvent(user, exception);

		for (IUserRegisterListener listener: listenersRegister)
			listener.OnUserRegister(event);
	}
	
	protected void fireLoginResult(ILoggedUser user, LOGINRESULT exception) {
		IUserLoginResult event = new UserLoginEvent(user, exception);

		for (IUserLoginListener listener: listenersLogin)
			listener.OnUserLogin(event);
	}

}
