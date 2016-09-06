package heracles.soccergo.login;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import heracles.soccergo.Tools.Test;

/**
 * Created by 10539 on 2016/9/6.
 */
public class LoginThread extends Thread
{
    private String mPassword;
    private String mUser;
    private Handler mHandler;
    private String mUrl;


    public LoginThread(String user, String password, Handler handler)
    {
        this.mUser = user;
        this.mPassword = password;
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
            final String content = "account=" + mUser + "&password=" + mPassword;
            out.write(content.getBytes());

            //读取服务器返回结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuffer result = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null)
            {
                result.append(str);
            }
            if(Test.flag)
                Log.d("result", result.toString());
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (ProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
