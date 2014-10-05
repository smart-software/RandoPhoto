using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RandoPhoto.Models.UserModel
{
    public interface IUser
    {
        int UID { get; }
        string UserName { get; set; }
        string UserEmail { get; }
    }

    public interface ILoggedUser : IUser
    {
    }

    public enum LOGINRESULT
    {
        SUCCESS, BADPASSWORD, NOTEXIST, UNDEFINED
    }

    public interface IUserLoginResult
    {
        LOGINRESULT LoginResult { get; }
        ILoggedUser GetLoggedUser(); // Returns null if login failed
    }

    public enum REGISTERRESULT
    {
        SUCCESS, USEREXISTS, BADPASSWORD, EMPTYDATA, UNDEFINED
    }

    public interface IUserRegisterResult
    {
        REGISTERRESULT RegisterResult { get; }
        IUser GetRegisteredUser(); // null if Register failed
    }

    public interface IUserManager
    {
        ILoggedUser GetCurrentUser();

        void LogIn(string userName, string userPassword, Action<IUserLoginResult> loginCallback);
        void LogOffUser();
        void RegisterUser(string userName, string userEmail, string userPassword,
            Action<IUserRegisterResult> registerCallback);
    }
}
