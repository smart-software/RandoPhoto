using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RandoPhoto.Stubs
{
    internal interface IUser
    {
    }

    internal interface ILoggedUser : IUser
    {
    }

    internal interface IUserManager
    {
        ILoggedUser GetCurrentUser();
    }

    internal class UserManagerStub : IUserManager
    {
        public ILoggedUser GetCurrentUser()
        {
            return null;
        }
    }
}
