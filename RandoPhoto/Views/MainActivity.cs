using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Views;
using RandoPhoto.Presenters;
using Android.Content;

namespace RandoPhoto
{
    [Activity]
    public class MainActivity : Activity, IMainView
    {
        public MainActivity() 
            : base()
        {
            Program.Container.ResolveToObject<IMainView, MainActivity>(this);
            Program.Container.Resolve(typeof(MainPresenter));
        }

        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
           
            IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
            viewManager.CurrentView = this;

            if (this.OnCreateView != null) this.OnCreateView(this, new EventArgs());
        }

        public void SetContent()
        {
            SetContentView(R.Layouts.main_view);
        }

        public new event EventHandler<EventArgs> OnCreateView;
    }
}
