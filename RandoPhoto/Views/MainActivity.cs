using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;
using RandoPhoto.Views;

namespace RandoPhoto
{
    [Activity]
    public class MainActivity : Activity, IMainView
    {
        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
            SetContentView(R.Layouts.main_view);
        }
    }
}
