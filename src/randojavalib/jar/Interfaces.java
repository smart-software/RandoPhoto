package randojavalib.jar;

import android.content.Context;
import randojavalib.jar.Interfaces.IUser;


public class Interfaces {

	public interface ILoggedUser extends IUser {

		
	}
	
	public interface IUserManager {
		
	public void initializeParse(Context context);
	public ILoggedUser GetCurrentLoggedUser();

	public void LogIn(String userName, String userPassword);
	public void LogOff(ILoggedUser user);

	// В случае, если user зарегистрирован, то GetCurrentLoggedUser должна возвращать ILoggedUser
	public void RegisterUser(String userName, String userPassword, String userEmail);

	public void GetUserByID(String ID);
	
	void AddUserRegisterListener(IUserRegisterListener userListener);
	void AddUserLoginListener(IUserLoginListener userListener);
    void RemoveUserRegisterListener(IUserRegisterListener userListener);
    void RemoveUserLoginListener(IUserLoginListener userListener);
	
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
	
	public enum REGISTERRESULT {
        SUCCESS, USEREXISTS, BADPASSWORD, EMPTYDATA,UNDEFINED
    }
	
	// Realization class should extend EventObject!
    public interface IUserRegisterResult { 
       REGISTERRESULT GetUserRegisterResult();
       IUser GetRegisteredUser(); // null if Register failed
    }
    

    // Don't realize this interface
    public interface IUserRegisterListener {
        void OnUserRegister(IUserRegisterResult registerResult);
    }
    
    public enum LOGINRESULT { 
        SUCCESS, BADPASSWORD, NOTEXIST, UNDEFINED
    }
    
    // Realization class should extend EventObject!
    public interface IUserLoginResult {
        LOGINRESULT GetUserLoginResult();
        ILoggedUser GetLoggedUser();
    }
    
    // Don't realize this interface
    public interface IUserLoginListener {
        void OnUserLogin(IUserLoginResult loginResult);
    }
    
    
}
