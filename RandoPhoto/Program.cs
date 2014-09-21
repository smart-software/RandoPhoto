using Android.App;
using Android.Util;
using Dot42.Manifest;
using SimpleIOC;
using RandoPhoto.Views;

namespace RandoPhoto
{
    [Application("RandoPhoto")]
    public class Program : Application
    {
        public static CSimpleIoCContainer IoCContainer = new CSimpleIoCContainer();

        private void Bootstrap()
        {
            IoCContainer.Register<IMainView, MainActivity>(null);
        }

        public override void OnCreate()
        {
            base.OnCreate();
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

