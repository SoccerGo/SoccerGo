package heracles.soccergo.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import heracles.soccergo.R;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;
    private Button btnRegister;
    private EditText etUser;
    private EditText etPassword;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();
                if(user.isEmpty())
                {
                    etUser.setError("请输入用户名");
                }
                else if(password.isEmpty())
                {
                    etPassword.setError("请输入密码");
                }
                else if(password.length()<6)
                {
                    etPassword.setError("需要密码长度大于6");
                }
                else
                {
                    new LoginThread(LoginActivity.this,user,password,handler).start();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    private void getWidget()
    {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

}
