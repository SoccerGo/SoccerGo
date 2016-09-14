package heracles.soccergo.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import heracles.soccergo.R;
import heracles.soccergo.Tools.GetLocalImage;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.RadarView;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;
import heracles.soccergo.Tools.User_abilities_club_club;
import heracles.soccergo.Tools.Utils;

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
    }

    private void setWidget()
    {
        radarView.setData(new double[]{50, 50, 60, 80, 100, 40});
        rivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(),R.style.Dialog_Notitle);
                dialog.setContentView(R.layout.dialog_get_local_image);
                TextView getImageByCamara = (TextView) dialog.findViewById(R.id.tvGetImageByCamara);
                TextView getImageFromGallery = (TextView) dialog.findViewById(R.id.tvGetImageFromGallery);
                getImageByCamara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),GetLocalImage.class);
                        intent.putExtra("type","byCamara");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                getImageFromGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),GetLocalImage.class);
                        intent.putExtra("type","fromGallery");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
}
