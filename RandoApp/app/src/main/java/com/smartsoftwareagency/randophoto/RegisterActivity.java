package com.smartsoftwareagency.randophoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rando.library.usermanager.UserInterfaces;
import com.rando.library.usermanager.UserManager;

/**
 * Created by SERGant on 08.12.2014.
 */
public class RegisterActivity extends Activity implements View.OnClickListener, UserInterfaces.IUserRegisterCallback {
    private EditText m_etNickname;
    private EditText m_etPassword;
    private EditText m_etEmail;

    public RegisterActivity() {
        super();

        m_etNickname = null;
        m_etPassword = null;
        m_etEmail = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        m_etNickname = (EditText) findViewById(R.id.etNickname);
        m_etPassword = (EditText) findViewById(R.id.etPassword);
        m_etPassword = (EditText) findViewById(R.id.etEmail);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String userName = m_etNickname.getText().toString().trim();
        String userPass = m_etPassword.getText().toString().trim();
        String userEmail = m_etEmail.getText().toString().trim();

        if(userName.isEmpty() || userPass.isEmpty() || userEmail.isEmpty()) {
            showErrorMessage(R.string.register_error_nodata_text);
        }
        else {
            UserInterfaces.IUserManager userManager = ObjectFactory.getUserManager();
            userManager.RegisterUser(userName, userPass, userEmail, null,  this);
        }
    }

    private void showErrorMessage(int textID) {
        Toast toast = Toast.makeText(this, textID, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void OnUserRegister(UserInterfaces.IUserRegisterResult iUserRegisterResult) {
        UserInterfaces.REGISTERRESULT registerResult = iUserRegisterResult.GetUserRegisterResult();

        if(registerResult == UserInterfaces.REGISTERRESULT.SUCCESS) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            switch (registerResult) {
                case BADPASSWORD:
                    showErrorMessage(R.string.register_error_badpassword_text);
                    break;
                case USEREXISTS:
                    showErrorMessage(R.string.register_error_userexist_text);
                    break;
                case UNDEFINED:
                default:
                    showErrorMessage(R.string.register_error_underfined_text);
                    break;
            }
        }
    }
}
