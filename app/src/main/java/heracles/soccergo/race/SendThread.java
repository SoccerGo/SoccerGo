package heracles.soccergo.race;

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

import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.Test;

/**
 * Created by 10539 on 2016/9/12.
 */
public class SendThread extends Thread
{
    private String game_name;
    private String user_id;
    private String game_standard;
    private String game_date;
    private String game_address;
    private String cost;
    private Handler mHandler;
    private Context mContext;
    private String mUrl = CONSTANT.HOST + "Game/addGame";
    private ProgressDialog progressDialog;


    public SendThread(Context context,Handler handler, String user_id, String game_name,String game_standard,String game_date,String game_address,String cost)
    {
        this.user_id = user_id;
        this.game_name = game_name;
        this.game_standard = game_standard;
        this.game_date = game_date;
        this.game_address = game_address;
        this.cost = cost;
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
            final String content = "game_name=" + game_name + "&user_id=" + user_id+"&game_standard="+game_standard+
                    "&game_date="+game_date+"&game_address="+game_address+"&cost="+cost;
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
                            Intent intent = new Intent(mContext,JoinRaceActivity.class);
                            mContext.startActivity(intent);

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
