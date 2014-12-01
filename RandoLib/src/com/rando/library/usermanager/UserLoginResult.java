package com.rando.library.usermanager;
/**
 * Created by SERGant on 11.10.2014.
 */

import com.rando.library.usermanager.UserInterfaces.IUserLoginResult;
import com.rando.library.usermanager.UserInterfaces.ILoggedUser;
import com.rando.library.usermanager.UserInterfaces.LOGINRESULT;

public class UserLoginResult implements IUserLoginResult {
    private ILoggedUser m_loggedUser;
    private LOGINRESULT m_loginResult;

    public UserLoginResult(ILoggedUser loggedUser, LOGINRESULT loginResult) {
        m_loggedUser = loggedUser;
        m_loginResult = loginResult;
    }

    @Override
    public LOGINRESULT GetUserLoginResult() {
        return m_loginResult;
    }

    @Override
    public ILoggedUser GetLoggedUser() {
        return m_loggedUser;
    }
}
