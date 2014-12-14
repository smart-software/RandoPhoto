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
import com.smartsoftwareagency.randophoto.common.ObjectFactory;

/**
 * Created by SERGant on 08.12.2014.
 */
public class LoginActivity extends Activity implements View.OnClickListener, UserInterfaces.IUserLoginCallback {
    private EditText m_etLogin;
    private EditText m_etPassword;

    public LoginActivity() {
        super();

        m_etLogin = null;
        m_etPassword = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_etLogin = (EditText) findViewById(R.id.etNickname);
        m_etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                loginUser();
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void loginUser() {
        String login = m_etLogin.getText().toString().trim();
        String password = m_etPassword.getText().toString().trim();

        if(login.isEmpty() || password.isEmpty()) {
            showErrorMessage(R.string.login_error_nodata_text);
        }
        else {
            UserInterfaces.IUserManager userManager = ObjectFactory.getUserManager();
            userManager.LogInUser(login, password, this);
        }
    }

    private void registerUser() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showErrorMessage(int textID) {
        Toast toast = Toast.makeText(this, textID, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void OnUserLogin(UserInterfaces.IUserLoginResult iUserLoginResult) {
        UserInterfaces.LOGINRESULT loginResult = iUserLoginResult.GetUserLoginResult();

        if(loginResult == UserInterfaces.LOGINRESULT.SUCCESS) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            switch (loginResult) {
                case BADPASSWORD:
                    showErrorMessage(R.string.login_error_badpassword_text);
                    break;
                case NOTEXIST:
                    showErrorMessage(R.string.login_error_notexist_text);
                    break;
                case UNDEFINED:
                    showErrorMessage(R.string.login_error_underfined_text);
                    break;
            }
        }
    }
}
