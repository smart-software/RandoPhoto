package randojavalib.jar;


import randojavalib.jar.UserInterfaces.ILoggedUser;

public class LoggedUser extends User implements ILoggedUser {
	



	public LoggedUser(String userID, String userName, String userEmail) {
		super(userID, userName);
		m_userEmail = userEmail;
		 
	}

	private String m_userEmail;
	
	
	 @Override
	public String GetUserEmail() {
		return m_userEmail;
	}
	
	@Override
	public void SetUserEmail(String userEmail) {
		m_userEmail = userEmail;
	}
}