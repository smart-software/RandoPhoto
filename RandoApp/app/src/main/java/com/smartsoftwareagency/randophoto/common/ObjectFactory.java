package com.smartsoftwareagency.randophoto.common;

import com.rando.library.usermanager.UserInterfaces.IUserManager;
import com.rando.library.usermanager.UserManager;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoManager;
import com.rando.library.randomanager.RandoManager;

/**
 * Created by SERGant on 08.12.2014.
 */
public final class ObjectFactory {
    private static IUserManager m_userManager= null;
    private static IRandoManager m_randoManager = null;

    private ObjectFactory() {
    }

    public static IUserManager getUserManager() {
        if(m_userManager == null) m_userManager = new UserManager();
        return m_userManager;
    }

    public static IRandoManager getRandoManager() {
        if(m_randoManager == null) m_randoManager = new RandoManager();
        return m_randoManager;
    }
}
