package com.rando.library.usermanager;

import java.io.File;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rando.library.LibManager;
import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.ParseConstants;
import com.rando.library.usermanager.UserInterfaces.IGetTotaLikesCallback;
import com.rando.library.usermanager.UserInterfaces.IGetTotalLikesResult;
import com.rando.library.usermanager.UserInterfaces.IGetTotalRandosCallback;
import com.rando.library.usermanager.UserInterfaces.IGetTotalRandosResult;
import com.rando.library.usermanager.UserInterfaces.IUser;
import com.rando.library.usermanager.UserInterfaces.IUserGetAvatarCallback;
import com.rando.library.usermanager.UserInterfaces.IUserGetAvatarResult;

/**
 * Created by SERGant on 11.10.2014.
 */

public class User implements IUser {
    private String m_userID;
    private String m_userName;
    private File m_avatar = null;
    private String m_avatar_url;
    private String m_avatarParseFileId;

    public User(String userID, String userName) {
        m_userID = userID;
        m_userName = userName;
    }

    public User(String userID, String userName,  File avatar) {
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
		return m_avatar_url;
	}

	@Override
	public File GetAvatar(final IUserGetAvatarCallback userGetAvatarCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_USER);
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		query.getInBackground(m_userID, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject user, ParseException e) { //indian code alert!
				IUserGetAvatarResult userGetAvatarResult = null;
				if (e==null){
					if(user.containsKey(ParseConstants.KEY_FILE)){
					ParseFile avatarParseFile  = user.getParseFile(ParseConstants.KEY_FILE);
					avatarParseFile.getDataInBackground(new GetDataCallback() {
						@Override
						public void done(byte[] byteArray, ParseException e) {
							IUserGetAvatarResult userGetAvatarResult = null;
							if(e==null){
							File avatarFile = LibManager.convertByteToFile("avatar"+m_userID, byteArray);
							m_avatar = avatarFile;
							userGetAvatarResult = new UserGetAvatarResult(avatarFile, GENERALERROR.SUCCESS);
							}
							else {
								userGetAvatarResult = new UserGetAvatarResult(null, LibManager.decodeError(e.getCode()));
							}
							if (userGetAvatarCallback!=null) {
								userGetAvatarCallback.OnUserGetAvatar(userGetAvatarResult);
							}
						}
						
					});
				}
					else {
						userGetAvatarResult = new UserGetAvatarResult(null, GENERALERROR.OBJECTNOTFOUND);
					}
				}
				else {
					userGetAvatarResult = new UserGetAvatarResult(null, LibManager.decodeError(e.getCode()));					
				}
				if (userGetAvatarCallback!=null) {
					userGetAvatarCallback.OnUserGetAvatar(userGetAvatarResult);
				}
			}
		});
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

	@Override
	public void GetTotalRandos(final IGetTotalRandosCallback getTotalRandosCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.whereEqualTo(ParseConstants.KEY_CREATED_BY, m_userID);
		query.whereExists(ParseConstants.KEY_LIKES_ID);
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> listTotalRandos, ParseException e) {
				IGetTotalRandosResult getTotalRandosResult;
				
				if (e== null) {
					int totalRandos = listTotalRandos.size();
					getTotalRandosResult = new GetTotalRandosResult(totalRandos, GENERALERROR.SUCCESS);
				}
				else {
					getTotalRandosResult = new GetTotalRandosResult(LibManager.decodeError(e.getCode()));
				}
				if(getTotalRandosCallback!=null) {getTotalRandosCallback.OnGetTotalRandos(getTotalRandosResult);};
			}
		});
	}

	@Override
	public void GetTotalLikes(final IGetTotaLikesCallback getTotalLikesCallback) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PHOTO);
		query.whereEqualTo(ParseConstants.KEY_CREATED_BY, m_userID);
		query.whereExists(ParseConstants.KEY_LIKES_ID);
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> listTotalRandos, ParseException e) {
				IGetTotalLikesResult getTotalLikesResult;
				
				if (e== null) {
					int totalRandos = 0;
					for (ParseObject photo : listTotalRandos) {
						totalRandos = totalRandos + photo.getList(ParseConstants.KEY_LIKES_ID).size();	
					}
					getTotalLikesResult = new GetTotalLikesResult(totalRandos, GENERALERROR.SUCCESS);
				}
				else {
					getTotalLikesResult = new GetTotalLikesResult(LibManager.decodeError(e.getCode()));
				}
				if(getTotalLikesCallback!=null) {getTotalLikesCallback.OnGetTotalRandos(getTotalLikesResult);};
			}
		});
	}
	
	
}
