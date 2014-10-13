using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Com.Rando.Library;
using RandoPhoto.Views;

namespace RandoPhoto.Presenters
{
    public class RegisterPresenter : IBasePresenter, UserInterfaces.IUserRegisterCallback
    {
        private IRegisterView m_registerView;

        public RegisterPresenter()
        {
            m_registerView = null;
        }

        public void SetDependency(Dictionary<Type, object> dependencyList)
        {
            m_registerView = dependencyList[typeof(IRegisterView)] as IRegisterView;

            m_registerView.OnCreateViewEvent += OnCreateView;
            m_registerView.OnRegisterUserClick += OnRegisterUserClick;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            m_registerView.SetContent();
        }

        public void OnRegisterUserClick(object sender, RegisterEventArgs e)
        {
            UserInterfaces.IUserManager userManager = Program.Container.Resolve(typeof(UserInterfaces.IUserManager))
                as UserInterfaces.IUserManager;
            userManager.RegisterUser(e.UserName, e.UserPassword, e.UserEmail, this);
        }

        public void OnUserRegister(UserInterfaces.IUserRegisterResult userRegisterResult)
        {
            UserInterfaces.REGISTERRESULT registerResult = userRegisterResult.GetUserRegisterResult();

            if (registerResult == UserInterfaces.REGISTERRESULT.SUCCESS)
            {
                IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
                viewManager.ShowView<IMainView, MainPresenter>(false);
            }
            else
            {
                if (registerResult == UserInterfaces.REGISTERRESULT.USEREXISTS)
                    m_registerView.ShowRegisterError(REGISTERERROR.USEREXISTS);
                else if (registerResult == UserInterfaces.REGISTERRESULT.BADPASSWORD)
                    m_registerView.ShowRegisterError(REGISTERERROR.BADPASSWORD);
                else
                    m_registerView.ShowRegisterError(REGISTERERROR.UNDEFINED);
            }
        }
    }
}
