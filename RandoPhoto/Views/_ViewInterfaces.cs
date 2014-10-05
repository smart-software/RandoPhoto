﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RandoPhoto.Views
{
    public interface IBaseView
    {
        void SetContent();
        event EventHandler<EventArgs> OnCreateViewEvent;
    }

    interface IMainView : IBaseView
    {
    }

    public class LoginEventArgs : EventArgs
    {
        public string UserName { get; set; }
        public string UserPassword { get; set; }
    }

    public enum LOGINERROR
    {
        UNDERFINED, BADPASSWORD, NOTEXIST
    }

    interface ILoginView : IBaseView
    {
        void ShowLoginError(LOGINERROR loginError);

        event EventHandler<LoginEventArgs> OnLoginUserClick;
        event EventHandler<EventArgs> OnRegisterUserClick;
    }

    public class RegisterEventArgs : EventArgs
    {
        public string UserName { get; set; }
        public string UserEmail { get; set; }
        public string UserPassword { get; set; }
    }

    public enum REGISTERERROR
    {
        UNDEFINED, USEREXISTS, BADPASSWORD
    }

    interface IRegisterView : IBaseView
    {
        void ShowRegisterError(REGISTERERROR registerError);

        event EventHandler<RegisterEventArgs> OnRegisterUserClick;
    }
}
