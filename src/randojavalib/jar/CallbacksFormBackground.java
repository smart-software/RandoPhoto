package randojavalib.jar;

import randojavalib.jar.Interfaces.ICallbacksFormBackground;
import randojavalib.jar.Interfaces.IUser;

public class CallbacksFormBackground implements ICallbacksFormBackground {

	@Override
	public void loginSucess(boolean state, String exception) {
		// TODO Auto-generated method stub
		//state - �����/������� ����������� ����� � ����
		// exception - �������� ������ (null ��� state == true)
	}



	@Override
	public void registerSucess(boolean state, String exception) {
		// TODO Auto-generated method stub
		//state - �����/������� ����������� ����� � ����
		// exception - �������� ������ (null ��� state == true)
	}

	@Override
	public void returnGetUserByID(IUser user) {
		// TODO Auto-generated method stub
		// user - ������������ ���� �� ���� �� id
	}
}
