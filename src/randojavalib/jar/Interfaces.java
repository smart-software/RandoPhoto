package randojavalib.jar;

import android.content.Context;
import randojavalib.jar.Interfaces.IUser;


public class Interfaces {

	/* Sequence of interfaces in file:
	 	1. IParseInitialize
	  	2. IUser
		3. ILoggedUser
		4. enum LOGINRESULT
		5. IUserLoginResult
		6. enum REGISTERRESULT
		7. IUserRegisterResult
		8. IUserManager
	 */
	
	
	public interface IParseInitialize {
		
	}
	
	public interface IUser {
		
		public String GetUID(); //Unique id for user. Consists from letters (all cases) and digits
		public String GetName(); //username - can be formed from letters (all cases) and digits
		public void SetName(String userName); // Sets the name in IUser
		public void SetId(String UserId); // Sets the id in IUser
		public void SetEmail(String email); // Sets the email in IUser
	}
	public interface ILoggedUser extends IUser {
		//the same as IUser, but different name
	}
	
    public enum LOGINRESULT { 
        SUCCESS, BADPASSWORD, NOTEXIST, UNDEFINED //for IUserLoginResult
    }
    // Realization class should extend EventObject!
    public interface IUserLoginResult {
        LOGINRESULT GetUserLoginResult(); //See LOGINRESULT for possible values
        ILoggedUser GetLoggedUser(); // Returns null if login failed
    }
    
    public enum REGISTERRESULT { 
        SUCCESS, USEREXISTS, BADPASSWORD, EMPTYDATA,UNDEFINED //for IUserRegisterResult
    }
    // Realization class should extend EventObject!
    public interface IUserRegisterResult { 
       REGISTERRESULT GetUserRegisterResult(); //see REGISTERRESULT for possible values
       IUser GetRegisteredUser(); // null if Register failed
    }
    
    
	public interface IUserManager {
		
	public void initializeParse(Context context); //Not necessary, depends on how Parse.initialize will be executed
	public ILoggedUser GetCurrentLoggedUser(); //Returns current user by ParseUser.getCurrantUser (locally). Returns null if no logged user. - fast

	public void LogIn(String userName, String userPassword); //Login registered user - background web task - ~1 sec
	public void LogOff(ILoggedUser user); // Logoff current user - locally, web access optional - fast

	public void RegisterUser(String userName, String userPassword, String userEmail); // Register user - web background task - ~1 sec

	public void GetUserByID(String ID); //Returns user by id (unique) - web background task - ~1 sec
	
	void AddUserRegisterListener(IUserRegisterListener userListener); //Adds listeners
	void AddUserLoginListener(IUserLoginListener userListener);
    void RemoveUserRegisterListener(IUserRegisterListener userListener); //removes listeners
    void RemoveUserLoginListener(IUserLoginListener userListener);
	
	}


    // Don't realize this interface
    public interface IUserRegisterListener {
        void OnUserRegister(IUserRegisterResult registerResult);
    }
    // Don't realize this interface
    public interface IUserLoginListener {
        void OnUserLogin(IUserLoginResult loginResult);
    }
    
    
}
