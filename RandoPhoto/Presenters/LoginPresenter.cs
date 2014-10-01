using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using RandoPhoto.Views;

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
            m_loginView.OnLoginUser += OnLoginUser;
            m_loginView.OnRegisterUser += OnRegisterUser;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            m_loginView.SetContent();
        }

        void OnRegisterUser(object sender, EventArgs e)
        {
            IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
            viewManager.ShowView<IRegisterView, RegisterPresenter>();
        }

        void OnLoginUser(object sender, LoginEventArgs e)
        {
            //
        }
    }
}
