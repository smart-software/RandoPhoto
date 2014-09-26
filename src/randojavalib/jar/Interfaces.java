package randojavalib.jar;

import android.content.Context;
import randojavalib.jar.Interfaces.IUser;

public class Interfaces {

	public interface ILoggedUser extends IUser {

		
	}
	
	public interface IUserManager {
		
	public void initializeParse(Context context);
	public ILoggedUser GetCurrentLoggedUser();

	public ILoggedUser LogIn(String userName, String userPassword);
	public void LogOff(ILoggedUser user);

	// В случае, если user зарегистрирован, то GetCurrentLoggedUser должна возвращать ILoggedUser
	public void RegisterUser(String userName, String userPassword, String userEmail);

	public void GetUserByID(String ID);
	}
	
	public interface IUser {

		public String GetUID();
		public String GetName();
		public void SetName(String userName);
		public void SetId(String UserId);
		public void SetEmail(String email);
	}
	
	public interface ICallbacksFormBackground {

		public void loginSucess(boolean state, String exception);

		public void registerSucess(boolean state, String exception);

		public void returnGetUserByID(IUser user);
		
	}
	
}
