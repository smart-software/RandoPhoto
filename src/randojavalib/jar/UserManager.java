package randojavalib.jar;


import randojavalib.jar.Interfaces.ICallbacksFormBackground;
import randojavalib.jar.Interfaces.ILoggedUser;
import randojavalib.jar.Interfaces.IUser;
import randojavalib.jar.Interfaces.IUserManager;


import android.content.Context;
import android.content.Intent;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class UserManager implements IUserManager{

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
	public ILoggedUser LogIn(String userName, String userPassword) {

		String username = userName.trim();
		String password = userPassword.trim();
		
		if (username.isEmpty() || password.isEmpty()) {
			// Empty username or password - TODO îáðàáîò÷èê îøèáêè
		}
		else {
			// Login
			//setProgressBarIndeterminateVisibility(true); òóò ìîæíî ïîñëàòü â ïðåçåíòåð "ïîêàçàòü ïðîãðåññ áàð"
			
			ParseUser.logInInBackground(username, password, new LogInCallback() {
				@Override
				public void done(ParseUser user, ParseException e) {
					//setProgressBarIndeterminateVisibility(false);  toPresenter "ïðîãðåññ áàð âûêëþ÷èòü"
					ICallbacksFormBackground callback = new CallbacksFormBackground();
					if (e == null) {
						// Success!
						callback.loginSucess(true, null);
					}
					else {
						//íå çàëîãèíèëñÿ
						callback.loginSucess(false, "Exception: "+e);
					}
				}
			});
		}
		
		return null;
	}

	@Override
	public void LogOff(ILoggedUser user) {
		ParseUser.logOut();
	}

	@Override
	public void RegisterUser(String userName,String userPassword, String userEmail) {
		
		String username = userName.trim();
		String password = userPassword.trim();
		String email = userEmail.trim();
		
		final  boolean success = true; // ýòî çàãëóøêà. Â áóäóùåì íàäî çàïèëèòü êîëëëáåê.
		
		if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
			//something missing - ïðîâåðêà íà íàëè÷èå äàííûõ
			ICallbacksFormBackground callback = new CallbacksFormBackground();
			callback.registerSucess(false, "Exception: Empty username or password or email");
		}
		else {
			// create the new user!
			//setProgressBarIndeterminateVisibility(true); ïðîãðåññ áàð - on
			
			ParseUser newUser = new ParseUser();
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setEmail(email);
			newUser.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(ParseException e) {
					//setProgressBarIndeterminateVisibility(false);
					
					ICallbacksFormBackground callback = new CallbacksFormBackground();
					if (e == null) {
						// Success!
						//success = true;
						callback.registerSucess(true, null);
					}
					else {
						callback.registerSucess(false, "Exception: "+e);
					}
				}
			});
		}

	}

	@Override
	public  void GetUserByID(String ID) {
		final IUser user = new User();
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.ClASS_USER);
		query.getInBackground(ID, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				if (e== null){
					
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
		Intent intent = new Intent(context, ParseInitialize.class); // íå óâåðåí, ñðàáîòàòåò ëè îòñþäà. Íî çàïóñòèòü âñå ðàâíîå íåîáõîäèìî.
		context.startActivity(intent);
	}

	

}
