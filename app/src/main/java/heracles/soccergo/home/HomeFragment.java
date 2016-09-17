package heracles.soccergo.home;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import heracles.soccergo.R;
import heracles.soccergo.Tools.GetLocalImageDialog;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.RadarView;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;
import heracles.soccergo.Tools.User_abilities_club_club;
import heracles.soccergo.Tools.Utils;
import heracles.soccergo.login.LoginActivity;
import heracles.soccergo.service.RaceInfo;
import heracles.soccergo.service.RaceService;

/**
 * Created by 10539 on 2016/9/5.
 */
public class HomeFragment extends Fragment
{
    private RadarView radarView;
    private User_abilities_club_club user;
    private TextView tvName;
    private TextView tvEnglishName;
    private TextView tvAge;
    private TextView tvClubInfo;
    private TextView tvName2;
    private TextView tvEnglishName2;
    private TextView tvAge2;
    private TextView tvBirthday;
    private TextView tvClub;
    private TextView tvShirtNum;
    private TextView tvLocation;
    private TextView tvAbilityValue;
    private ImageView rivIcon;
    private GetLocalImageDialog getLocalImageDialog;
    private LinearLayout layoutUserEdit;

    private RaceInfo raceInfo;
    private RaceServiceConnection raceCon;

    private NotificationManager notificationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initWidget();
        String userInfo = getActivity().getIntent().getStringExtra("userInfo");
        if(Test.flag)
            Log.d("userInfo",userInfo);
        new UpdateUI(userInfo).execute();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void getWidget()
    {
        FragmentActivity activity = getActivity();
        radarView = (RadarView) activity.findViewById(R.id.radarView);
        rivIcon = (ImageView) getActivity().findViewById(R.id.rivIcon);
        tvName = (TextView) activity.findViewById(R.id.tvName);
        tvEnglishName = (TextView) activity.findViewById(R.id.tvEnglishName);
        tvAge = (TextView) activity.findViewById(R.id.tvAge);
        tvClubInfo = (TextView) activity.findViewById(R.id.tvClubInfo);
        tvName2 = (TextView) activity.findViewById(R.id.tvName2);
        tvEnglishName2 = (TextView) activity.findViewById(R.id.tvEnglishName2);
        tvAge2 = (TextView) activity.findViewById(R.id.tvAge2);
        tvBirthday = (TextView) activity.findViewById(R.id.tvBirthday);
        tvClub = (TextView) activity.findViewById(R.id.tvClub);
        tvShirtNum = (TextView) activity.findViewById(R.id.tvShirtNum);
        tvLocation = (TextView) activity.findViewById(R.id.tvLocation);
        tvAbilityValue = (TextView) activity.findViewById(R.id.tvAbilityValue);
        layoutUserEdit = (LinearLayout) activity.findViewById(R.id.layoutUserEdit);
    }

    private void setWidget()
    {
        rivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getLocalImageDialog = new GetLocalImageDialog(getActivity());
            }
        });

        layoutUserEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(),UserEditActivity.class);
                startActivity(intent);
            }
        });
    }

    // 异步获取
    class UpdateUI extends AsyncTask<Void, Integer, Void>
    {
        private String json;
        private ProgressDialog progressDialog;
        public UpdateUI(String json)
        {
            this.json = json;
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(json);
                user = JSON.parseObject(jsonObject.getString("data"),User_abilities_club_club.class);
                User.setUser(user);
                startRaceService();
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            tvName.setText(Utils.strOrNull(user.getChinese_name()));
            tvEnglishName.setText(Utils.strOrNull(user.getEnglish_name()));
            tvAge.setText(Utils.strOrNull(String.valueOf(user.getAge())));
            tvClubInfo.setText(Utils.strOrNull(user.getClub_club_name())+" "
                    +Utils.strOrNull(String.valueOf(user.getShirt_num()))+"号"
                    +Utils.strOrNull(user.getLocation()));
            tvName2.setText("中文名："+Utils.strOrNull(user.getChinese_name()));
            tvEnglishName2.setText("英文名："+Utils.strOrNull(user.getEnglish_name()));
            tvAge2.setText("年龄："+Utils.strOrNull(String.valueOf(user.getAge())));
            tvBirthday.setText(Utils.strOrNull("生日："+Utils.strOrNull(user.getBirthday())));
            tvClub.setText("俱乐部："+Utils.strOrNull(user.getClub_club_name()));
            tvShirtNum.setText("球衣号："+Utils.strOrNull(String.valueOf(user.getShirt_num())));
            tvLocation.setText("常用位置："+Utils.strOrNull(user.getLocation()));

            radarView.setData(new double[]{user.getPandai(), user.getChuanqiu(),
                    user.getShoot_grade(), user.getScore(), user.getFangshou(), user.getLiliang()});

            tvAbilityValue.setText(String.valueOf ((int) ((user.getPandai()+user.getChuanqiu()+user.getShoot_grade()+user.getScore()+
                                user.getFangshou()+user.getLiliang())*100.0/600)));
            progressDialog.close();
        }
    }

    private void startRaceService()
    {
        notificationManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext());
        notification.setSmallIcon(R.drawable.footbal);
        notification.setContentTitle("标题");
        notification.setContentText("内容");
        notification.setAutoCancel(true);	    //点击自动消息
        Intent intent = new Intent(getContext(), LoginActivity.class);    //点击通知进入的界面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        notificationManager.notify(0, notification.build());

        Intent raceService = new Intent(getContext(), RaceService.class);
        raceCon = new RaceServiceConnection();
        getContext().startService(raceService);
        getContext().bindService(raceService, raceCon, getContext().BIND_AUTO_CREATE);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                Log.d("MainRaceInfo:", raceInfo.getRaceInfo().toString());
//            }
//        }, 5 * 1000,5000 * 10);
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
    public void onDestroy()
    {
        super.onDestroy();
        getContext().unbindService(raceCon);
    }
}
