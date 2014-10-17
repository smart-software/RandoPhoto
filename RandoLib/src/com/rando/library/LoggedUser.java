package com.rando.library;
/**
 * Created by SERGant on 11.10.2014.
 */

import java.io.File;

import com.rando.library.UserInterfaces.ILoggedUser;

public class LoggedUser extends User implements ILoggedUser {
    private String m_userEmail;
    
    public LoggedUser(String userID, String userName, String userEmail) {
        super(userID, userName);
        m_userEmail = userEmail;
    }
    
    public LoggedUser(String userID, String userName, String userEmail, String avatarUrl) {
        super(userID, userName, avatarUrl);
        m_userEmail = userEmail;
    }

    public LoggedUser(String userID, String userName, String userEmail, File avatar) {
        super(userID, userName, avatar);
        m_userEmail = userEmail;
    }

    @Override
    public String GetUserEmail() {
        return m_userEmail;
    }

    @Override
    public void SetUserEmail(String userEmail) {
        m_userEmail = userEmail;
    }
}
