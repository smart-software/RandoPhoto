package randojavalib.jar;

import randojavalib.jar.UserInterfaces.IUser;
import randojavalib.jar.UserInterfaces.IUserRegisterResult;
import randojavalib.jar.UserInterfaces.REGISTERRESULT;



public class UserRegisterEvent implements IUserRegisterResult {
	private IUser m_user;
	private REGISTERRESULT m_registerResult;
	
	public UserRegisterEvent(IUser user, REGISTERRESULT registerResult) {
		m_user = user;
		m_registerResult = registerResult;
	}

	@Override
	public REGISTERRESULT GetUserRegisterResult() {
		return m_registerResult;
	}

	@Override
	public IUser GetRegisteredUser() {
		return m_user;
	}
}
