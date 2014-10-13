using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using RandoPhoto.Views;

namespace RandoPhoto.Presenters.TabPresenters
{
    public class MyRandoTabPresenter : IBasePresenter
    {
        private IMyRandoTabView m_myRandoView;

        public MyRandoTabPresenter()
        {
            m_myRandoView = null;
        }

        public void SetDependency(Dictionary<Type, object> dependencyList)
        {
            m_myRandoView = dependencyList[typeof(IMyRandoTabView)] as IMyRandoTabView;
        }
    }
}
