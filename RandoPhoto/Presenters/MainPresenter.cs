using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using RandoPhoto.Presenters;
using RandoPhoto.Views;

namespace RandoPhoto.Presenters
{
    using RandoPhoto.Stubs;

    public sealed class MainPresenter : IBasePresenter
    {
        private IMainView m_mainView;

        public void SetDependency(Dictionary<Type, object> dependencyList)
        {
            m_mainView = dependencyList[typeof(IMainView)] as IMainView;
            m_mainView.OnCreateView += OnCreateView;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            IUserManager userManager = (IUserManager)Program.Container.Resolve(typeof(IUserManager));
            ILoggedUser loggedUser = userManager.GetCurrentUser();

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
