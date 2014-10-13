using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Presenters;
using Android.View;

namespace RandoPhoto.Views
{
    [Activity(Label = "LoginActivity", VisibleInLauncher = false)]
    public class LoginActivity : Activity, ILoginView
    {
        private EditText m_etLogin;
        private EditText m_etPassword;
        private Button m_btnLogin;
        private Button m_btnRegister;

        public LoginActivity()
        {
            m_etLogin = null;
            m_etPassword = null;
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
            
            m_etLogin = FindViewById<EditText>(R.Ids.etNickname);
            m_etPassword = FindViewById<EditText>(R.Ids.etPassword);
            
            m_btnLogin = FindViewById<Button>(R.Ids.btnLogin);
            m_btnLogin.Click += (sender, args) =>
            {
                LoginEventArgs loginArgs = new LoginEventArgs();
                loginArgs.UserName = m_etLogin.Text.ToString().Trim();
                loginArgs.UserPassword = m_etPassword.Text.ToString().Trim();

                if (loginArgs.UserName.IsEmpty() || loginArgs.UserPassword.IsEmpty())
                {
                    // Show error tooltip
                }
                else
                {
                    if (this.OnUserLoginClick != null) this.OnUserLoginClick(this, loginArgs);
                }
            };

            m_btnRegister = FindViewById<Button>(R.Ids.btnRegister);
            m_btnRegister.Click += (sender, args) =>
            {
                if (this.OnUserRegisterClick != null) this.OnUserRegisterClick(this, EventArgs.Empty);
            };
        }

        public void ShowLoginError(LOGINERROR loginError)
        {
            int login_text_id = -1;
            switch (loginError)
            {
                case LOGINERROR.BADPASSWORD:
                    login_text_id = R.Strings.login_error_badpassword_text;
                    break;
                case LOGINERROR.NOTEXIST:
                    login_text_id = R.Strings.login_error_notexist_text;
                    break;
                default:
                    login_text_id = R.Strings.login_error_underfined_text;
                    break;
            }

            Toast toast = Toast.MakeText(this, login_text_id, Toast.LENGTH_SHORT);
            toast.SetGravity(Gravity.CENTER, 0, 0);
            toast.Show();
        }

        public event EventHandler<EventArgs> OnCreateViewEvent;
        public event EventHandler<LoginEventArgs> OnUserLoginClick;
        public event EventHandler<EventArgs> OnUserRegisterClick;
    }
}
