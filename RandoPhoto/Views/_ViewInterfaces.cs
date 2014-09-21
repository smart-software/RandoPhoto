using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RandoPhoto.Views
{
    interface IBaseView
    {
        void ShowView();
        event EventHandler<EventArgs> OnCreateView;
    }

    interface IMainView : IBaseView
    {
    }

    interface ILoginView : IBaseView
    {
    }
}
