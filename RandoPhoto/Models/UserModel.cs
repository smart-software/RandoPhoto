using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RandoPhoto.Models.UserModel
{
    public class User : IUser
    {
        public int UID
        {
            get { throw new NotImplementedException(); }
        }

        public string UserName
        {
            get
            {
                throw new NotImplementedException();
            }
            set
            {
                throw new NotImplementedException();
            }
        }

        public string UserEmail
        {
            get { throw new NotImplementedException(); }
        }
    }

    public class LoggedUser : User, ILoggedUser
    {
    }

    public class UserManager : IUserManager
    {
        public UserManager()
        {
        }

        public ILoggedUser GetCurrentUser()
        {
            throw new NotImplementedException();
        }

        public void LogIn(string userName, string userPassword, Action<IUserLoginResult> loginCallback)
        {
            throw new NotImplementedException();
        }

        public void LogOffUser()
        {
            throw new NotImplementedException();
        }

        public void RegisterUser(string userName, string userEmail, string userPassword, 
            Action<IUserRegisterResult> registerCallback)
        {
            throw new NotImplementedException();
        }
    }
}
