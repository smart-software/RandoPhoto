package randojavalib.jar;

import randojavalib.jar.UserInterfaces.ILoggedUser;
import randojavalib.jar.UserInterfaces.IUser;
import randojavalib.jar.UserInterfaces.IUserLoginCallback;
import randojavalib.jar.UserInterfaces.IUserLoginResult;
import randojavalib.jar.UserInterfaces.IUserManager;
import randojavalib.jar.UserInterfaces.IUserRegisterCallback;
import randojavalib.jar.UserInterfaces.IUserRegisterResult;
import randojavalib.jar.UserInterfaces.LOGINRESULT;
import randojavalib.jar.UserInterfaces.REGISTERRESULT;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;



public class UserManager implements IUserManager {
	String TAG = "[RandoLibrary][UserManager]";
	
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
				
				if ((e == null) && (user != null)) {
					ILoggedUser loggedUser = new LoggedUser(user.getObjectId(), user.getUsername(), user.getEmail());
					loginResult = new UserLoginEvent(loggedUser, LOGINRESULT.SUCCESS);
				}
				else if (user == null) {
					ILoggedUser loggedUser = null;
					loginResult = new UserLoginEvent(loggedUser, LOGINRESULT.BADPASSWORD);
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
					loginResult = new UserLoginEvent(loggedUser, loginRes);
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
		final ParseUser newUser = new ParseUser(); // точно тут final нужен?
		newUser.setUsername(userName);
		newUser.setPassword(userPassword);
		newUser.setEmail(userEmail);
		
		newUser.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				IUserRegisterResult registerResult = null;
				
				if (e == null) { // User signed up Successfully
					IUser registerUser = new User(newUser.getObjectId(), newUser.getUsername());
					registerResult = new UserRegisterEvent(registerUser, REGISTERRESULT.SUCCESS);
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
					
					registerResult = new UserRegisterEvent(null, registerRes);
				}
				
				if(registerCallback != null) registerCallback.OnUserRegister(registerResult);
			}
		});
		
	}
}
