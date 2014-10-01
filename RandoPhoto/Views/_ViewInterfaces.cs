using System;
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
        public string UserLogin { get; set; }
        public string UserPassword { get; set; }
    }

    interface ILoginView : IBaseView
    {
        event EventHandler<LoginEventArgs> OnLoginUser;
        event EventHandler<EventArgs> OnRegisterUser;
    }

    interface IRegisterView : IBaseView
    {
    }
}
