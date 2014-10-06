package randojavalib.jar;

import randojavalib.jar.Interfaces.ILoggedUser;

public class LoggedUser extends User implements ILoggedUser {
	private String mEmail;
	@Override
	public void SetUserEmail(String email) {
		
			mEmail = email;
	}

	
}
