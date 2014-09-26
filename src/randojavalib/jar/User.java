package randojavalib.jar;

import randojavalib.jar.Interfaces.IUser;


public class User implements IUser {
	private String mUserID;
	private String mUsername;
	private String mEmail;
	
	
	public String GetUID() {
		return mUserID;
	}
	
	public String GetName(){
		return mUsername;
	}
	
	public void SetName(String userName){
		mUsername = userName;
	}
	
	public void SetId(String UserId) {
		mUserID = UserId;
	}
	
	public void SetEmail(String email){
		mEmail = email;
	}
	
}
