package heracles.soccergo.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import heracles.soccergo.MainActivity;
import heracles.soccergo.R;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;
    private Dialog progressDialog;
    private Button btnRegister;

    // MOB短信验证属性
    private static String APPKEY = "16ec22ed56244";
    private static String APPSECRET = "bd81b89c1084304dd39bf806946eed5e";

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
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
//                showProgressDialog();
            }
        });
    }

    private void getWidget()
    {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    private void showProgressDialog()
    {
        progressDialog = new Dialog(LoginActivity.this, R.style.ProgBarTheme_AlphaBackground);
        progressDialog.setContentView(R.layout.dialog_progbar);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void closeProgressDialog()
    {
        progressDialog.dismiss();
    }

}
