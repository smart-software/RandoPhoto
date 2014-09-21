using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimpleIOC;
using RandoPhoto.Views;

namespace RandoPhoto.Presenters
{
    public sealed class MainPresenter : IContainerObject
    {
        private IMainView m_mainView;

        public void SetDependency(Dictionary<Type, object> dependencyList)
        {
            m_mainView = dependencyList[typeof(IMainView)] as IMainView;
            m_mainView.OnCreateView += OnCreateView;
        }

        public void OnCreateView(object sender, EventArgs e)
        {
            // Тут проверяем, залогинен ли пользователь. Если да, то все ОК, вызываем 
            m_mainView.ShowView();
        }
    }
}
