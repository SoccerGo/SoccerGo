package heracles.soccergo.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import heracles.soccergo.MainActivity;
import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;
    private Dialog progressDialog;

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
//                showProgressDialog();
                SMSSDK.initSDK(LoginActivity.this, APPKEY, APPSECRET);

                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler()
                {
                    public void afterEvent(int event, int result, Object data)
                    {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE)
                        {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            // 提交用户信息（此方法可以不调用）
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(LoginActivity.this);
            }
        });
    }

    private void registerUser(String country, String phone)
    {
        if(Test.flag)
            Toast.makeText(LoginActivity.this, "注册成功,电话：" + phone + " " + "国家：" + country, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void getWidget()
    {
        btnLogin = (Button) findViewById(R.id.btnLogin);
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
