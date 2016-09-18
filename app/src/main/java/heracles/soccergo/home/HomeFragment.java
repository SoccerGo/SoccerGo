package heracles.soccergo.home;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.Honor;
import heracles.soccergo.Tools.HttpConnectionUtil;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.RadarView;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;
import heracles.soccergo.Tools.User_abilities_club_club;
import heracles.soccergo.Tools.Utils;
import heracles.soccergo.login.LoginActivity;
import heracles.soccergo.race.Game;
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
    private LinearLayout layoutUserEdit;
    private SimpleDraweeView sdvUser;
    private RecyclerView rvHonor;
    private List<Honor> honors;

    private RaceInfo raceInfo;
    private RaceServiceConnection raceCon;

    private NotificationManager notificationManager;

    private static boolean serviceSwitch = true;

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
        if (Test.flag)
            Log.d("userInfo", userInfo);
        new GetUserInfo(userInfo).execute();
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
        sdvUser = (SimpleDraweeView) activity.findViewById(R.id.sdvUser);
        rvHonor = (RecyclerView) activity.findViewById(R.id.rvHonor);
    }

    private void setWidget()
    {
        layoutUserEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), UserEditActivity.class);
                intent.putExtra("age",tvAge.getText().toString());
                intent.putExtra("num",tvShirtNum.getText().toString());
                intent.putExtra("chineseName",tvName.getText().toString());
                intent.putExtra("englishName",tvEnglishName.getText().toString());
                intent.putExtra("birthdaty",tvBirthday.getText().toString());
                intent.putExtra("sex",Utils.strOrNull(User.mUserInfo.getSex()));
                intent.putExtra("position",Utils.strOrNull(User.mUserInfo.getLocation()));
                startActivity(intent);
            }
        });
    }

    public void update()
    {
        new UpDateUI().execute();
    }

    // 异步获取
    class GetUserInfo extends AsyncTask<Void, Integer, Void>
    {
        private String json;
        private ProgressDialog progressDialog;

        public GetUserInfo(String json)
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
                user = JSON.parseObject(jsonObject.getString("data"), User_abilities_club_club.class);
                honors = JSON.parseArray(jsonObject.getString("honor"),Honor.class);
                User.setUser(user);
                if(serviceSwitch)
                {
                    startRaceService();
                    serviceSwitch = false;
                }

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            sdvUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+user.getHead_link()));
            tvName.setText(Utils.strOrNull(user.getChinese_name()));
            tvEnglishName.setText(Utils.strOrNull(user.getEnglish_name()));
            tvAge.setText(Utils.strOrNull(String.valueOf(user.getAge()))+"岁");
            tvClubInfo.setText(Utils.strOrNull(user.getClub_club_name()) + " "
                    + Utils.strOrNull(String.valueOf(user.getShirt_num())) + "号" + " "
                    + Utils.strOrNull(user.getLocation()));
            tvName2.setText("中文名：" + Utils.strOrNull(user.getChinese_name()));
            tvEnglishName2.setText("英文名：" + Utils.strOrNull(user.getEnglish_name()));
            tvAge2.setText("年龄：" + Utils.strOrNull(String.valueOf(user.getAge())));
            String birthday = user.getBirthday();
            if(birthday!=null)
            {
                birthday = birthday.split(" ")[0];
                tvBirthday.setText("生日：" + birthday);
            }
            else {
                tvBirthday.setText("生日：" );
            }

            tvClub.setText("俱乐部：" + Utils.strOrNull(user.getClub_club_name()));
            tvShirtNum.setText("球衣号：" + Utils.strOrNull(String.valueOf(user.getShirt_num())));
            tvLocation.setText("常用位置：" + Utils.strOrNull(user.getLocation()));

            radarView.setData(new double[]{user.getPandai(), user.getChuanqiu(),
                    user.getShoot_grade(), user.getScore(), user.getFangshou(), user.getLiliang()});

            tvAbilityValue.setText(String.valueOf((int) ((user.getPandai() + user.getChuanqiu() + user.getShoot_grade() + user.getScore() +
                    user.getFangshou() + user.getLiliang()) * 100.0 / 600)));

            RVHonorAdapter rvHonorAdapter = new RVHonorAdapter(getContext(),honors);
            rvHonor.setAdapter(rvHonorAdapter);
            rvHonor.setLayoutManager(new LinearLayoutManager(getContext()));
            rvHonor.setItemAnimator(new DefaultItemAnimator());
            progressDialog.close();
        }
    }

    class UpDateUI extends AsyncTask<Void, Integer, Map<String,Object>>
    {
        @Override
        protected Map<String, Object> doInBackground(Void... params)
        {
            Map<String,Object> map = new HashMap<>();
            map.put("user_id",User.mUserInfo.getUser_id());
            Map<String, Object> result = null;
            try
            {
                result = HttpConnectionUtil.doPostPicture(CONSTANT.HOST+"app/user/findUserMessage",map,null);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, Object> datas)
        {
            if(datas != null)
            {
                boolean ret = (boolean) datas.get("ret");
                if(ret)
                {
                    Log.d("Home  user",(String) datas.get("data"));
                    user = JSON.parseObject((String) datas.get("data"), User_abilities_club_club.class);
                    User.setUser(user);

                    sdvUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+user.getHead_link()));
                    tvName.setText(Utils.strOrNull(user.getChinese_name()));
                    tvEnglishName.setText(Utils.strOrNull(user.getEnglish_name()));
                    tvAge.setText(Utils.strOrNull(String.valueOf(user.getAge()))+"岁");
                    tvClubInfo.setText(Utils.strOrNull(user.getClub_club_name()) + " "
                            + Utils.strOrNull(String.valueOf(user.getShirt_num())) + "号" + " "
                            + Utils.strOrNull(user.getLocation()));
                    tvName2.setText("中文名：" + Utils.strOrNull(user.getChinese_name()));
                    tvEnglishName2.setText("英文名：" + Utils.strOrNull(user.getEnglish_name()));
                    tvAge2.setText("年龄：" + Utils.strOrNull(String.valueOf(user.getAge())));
                    String birthday = user.getBirthday();
                    if(birthday!=null)
                    {
                        birthday = birthday.split(" ")[0];
                        tvBirthday.setText("生日：" + birthday);
                    }
                    else {
                        tvBirthday.setText("生日：" );
                    }

                    tvClub.setText("俱乐部：" + Utils.strOrNull(user.getClub_club_name()));
                    tvShirtNum.setText("球衣号：" + Utils.strOrNull(String.valueOf(user.getShirt_num())));
                    tvLocation.setText("常用位置：" + Utils.strOrNull(user.getLocation()));

                    radarView.setData(new double[]{user.getPandai(), user.getChuanqiu(),
                            user.getShoot_grade(), user.getScore(), user.getFangshou(), user.getLiliang()});

                    tvAbilityValue.setText(String.valueOf((int) ((user.getPandai() + user.getChuanqiu() + user.getShoot_grade() + user.getScore() +
                            user.getFangshou() + user.getLiliang()) * 100.0 / 600)));

                }
            }
        }
    }


    private void startRaceService()
    {
        notificationManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
        Intent raceService = new Intent(getContext(), RaceService.class);
        raceCon = new RaceServiceConnection();
        getContext().startService(raceService);
        getContext().bindService(raceService, raceCon, getContext().BIND_AUTO_CREATE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                List<Game> games = raceInfo.getRaceInfo();
                if (games.size() != 0)
                {
                    Log.d("MainRaceInfo:", games.toString());
                    for(Game game:games)
                    {
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext());
                        notification.setSmallIcon(R.drawable.logo);
                        notification.setContentTitle(game.getGame_name());
                        notification.setContentText("足球人数已经凑齐，赶快看看吧");
                        notification.setAutoCancel(true);        //点击自动消息
                        Intent intent = new Intent(getContext(), LoginActivity.class);    //点击通知进入的界面
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        notification.setContentIntent(contentIntent);
                        notificationManager.notify(0, notification.build());
                    }
                }
                else
                    Log.d("MainRaceInfo:", "没数据");
            }
        }, 5 * 1000, 10 * 1000);
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

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if(Test.flag)
            Log.d("HomeService","销毁服务");
        getContext().unbindService(raceCon);
        serviceSwitch = true;
    }
}
