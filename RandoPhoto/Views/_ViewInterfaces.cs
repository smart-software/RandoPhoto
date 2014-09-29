using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RandoPhoto.Views
{
    public interface IBaseView
    {
        void SetContent();
        event EventHandler<EventArgs> OnCreateView;
    }

    interface IMainView : IBaseView
    {
    }

    interface ILoginView : IBaseView
    {
    }
}
