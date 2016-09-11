package heracles.soccergo.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;

public class RegisterActivity extends AppCompatActivity
{
    private ImageView ivBack;
    private EditText etTel;
    private Button btnVerify;
    private EditText etPassword;
    private Button btnNext;

    private String mPassword = "";
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initWeight();
    }

    private void initWeight()
    {
        getWeight();
        setWeight();
    }

    private void setWeight()
    {
        ivBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tel = etTel.getText().toString();
                String telRegex = "^[1][3-8]+\\d{9}$";
                if (tel.isEmpty())
                    etTel.setError("请输入手机号");
                else if (!tel.matches(telRegex))
                {
                    etTel.setError("请输入正确的手机号");
                } else
                {
                    mPassword = String.valueOf((int)(Math.random()*10000));
                    new GetMsg().execute();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String password = etPassword.getText().toString();
                if(Test.flag)
                {
                    Log.d("验证码",mPassword);
                    Log.d("输入的验证码",password);
                }
                if(mPassword.isEmpty())
                {
                    etPassword.setError("请输入验证码");
                }
                else if(password.isEmpty())
                {
                    etPassword.setError("请输入验证码");
                }
                else if (mPassword.equals(password))
                {
                    Toast.makeText(RegisterActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWeight()
    {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etTel = (EditText) findViewById(R.id.etTel);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnNext = (Button) findViewById(R.id.btnNext);
    }

    // 异步获取
    class GetMsg extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            URL httpUrl = null;
            try
            {
                httpUrl = new URL("https://106.ihuyi.com/webservice/sms.php?method=Submit&account=cf_lierabbit&password=c126706fe3ffe556a586f0b2710adc57&mobile=" + tel + "&content=您的验证码是：" + mPassword + "。请不要把验证码泄露给其他人。");
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try
            {
                connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                StringBuffer result = new StringBuffer();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str;
                while ((str = bufferedReader.readLine()) != null)
                    result.append(str);
                if(Test.flag)
                    Log.d("result", result.toString());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
