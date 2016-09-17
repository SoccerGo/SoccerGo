package heracles.soccergo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;
import heracles.soccergo.race.Game;

public class RaceService extends Service
{
    private List<Game> games;
    private List<Game> oldGames;
    private String findUserAddGameUrl = CONSTANT.HOST+"Game/findUserAddGame";

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return new RaceBinder();
    }

    private List<Game> getGames()
    {
        List<Game> result = new ArrayList<>();
        for(Game game:games)
        {
            for(Game oldGame:oldGames)
            {
                if(oldGame.getGame_id() != game.getGame_id())
                    result.add(game);
            }
        }
        return result;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        oldGames = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(Test.flag)
            Log.d("serviceStart","ok");
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if(Test.flag)
                    Log.d("InService","ok");
                getInfo(findUserAddGameUrl);
                for(Game game:games)
                    Log.d("ServiceGame",game.toString());
            }
        }, 0, 1000 * 10);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void getInfo(String url)
    {
        games = new ArrayList<>();
        try
        {
            // 连接web,提交帐号密码
            URL httpUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            OutputStream out = httpURLConnection.getOutputStream();
            final String content = "user_id=" + User.mUserInfo.getUser_id();
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
                Log.d("ServiceResult", result.toString());

            //解析返回值，判断是否登入成功
            final JSONObject jsonObject = new JSONObject(result.toString());
            int ret = jsonObject.getInt("success");
            switch (ret)
            {
                case CONSTANT.SUCCESS:
                    List<Game> gamesList = JSON.parseArray(jsonObject.getString("data"), Game.class);
                    for(Game game:gamesList)
                    {
                        if(game.getGame_standard()*2 != game.getJoin_num())
                        {
                            games.add(game);
                        }
                    }
                    for(Game game:games)
                    {
                        for(Game oldGame:oldGames)
                        {
                            if(oldGame.getGame_id() != game.getGame_id())
                                oldGames.add(game);
                        }
                        if(oldGames.size() == 0)
                            oldGames.addAll(games);
                    }
                    break;
                case CONSTANT.ERROR:
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


    private class RaceBinder extends Binder implements RaceInfo
    {
        @Override
        public List<Game> getRaceInfo()
        {
            return getGames();
        }

    }
}
