package randojavalib.jar;

import java.util.EventObject;

import randojavalib.jar.Interfaces.ILoggedUser;
import randojavalib.jar.Interfaces.IUserLoginResult;
import randojavalib.jar.Interfaces.LOGINRESULT;

public class UserLoginEvent extends EventObject implements IUserLoginResult {
	
	ILoggedUser user;
	LOGINRESULT result;

	public UserLoginEvent(ILoggedUser user, LOGINRESULT result) {
		super(user);
		this.user = user;
		this.result = result;
	}

	@Override
	public LOGINRESULT GetUserLoginResult() {
		return this.result;
	}

	@Override
	public ILoggedUser GetLoggedUser() {
		return this.user;
	}

}
