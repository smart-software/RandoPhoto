package com.rando.library;
/**
 * Created by SERGant on 11.10.2014.
 */

import com.rando.library.UserInterfaces.IUser;

public class User implements IUser {
    private String m_userID;
    private String m_userName;

    public User(String userID, String userName) {
        m_userID = userID;
        m_userName = userName;
    }

    @Override
    public String GetUID() {
        return m_userID;
    }

    @Override
    public String GetUserName() {
        return m_userName;
    }

    @Override
    public void SetUserName(String userName) {
        m_userName = userName;
    }
}
