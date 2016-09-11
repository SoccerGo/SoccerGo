package heracles.soccergo.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
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

import heracles.soccergo.MainActivity;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.Test;

/**
 * Created by 10539 on 2016/9/6.
 */
public class LoginThread extends Thread
{
    private String mPassword;
    private String mUser;
    private Handler mHandler;
    private Context mContext;
    private String mUrl = CONSTANT.HOST + "Heracles/app/user/Login/";


    public LoginThread(Context context,String user, String password, Handler handler)
    {
        this.mUser = user;
        this.mPassword = password;
        this.mContext = context;
        this.mHandler = handler;
    }

    @Override
    public void run()
    {

        try
        {
            // 连接web,提交帐号密码
            URL httpUrl = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            OutputStream out = httpURLConnection.getOutputStream();
            final String content = "user_name=" + mUser + "&password=" + mPassword;
            out.write(content.getBytes());

            //读取服务器返回结果
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
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    break;
                case CONSTANT.ERROR:
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Toast.makeText(mContext, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
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
    }
}
