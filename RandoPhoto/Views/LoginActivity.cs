using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Presenters;

namespace RandoPhoto.Views
{
    [Activity(Label = "LoginActivity", VisibleInLauncher = false)]
    public class LoginActivity : Activity, ILoginView
    {
        public LoginActivity()
        {
            Program.Container.ResolveToObject<ILoginView, LoginActivity>(this);
            Program.Container.Resolve(typeof(LoginPresenter));
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
            SetContentView(R.Layouts.login_view);
        }

        public new event EventHandler<EventArgs> OnCreateView;
    }
}
