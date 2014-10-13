using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android;
using Android.App;
using Android.Content;
using Android.Widget;
using RandoPhoto.Presenters;

namespace RandoPhoto.Views
{
    public interface IViewManager
    {
        void ShowView<TView, TPresenter>(bool CanGoBack = true)
            where TView : class, IBaseView
            where TPresenter : class, new();
        void AddTabView<TView, TPresenter>(string headerName)
            where TView : class, ITabView
            where TPresenter : class, new();

        string GetStringResource(int resourceID);

        IBaseView CurrentView { get; set; }
    }

    public sealed class ViewManager : IViewManager
    {
        public ViewManager()
        {
            this.CurrentView = null;
        }

        public void ShowView<TView, TPresenter>(bool CanGoBack = true)
            where TView : class, IBaseView
            where TPresenter : class, new()
        {
            Type viewType = Program.Container.GetConcreteType(typeof(TView));
            if (viewType != null)
            {
                Activity currentActivity = this.CurrentView as Activity;
                Intent intent = new Intent(currentActivity, viewType);
                if (!CanGoBack)
                {
                    intent.AddFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.AddFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
                currentActivity.StartActivity(intent);
            }
        }

        public void AddTabView<TView, TPresenter>(string headerName)
            where TView : class, ITabView
            where TPresenter : class, new()
        {
            Activity currActivity = this.CurrentView as Activity;
            TabHost tabHost = currActivity.FindViewById<TabHost>(Android.R.Id.Tabhost);
            Type viewType = Program.Container.GetConcreteType(typeof(TView));

            if ((tabHost != null) && (viewType != null))
            {
                TabHost.TabSpec tabSpec = tabHost.NewTabSpec(viewType.Name);
                tabSpec.SetIndicator(headerName);
                tabSpec.SetContent(new Intent(currActivity, viewType));
                tabHost.AddTab(tabSpec);
            }
        }

        public string GetStringResource(int resourceID)
        {
            Activity currActivity = this.CurrentView as Activity;
            return currActivity.GetResources().GetString(resourceID);
        }

        public IBaseView CurrentView { get; set; }
    }
}
