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
        private ImageButton m_btnUserSettings;

        public MainActivity() 
            : base()
        {
            m_btnUserSettings = null;

            Program.Container.ResolveToObject<IMainView, MainActivity>(this);
            Program.Container.Resolve(typeof(MainPresenter));
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
            SetContentView(R.Layouts.main_view);

            TabHost tabHost = FindViewById<TabHost>(Android.R.Id.Tabhost);
            tabHost.Setup();

            m_btnUserSettings = FindViewById<ImageButton>(R.Ids.btnSettings);
            m_btnUserSettings.Click += (sender, args) =>
            {
                if (this.OnUserSettingsClick != null) this.OnUserSettingsClick(this, EventArgs.Empty);
            };
        }

        public event EventHandler<EventArgs> OnCreateViewEvent;
        public event EventHandler<EventArgs> OnUserSettingsClick;
    }
}
