package randojavalib.jar;

import randojavalib.jar.Interfaces.ILoggedUser;

public  class LoggedUser implements ILoggedUser {

	private String mUserID;
	private String mUsername;
	private String mEmail;
	
	
	@Override
	public String GetUID() {
		return mUserID;
	}

	@Override
	public String GetName() {
		return mUsername;
	}

	@Override
	public void SetName(String userName) {
		mUsername = userName;
	}

	@Override
	public void SetId(String UserId) {
		mUserID = UserId;
	}

	@Override
	public void SetEmail(String email) {
		mEmail = email;
	}

}
