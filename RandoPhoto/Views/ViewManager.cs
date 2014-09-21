using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android;
using Android.App;

namespace RandoPhoto.Views
{
    public sealed class ViewManager
    {
        private Activity m_currentActivity;

        private ViewManager()
        {
            m_currentActivity = null;
        }

        public void ShowView()
        {
            m_currentActivity.StartActivity(null);
        }
    }
}
