package heracles.soccergo.race;

import android.content.Context;
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

import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.Test;

/**
 * Created by 10539 on 2016/9/12.
 */
public class JoinRaceThread extends Thread
{
    private String userID;
    private String gameID;
    private Handler mHandler;
    private Context mContext;
    private String mUrl = CONSTANT.HOST + "Game/joinGame";
    private ProgressDialog progressDialog;


    public JoinRaceThread(Context context, Handler handler, String userID, String gameID)
    {
        this.userID = userID;
        this.gameID = gameID;
        this.mContext = context;
        this.mHandler = handler;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
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
            final String content = "user_id=" + userID + "&game_id=" + gameID;
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
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Toast.makeText(mContext, "加入成功", Toast.LENGTH_LONG).show();
                        }
                    });

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

        progressDialog.close();
    }
}
