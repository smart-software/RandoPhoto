package randojavalib.jar;

import java.util.EventObject;

import randojavalib.jar.Interfaces.IUser;
import randojavalib.jar.Interfaces.IUserRegisterResult;
import randojavalib.jar.Interfaces.REGISTERRESULT;

public class UserRegisterEvent extends EventObject implements IUserRegisterResult {

	private IUser user;
	REGISTERRESULT result;
	
	public UserRegisterEvent(IUser user, REGISTERRESULT result) {
		super(user);
		this.user = user;
		this.result = result;
	}
	

	@Override
	public REGISTERRESULT GetUserRegisterResult() {
		return this.result;
	}

	@Override
	public IUser GetRegisteredUser() {
		return this.user;
	}

}
