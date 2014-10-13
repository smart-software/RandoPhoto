using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Presenters.TabPresenters;

namespace RandoPhoto.Views.TabsActivity
{
    [Activity(Label = "MyRandoTab", VisibleInLauncher = false)]
    public class MyRandoTabView : Activity, IMyRandoTabView
    {
        public MyRandoTabView()
            : base()
        {
            Program.Container.ResolveToObject<IMyRandoTabView, MyRandoTabView>(this);
            Program.Container.Resolve(typeof(MyRandoTabPresenter));
        }

        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
        }

        public void SetContent()
        {
            SetContentView(R.Layouts.my_rando_view);
        }

        public event EventHandler<EventArgs> OnCreateViewEvent;
    }
}
