package heracles.soccergo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import heracles.soccergo.race.Game;

public class RaceService extends Service
{
    private int id;
    private List<Game> games;

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return new RaceBinder();
    }

    private List<Game> getGames()
    {
        return games;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        id = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("serviceStart","ok");
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                id++;
                Log.d("id",String.valueOf(id));
            }
        }, 0, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private class RaceBinder extends Binder implements RaceInfo
    {
        @Override
        public List<Game> getRaceInfo()
        {
            return getGames();
        }

        @Override
        public int getId()
        {
            return id;
        }
    }
}
