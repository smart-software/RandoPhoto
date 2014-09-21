using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;

namespace RandoPhoto.Views
{
    [Activity(Label = "LoginActivity", VisibleInLauncher = false)]
    public class LoginActivity : Activity, ILoginView
    {
        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
            SetContentView(R.Layouts.login_view);
        }

        public void ShowView()
        {
            throw new NotImplementedException();
        }

        public new event EventHandler<EventArgs> OnCreateView;
    }
}
