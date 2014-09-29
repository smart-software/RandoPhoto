using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android;
using Android.App;
using Android.Content;
using RandoPhoto.Presenters;

namespace RandoPhoto.Views
{
    public interface IViewManager
    {
        void ShowView<TView, TPresenter>(bool CanGoBack = true)
            where TView : class, IBaseView
            where TPresenter : class, new();

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

        public IBaseView CurrentView { get; set; }
    }
}
