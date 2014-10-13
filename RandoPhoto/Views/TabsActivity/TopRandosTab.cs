using System;
using Android.App;
using Android.Os;
using Android.Widget;
using Dot42;
using Dot42.Manifest;

namespace RandoPhoto.Views.TabsActivity
{
    [Activity(Label = "TopRandosTab", VisibleInLauncher = false)]
    public class TopRandosTab : Activity
    {
        protected override void OnCreate(Bundle savedInstance)
        {
            base.OnCreate(savedInstance);
            //SetContentView(R.Layouts.MyRandoTabActivity_Layout);
        }
    }
}
