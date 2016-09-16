package heracles.soccergo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import heracles.soccergo.club.ClubFragment;
import heracles.soccergo.community.CommunityFragment;
import heracles.soccergo.home.HomeFragment;
import heracles.soccergo.login.LoginActivity;
import heracles.soccergo.more.MoreFragment;
import heracles.soccergo.race.RaceFragment;
import heracles.soccergo.service.RaceInfo;
import heracles.soccergo.service.RaceService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView ivRace;
    private ImageView ivClub;
    private ImageView ivHome;
    private ImageView ivCommunity;
    private ImageView ivMore;
    private LinearLayout llMainLayout;

    private Fragment clubFragment;
    private Fragment homeFragment;
    private Fragment moreFragment;
    private Fragment raceFragment;
    private Fragment communityFragment;
    private int currentFragmentID = -1;

    private RaceInfo raceInfo;
    private RaceServiceConnection raceCon;

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        startRaceService();
    }

    private void startRaceService()
    {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setSmallIcon(R.drawable.footbal).setContentTitle("比赛信息")
//                .setContentText("比赛人数已齐")
//                .setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//        notificationManager.notify(1,mBuilder.build());

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.footbal);
        notification.setContentTitle("标题");
        notification.setContentText("内容");
        notification.setAutoCancel(true);	    //点击自动消息
        Intent intent = new Intent(this, LoginActivity.class);    //点击通知进入的界面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        notificationManager.notify(0, notification.build());

        Intent raceService = new Intent(this, RaceService.class);
        raceCon = new RaceServiceConnection();
        startService(raceService);
        bindService(raceService, raceCon, BIND_AUTO_CREATE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                Log.d("serviceTest:id", String.valueOf(raceInfo.getId()));
            }
        }, 5 * 1000,5000);
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
        new GetFragment().execute();
    }

    private void setWidget()
    {
        ivRace.setOnClickListener(this);
        ivClub.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        ivCommunity.setOnClickListener(this);
        ivMore.setOnClickListener(this);
    }

    private void getWidget()
    {
        ivRace = (ImageView) findViewById(R.id.ivRace);
        ivClub = (ImageView) findViewById(R.id.ivClub);
        ivHome = (ImageView) findViewById(R.id.ivHome);
        ivCommunity = (ImageView) findViewById(R.id.ivCommunity);
        ivMore = (ImageView) findViewById(R.id.ivMore);
        llMainLayout = (LinearLayout) findViewById(R.id.llMainLayout);
    }

    @Override
    public void onClick(View v)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (currentFragmentID)
        {
            case R.id.ivRace:
                ft.hide(raceFragment);
                ivRace.setImageResource(R.drawable.race);
                break;
            case R.id.ivClub:
                ft.hide(clubFragment);
                ivClub.setImageResource(R.drawable.club);
                break;
            case R.id.ivHome:
                ft.hide(homeFragment);
                ivHome.setImageResource(R.drawable.home);
                break;
            case R.id.ivCommunity:
                ft.hide(communityFragment);
                ivCommunity.setImageResource(R.drawable.community);
                break;
            case R.id.ivMore:
                ft.hide(moreFragment);
                ivMore.setImageResource(R.drawable.more);
                break;
        }

        switch (v.getId())
        {
            case R.id.ivRace:
                ft.show(raceFragment);
                currentFragmentID = R.id.ivRace;
                ivRace.setImageResource(R.drawable.race2);
                break;
            case R.id.ivClub:
                ft.show(clubFragment);
                currentFragmentID = R.id.ivClub;
                ivClub.setImageResource(R.drawable.club2);
                break;
            case R.id.ivHome:
                ft.show(homeFragment);
                currentFragmentID = R.id.ivHome;
                ivHome.setImageResource(R.drawable.home2);
                break;
            case R.id.ivCommunity:
                ft.show(communityFragment);
                currentFragmentID = R.id.ivCommunity;
                ivCommunity.setImageResource(R.drawable.community2);
                break;
            case R.id.ivMore:
                ft.show(moreFragment);
                currentFragmentID = R.id.ivMore;
                ivMore.setImageResource(R.drawable.more2);
                break;
        }

        ft.commit();
    }

    // 异步获取
    class GetFragment extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            clubFragment = new ClubFragment();
            homeFragment = new HomeFragment();
            moreFragment = new MoreFragment();
            raceFragment = new RaceFragment();
            communityFragment = new CommunityFragment();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.llMainLayout, homeFragment, "homeFragment").add(R.id.llMainLayout, clubFragment, "clubFragment")
                    .add(R.id.llMainLayout, moreFragment, "moreFragment").add(R.id.llMainLayout, raceFragment, "raceFragment")
                    .add(R.id.llMainLayout, communityFragment, "communityFragment");
            ft.show(homeFragment).hide(clubFragment).hide(moreFragment).hide(raceFragment).hide(communityFragment).commit();
            currentFragmentID = R.id.ivHome;
        }
    }

    private class RaceServiceConnection implements ServiceConnection
    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            raceInfo = (RaceInfo) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.unbindService(raceCon);
    }
}