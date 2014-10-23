package com.rando.library.usermanager;

import java.io.File;

/**
 * Created by SERGant on 11.10.2014.
 */

public class User implements UserInterfaces.IUser {
    private String m_userID;
    private String m_userName;
    private File m_avatar = null;
    private String m_avatar_url;

    public User(String userID, String userName) {
        m_userID = userID;
        m_userName = userName;
    }

    public User(String userID, String userName, File avatar) {
        m_userID = userID;
        m_userName = userName;
        m_avatar = avatar;
    }
    
    public User(String userID, String userName, String avatarUrl) {
        m_userID = userID;
        m_userName = userName;
        m_avatar_url = avatarUrl;
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

	@Override
	public String GetAvatarUrl() {
		// TODO Auto-generated method stub
		return m_avatar_url;
	}

	@Override
	public File GetAvatar() {
		return m_avatar;
	}

	@Override
	public boolean HasAvatarFile() {
		boolean hasAvatarFile = false;
		if(m_avatar !=null){
			hasAvatarFile = true;
		}
		return hasAvatarFile;
	}
}
