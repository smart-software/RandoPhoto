using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Com.Rando.Library;
using RandoPhoto.Views;

namespace RandoPhoto.Presenters
{
    public class LoginPresenter : IBasePresenter, UserInterfaces.IUserLoginCallback
    {
        private ILoginView m_loginView;

        public LoginPresenter()
        {
            m_loginView = null;
        }

        public void SetDependency(Dictionary<Type, object> dependencyList)
        {
            m_loginView = dependencyList[typeof(ILoginView)] as ILoginView;
            m_loginView.OnCreateViewEvent += OnCreateView;
            m_loginView.OnLoginUserClick += OnLoginUserClick;
            m_loginView.OnRegisterUserClick += OnRegisterUserClick;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            m_loginView.SetContent();
        }

        public void OnRegisterUserClick(object sender, EventArgs e)
        {
            IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
            viewManager.ShowView<IRegisterView, RegisterPresenter>();
        }

        public void OnLoginUserClick(object sender, LoginEventArgs e)
        {
            UserInterfaces.IUserManager userManager = Program.Container.Resolve(typeof(UserInterfaces.IUserManager))
                as UserInterfaces.IUserManager;
            userManager.LogInUser(e.UserName, e.UserPassword, this);
        }

        public void OnUserLogin(UserInterfaces.IUserLoginResult loginResult)
        {
            UserInterfaces.LOGINRESULT logRes = loginResult.GetUserLoginResult();

            if (logRes == UserInterfaces.LOGINRESULT.SUCCESS)
            {
                IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
                viewManager.ShowView<IMainView, MainPresenter>(false);
            }
            else
            {
                if (logRes == UserInterfaces.LOGINRESULT.BADPASSWORD) m_loginView.ShowLoginError(LOGINERROR.BADPASSWORD);
                else if (logRes == UserInterfaces.LOGINRESULT.NOTEXIST) m_loginView.ShowLoginError(LOGINERROR.NOTEXIST);
                else m_loginView.ShowLoginError(LOGINERROR.UNDERFINED);
            }
        }
    }
}
