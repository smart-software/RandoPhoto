using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Presenters;

namespace RandoPhoto.Views
{
    [Activity(Label = "RegisterActivity", VisibleInLauncher = false)]
    public class RegisterActivity : Activity, IRegisterView
    {
        public RegisterActivity()
        {
            Program.Container.ResolveToObject<IRegisterView, RegisterActivity>(this);
            Program.Container.Resolve(typeof(RegisterPresenter));
        }

        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
            IViewManager viewManager = Program.Container.Resolve(typeof(IViewManager)) as IViewManager;
            viewManager.CurrentView = this;

            if (this.OnCreateViewEvent != null) this.OnCreateViewEvent(this, new EventArgs());
        }

        public void SetContent()
        {
            SetContentView(R.Layouts.register_view);
        }

        public event EventHandler<EventArgs> OnCreateViewEvent;
    }
}
