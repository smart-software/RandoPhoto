using System;
using System.Collections.Generic;
using Android.App;
using Android.Util;
using Dot42.Manifest;
using SimpleIOC;
using RandoPhoto.Views;
using RandoPhoto.Presenters;

namespace RandoPhoto
{
    [Application("RandoPhoto")]
    public class Program : Application
    {
        public static CSimpleIoCContainer Container { get; private set; }

        private void Bootstrap()
        {
            // Create IoC Container
            Program.Container = new CSimpleIoCContainer();
            Program.Container.Register<IMainView, MainActivity>(null);
            Program.Container.Register<MainPresenter, MainPresenter>(new List<Type>() 
            {
                typeof(IMainView)
            });
        }

        public override void OnCreate()
        {
            base.OnCreate();
            Bootstrap();
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

