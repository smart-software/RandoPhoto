using System;
using System.Collections.Generic;
using Android.App;
using Android.Util;
using Dot42.Manifest;
using SimpleIOC;
using RandoPhoto.Models;
using RandoPhoto.Models.UserModel;
using RandoPhoto.Views;
using RandoPhoto.Presenters;

namespace RandoPhoto
{
    [Application("RandoPhoto", Theme = "@android:style/Theme.NoTitleBar")]
    public class Program : Application
    {
        public static CSimpleIoCContainer Container { get; private set; }

        private void Bootstrap()
        {
            // Create IoC Container
            Program.Container = new CSimpleIoCContainer();

            // Regster UserManager
            Program.Container.Register<IUserManager, UserManager>(null);

            // Register ViewManager
            Program.Container.Register<IViewManager, ViewManager>(null);
            
            // Register view interfaces
            Program.Container.Register<IMainView, MainActivity>(null);
            Program.Container.Register<ILoginView, LoginActivity>(null);
            Program.Container.Register<IRegisterView, RegisterActivity>(null);

            // Register presenter interfaces (LifeCycle.Transient)
            Program.Container.Register<MainPresenter, MainPresenter>(LifeCycle.Transient, new List<Type>() 
            {
                typeof(IMainView)
            });
            Program.Container.Register<LoginPresenter, LoginPresenter>(LifeCycle.Transient, new List<Type>()
            {
                typeof(ILoginView)
            });
            Program.Container.Register<RegisterPresenter, RegisterPresenter>(LifeCycle.Transient, new List<Type>()
            {
                typeof(IRegisterView)
            });
        }

        public override void OnCreate()
        {
            base.OnCreate();

            Bootstrap();
            RandoLibraryManager.InitializeLibrary(this);
        }

        public override void OnConfigurationChanged(Android.Content.Res.Configuration newConfig)
        {
            base.OnConfigurationChanged(newConfig);
        }

        public override void OnLowMemory()
        {
            base.OnLowMemory();
        }

        public override void OnTerminate()
        {
            base.OnTerminate();
        }
    }
}

