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
        private EditText m_tvLogin;
        private EditText m_tvPassword;
        private Button m_btnLogin;
        private Button m_btnRegister;

        public LoginActivity()
        {
            m_tvLogin = null;
            m_tvPassword = null;
            m_btnLogin = null;
            m_btnRegister = null;

            Program.Container.ResolveToObject<ILoginView, LoginActivity>(this);
            Program.Container.Resolve(typeof(LoginPresenter));
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
            SetContentView(R.Layouts.login_view);
            
            m_tvLogin = FindViewById<EditText>(R.Ids.etEmail);
            m_tvPassword = FindViewById<EditText>(R.Ids.etPassword);
            
            m_btnLogin = FindViewById<Button>(R.Ids.btnLogin);
            m_btnLogin.Click += (sender, args) =>
            {
                LoginEventArgs loginArgs = new LoginEventArgs();
                loginArgs.UserLogin = m_tvLogin.Text.ToString().Trim();
                loginArgs.UserPassword = m_tvPassword.Text.ToString().Trim();

                if (loginArgs.UserLogin.IsEmpty() || loginArgs.UserPassword.IsEmpty())
                {
                    // Show error tooltip
                }
                else
                {
                    if (this.OnLoginUser != null) this.OnLoginUser(this, loginArgs);
                }
            };

            m_btnRegister = FindViewById<Button>(R.Ids.btnRegister);
            m_btnRegister.Click += (sender, args) =>
            {
                if (this.OnRegisterUser != null) this.OnRegisterUser(this, EventArgs.Empty);
            };
        }

        public event EventHandler<EventArgs> OnCreateViewEvent;
        public event EventHandler<LoginEventArgs> OnLoginUser;
        public event EventHandler<EventArgs> OnRegisterUser;
    }
}
