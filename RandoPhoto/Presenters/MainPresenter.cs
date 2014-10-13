using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Com.Rando.Library;
using RandoPhoto.Presenters;
using RandoPhoto.Presenters.TabPresenters;
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
            m_mainView.OnUserSettingsClick += OnUserSettingsClick;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            UserInterfaces.IUserManager userManager = Program.Container.Resolve(typeof(UserInterfaces.IUserManager))
                as UserInterfaces.IUserManager;
            UserInterfaces.ILoggedUser loggedUser = userManager.GetCurrentUser();
            IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;

            if (loggedUser != null)
            {
                m_mainView.SetContent();
                
                //viewManager.AddTabView<IMyRandoTabView, MyRandoTabPresenter>(viewManager.GetStringResource(R.Strings.my_rando_tab_text));
            }
            else viewManager.ShowView<ILoginView, LoginPresenter>(false);
        }

        public void OnUserSettingsClick(object sender, EventArgs e)
        {
            /* For Debug purposes ONLY */
            UserInterfaces.IUserManager userManager = Program.Container.Resolve(typeof(UserInterfaces.IUserManager))
                as UserInterfaces.IUserManager;
            IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;

            userManager.LogOffUser();
            viewManager.ShowView<ILoginView, LoginPresenter>(false);
        }
    }
}
