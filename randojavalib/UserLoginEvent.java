package randojavalib.jar;


import randojavalib.jar.UserInterfaces.ILoggedUser;
import randojavalib.jar.UserInterfaces.IUserLoginResult;
import randojavalib.jar.UserInterfaces.LOGINRESULT;

public class UserLoginEvent implements IUserLoginResult {
	private ILoggedUser m_loggedUser;
	private LOGINRESULT m_loginResult;

	public UserLoginEvent(ILoggedUser loggedUser, LOGINRESULT loginResult) {
		m_loggedUser = loggedUser;
		m_loginResult = loginResult;
	}

	@Override
	public LOGINRESULT GetUserLoginResult() {
		return m_loginResult;
	}

	@Override
	public ILoggedUser GetLoggedUser() {
		return m_loggedUser;
	}
}
