package heracles.soccergo.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.Test;

public class Register2Activity extends AppCompatActivity
{
    private EditText etPassword;
    private EditText etPassword2;
    private Button btnSuccess;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
        progressDialog = new ProgressDialog(this);
    }

    private void setWidget()
    {
        btnSuccess.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String psw = etPassword.getText().toString();
                String psw2 = etPassword2.getText().toString();

                if (psw.isEmpty())
                {
                    etPassword.setError("请输入密码");
                } else if (psw2.isEmpty())
                {
                    etPassword2.setError("请输入密码");
                } else if (psw.length() < 6)
                {
                    etPassword.setError("密码长度需要大于6");
                } else if (psw2.length() < 6)
                {
                    etPassword2.setError("密码长度需要大于6");
                } else if (psw.length() > 16)
                {
                    etPassword.setError("密码长度需要小于6");
                } else if (psw2.length() > 16)
                {
                    etPassword2.setError("密码长度需要小于6");
                } else if (!psw.equals(psw2))
                {
                    etPassword2.setError("密码输入不一致");

                } else
                {
                    if (Test.flag)
                        Toast.makeText(Register2Activity.this, "密码一致", Toast.LENGTH_SHORT).show();
                    //获得手机号
                    String tel = getIntent().getStringExtra("tel");
                    progressDialog.show();
                    new HttpRegister(tel,psw).start();

                }

            }
        });
    }

    private void getWidget()
    {
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        btnSuccess = (Button) findViewById(R.id.btnSuccess);
    }

    class HttpRegister extends Thread
    {
        private String password;
        private String tel;

        public HttpRegister(String tel,String password)
        {
            this.password = password;
            this.tel = tel;
        }

        @Override
        public void run()
        {
            try
            {
                URL httpUrl = new URL(CONSTANT.HOST + "app/user/register");
                HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                OutputStream out = httpURLConnection.getOutputStream();
                final String content = "user_name=" + tel + "&password=" + password;
                out.write(content.getBytes());
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer result = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null)
                {
                    result.append(str);
                }
                if (Test.flag)
                    Log.d("result", result.toString());

                //解析返回值，判断是否登入成功
                final JSONObject jsonObject = new JSONObject(result.toString());
                int ret = jsonObject.getInt("success");
                switch (ret)
                {
                    case CONSTANT.SUCCESS:
                        Intent intent = new Intent(Register2Activity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case CONSTANT.ERROR:
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    Toast.makeText(Register2Activity.this,jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (ProtocolException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            progressDialog.close();
        }
    }
}
