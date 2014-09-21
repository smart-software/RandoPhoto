using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Views;
using RandoPhoto.Presenters;

namespace RandoPhoto
{
    [Activity]
    public class MainActivity : Activity, IMainView
    {
        public MainActivity() 
            : base()
        {
            Program.Container.RegisterObject<IMainView, MainActivity>(this);
            // Надо инициализировать MainPresenter.
            // В результате инициализации BL по отображению MainView лежит на созданном MainPresenter.
            Program.Container.Resolve(typeof(MainPresenter));
        }

        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
            // !!!! Надо оповестить Presenter, что Activity создается через событие !!!!
            if (this.OnCreateView != null) this.OnCreateView(this, new EventArgs()); 
        }

        public void ShowView()
        {
            SetContentView(R.Layouts.main_view);
        }

        public new event EventHandler<EventArgs> OnCreateView;
    }
}
