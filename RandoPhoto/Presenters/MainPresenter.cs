using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Com.Rando.Library;
using RandoPhoto.Presenters;
using RandoPhoto.Views;


namespace RandoPhoto.Presenters
{
    public sealed class MainPresenter : IBasePresenter
    {
        private IMainView m_mainView;

        public void SetDependency(Dictionary<Type, object> dependencyList)
        {
            m_mainView = dependencyList[typeof(IMainView)] as IMainView;
            m_mainView.OnCreateViewEvent += OnCreateView;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            UserInterfaces.IUserManager userManager = Program.Container.Resolve(typeof(UserInterfaces.IUserManager))
                as UserInterfaces.IUserManager;
            UserInterfaces.ILoggedUser loggedUser = userManager.GetCurrentUser();

            if (loggedUser != null)
            {
                m_mainView.SetContent();
            }
            else
            {
                IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
                viewManager.ShowView<ILoginView, LoginPresenter>(false);
            }
        }
    }
}
