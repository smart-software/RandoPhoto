package com.rando.library;

/**
 * Created by SERGant on 11.10.2014.
 */

import com.rando.library.UserInterfaces.IUserRegisterResult;
import com.rando.library.UserInterfaces.IUser;
import com.rando.library.UserInterfaces.REGISTERRESULT;

public class UserRegisterResult implements IUserRegisterResult {
    private IUser m_user;
    private REGISTERRESULT m_registerResult;

    public UserRegisterResult(IUser user, REGISTERRESULT registerResult) {
        m_user = user;
        m_registerResult = registerResult;
    }

    @Override
    public REGISTERRESULT GetUserRegisterResult() {
        return m_registerResult;
    }

    @Override
    public IUser GetRegisteredUser() {
        return m_user;
    }
}
