package com.rando.library;

/**
 * Created by SERGant on 11.10.2014.
 */
public class UserInterfaces {
    public interface IUser {
        public String GetUID(); // Unique id for user. Consists from letters (all cases) and digits
        public String GetUserName(); // Username - can be formed from letters (all cases) and digits
        public void SetUserName(String userName); // Sets the name in IUser
    }

    public interface ILoggedUser extends IUser {
        String GetUserEmail();
        void SetUserEmail(String userEmail);// Sets the email
    }

    public enum LOGINRESULT {
        SUCCESS, BADPASSWORD, NOTEXIST, UNDEFINED //for IUserLoginResult
    }

    public interface IUserLoginResult {
        LOGINRESULT GetUserLoginResult(); //See LOGINRESULT for possible values
        ILoggedUser GetLoggedUser(); // Returns null if login failed
    }

    public interface IUserLoginCallback {
        void OnUserLogin(IUserLoginResult loginResult);
    }

    public enum REGISTERRESULT {
        SUCCESS, USEREXISTS, BADPASSWORD, EMPTYDATA,UNDEFINED //for IUserRegisterResult
    }

    public interface IUserRegisterResult {
        REGISTERRESULT GetUserRegisterResult(); //see REGISTERRESULT for possible values
        IUser GetRegisteredUser(); // null if register failed
    }

    public interface IUserRegisterCallback {
        void OnUserRegister(IUserRegisterResult registerResult);
    }

    public interface IUserManager {
        public ILoggedUser GetCurrentUser(); //Returns current user by ParseUser.getCurrentUser (locally). Returns null if no logged user. - fast
        public void LogInUser(String userName, String userPassword, IUserLoginCallback loginCallback); //Login registered user - background web task - ~1 sec
        public void LogOffUser(); // Logoff current user - locally, web access optional - fast
        public void RegisterUser(String userName, String userPassword, String userEmail, IUserRegisterCallback registerCallback); // Register user - web background task - ~1 sec
    }
}
