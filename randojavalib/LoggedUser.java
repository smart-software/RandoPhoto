package randojavalib.jar;


import randojavalib.jar.UserInterfaces.ILoggedUser;

public class LoggedUser extends User implements ILoggedUser {
	

	@SuppressWarnings("unused")
	private String m_userID;
	@SuppressWarnings("unused")
	private String m_userName;

	public LoggedUser(String userID, String userName, String userEmail) {
		super(userID, userName);
		m_userID = userID;
		m_userName = userName;
		m_userEmail = userEmail;
		
	}

	private String m_userEmail;
	
	
	
	public String GetUserEmail() {
		return m_userEmail;
	}
	
	@Override
	public void SetUserEmail(String userEmail) {
		m_userEmail = userEmail;
	}
}