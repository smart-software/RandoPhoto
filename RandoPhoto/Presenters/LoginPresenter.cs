using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using RandoPhoto.Views;
using RandoPhoto.Models.UserModel;

namespace RandoPhoto.Presenters
{
    public class LoginPresenter : IBasePresenter
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

        void OnLoginUserClick(object sender, LoginEventArgs e)
        {
            IUserManager userManager = Program.Container.Resolve(typeof(IUserManager)) as IUserManager;
            userManager.LogIn(e.UserName, e.UserPassword, this.OnUserLogin);
        }

        void OnUserLogin(IUserLoginResult loginResult)
        {
            if (loginResult.LoginResult == LOGINRESULT.SUCCESS)
            {
                IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
                viewManager.ShowView<IMainView, MainPresenter>(false);
            }
            else
            {
                switch (loginResult.LoginResult)
                {
                    case LOGINRESULT.BADPASSWORD:
                        m_loginView.ShowLoginError(LOGINERROR.BADPASSWORD);
                        break;
                    case LOGINRESULT.NOTEXIST:
                        m_loginView.ShowLoginError(LOGINERROR.NOTEXIST);
                        break;
                    default:
                        m_loginView.ShowLoginError(LOGINERROR.UNDERFINED);
                        break;
                }
            }
        }
    }
}
