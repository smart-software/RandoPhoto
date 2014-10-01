using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using RandoPhoto.Views;

namespace RandoPhoto.Presenters
{
    public class RegisterPresenter : IBasePresenter
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
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            m_registerView.SetContent();
        }
    }
}
