package heracles.soccergo.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;
    private Button btnRegister;
    private EditText etUser;
    private EditText etPassword;
    private CheckBox cbAutoLogin;

    private Handler handler = new Handler();

    // MOB
    private static String APPKEY = "16ba2c21962c8";
    private static String APPSECRET = "84e669b4aab05998f4999887433bf2a6";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();

        SharedPreferences pref = getSharedPreferences("user",0);
        if(pref.getString("autoflag","").equals("1")){
            etUser.setText(pref.getString("username",""));
            etPassword.setText(pref.getString("password",""));
            btnLogin.performClick();
        }
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
                if (user.isEmpty())
                {
                    etUser.setError("请输入用户名");
                } else if (password.isEmpty())
                {
                    etPassword.setError("请输入密码");
                } else if (password.length() < 6)
                {
                    etPassword.setError("需要密码长度大于6");
                } else
                {
                    SharedPreferences pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    if(cbAutoLogin.isChecked()){
                        editor.putString("username", user);
                        editor.putString("password", password);
                        editor.putString("autoflag","1");
                        editor.commit();
                    }
                    else {
                        editor.putString("autoflag","0");
                        editor.commit();
                    }
                    new LoginThread(LoginActivity.this, user, password, handler).start();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //MOB兼容性
                if (Build.VERSION.SDK_INT >= 23)
                {
                    int readPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
                    int receiveSms = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
                    int readSms = checkSelfPermission(Manifest.permission.READ_SMS);
                    int readContacts = checkSelfPermission(Manifest.permission.READ_CONTACTS);
                    int readSdcard = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                    int requestCode = 0;
                    ArrayList<String> permissions = new ArrayList<String>();
                    if (readPhone != PackageManager.PERMISSION_GRANTED)
                    {
                        requestCode |= 1 << 0;
                        permissions.add(Manifest.permission.READ_PHONE_STATE);
                    }
                    if (receiveSms != PackageManager.PERMISSION_GRANTED)
                    {
                        requestCode |= 1 << 1;
                        permissions.add(Manifest.permission.RECEIVE_SMS);
                    }
                    if (readSms != PackageManager.PERMISSION_GRANTED)
                    {
                        requestCode |= 1 << 2;
                        permissions.add(Manifest.permission.READ_SMS);
                    }
                    if (readContacts != PackageManager.PERMISSION_GRANTED)
                    {
                        requestCode |= 1 << 3;
                        permissions.add(Manifest.permission.READ_CONTACTS);
                    }
                    if (readSdcard != PackageManager.PERMISSION_GRANTED)
                    {
                        requestCode |= 1 << 4;
                        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }

                // 初始化短信SDK
                SMSSDK.initSDK(LoginActivity.this, APPKEY, APPSECRET, true);
                // 打开注册页面
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
                            // 提交用户信息
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
            Toast.makeText(this,"注册成功   "+"手机号："+phone+"   "+"国家："+country,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, Register2Activity.class);
        intent.putExtra("tel",phone);
        startActivity(intent);
    }

    private void getWidget()
    {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbAutoLogin = (CheckBox) findViewById(R.id.cbAutoLogin);

    }

}
