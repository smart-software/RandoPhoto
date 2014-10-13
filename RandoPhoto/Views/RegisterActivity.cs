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
        private EditText m_etUserName;
        private EditText m_etUserEmail;
        private EditText m_etUserPassword;
        private Button m_btnRegister;

        public RegisterActivity()
        {
            m_etUserName = null;
            m_etUserEmail = null;
            m_etUserPassword = null;
            m_btnRegister = null;

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

            m_etUserName = FindViewById<EditText>(R.Ids.etNickname);
            m_etUserEmail = FindViewById<EditText>(R.Ids.etEmail);
            m_etUserPassword = FindViewById<EditText>(R.Ids.etPassword);

            m_btnRegister = FindViewById<Button>(R.Ids.btnRegister);
            m_btnRegister.Click += (sender, args) =>
            {
                RegisterEventArgs registerArgs = new RegisterEventArgs();
                registerArgs.UserName = m_etUserName.Text.ToString().Trim();
                registerArgs.UserEmail = m_etUserEmail.Text.ToString().Trim();
                registerArgs.UserPassword = m_etUserPassword.Text.ToString().Trim();

                if ((registerArgs.UserEmail.IsEmpty()) || (registerArgs.UserName.IsEmpty())
                    || registerArgs.UserPassword.IsEmpty())
                {

                }
                else
                {
                    if (this.OnRegisterUserClick != null) this.OnRegisterUserClick(this, registerArgs);
                }
            };
        }

        public void ShowRegisterError(REGISTERERROR registerError)
        {
        }

        public event EventHandler<EventArgs> OnCreateViewEvent;
        public event EventHandler<RegisterEventArgs> OnRegisterUserClick;
    }
}
