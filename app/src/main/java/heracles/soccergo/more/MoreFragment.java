package heracles.soccergo.more;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.User;
import heracles.soccergo.Tools.Utils;

/**
 * Created by 10539 on 2016/9/5.
 */
public class MoreFragment extends Fragment
{
    ImageView ivPurse, ivDailyTask, ivPrivateCoach, ivFootballMall, ivSetting;
    private SimpleDraweeView sdvMoreUser;
    private TextView tvMoreChineseName;
    private TextView tvMoreEnglishName;
    private TextView tvMoreAccount;
    private TextView tvVip;
    private TextView tvMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initWeight();
    }

    private void initWeight()
    {
        getWeight();
        setWeight();
    }

    private void setWeight()
    {
        ivPurse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), MyWalletActivity.class);
                startActivity(intent);
            }
        });
        ivDailyTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), DailyTaskActivity.class);
                startActivity(intent);
            }
        });
        ivPrivateCoach.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), PrivateCoachActivity.class);
                startActivity(intent);
            }
        });
        ivFootballMall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), FootballMallActivity.class);
                startActivity(intent);
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        if(User.mUserInfo.getHead_link()!=null)
            sdvMoreUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+ User.mUserInfo.getHead_link()));
        else
            sdvMoreUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/more_img.png"));
        tvMoreChineseName.setText(Utils.strOrNull(User.mUserInfo.getChinese_name()));
        tvMoreEnglishName.setText(Utils.strOrNull(User.mUserInfo.getEnglish_name()));
        tvMoreAccount.setText(User.mUserInfo.getUser_name());
        tvMoney.setText(String.valueOf(User.mUserInfo.getHelab()));
        tvVip.setText("无");
    }

    public void update()
    {
        sdvMoreUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+ User.mUserInfo.getHead_link()));
        tvMoreChineseName.setText(Utils.strOrNull(User.mUserInfo.getChinese_name()));
        tvMoreEnglishName.setText(Utils.strOrNull(User.mUserInfo.getEnglish_name()));
        tvMoreAccount.setText(User.mUserInfo.getUser_name());
        tvMoney.setText(String.valueOf(User.mUserInfo.getHelab()));
        tvVip.setText("无");
    }

    private void getWeight()
    {
        Activity activity = getActivity();
        ivPurse = (ImageView) activity.findViewById(R.id.ivPurse);
        ivDailyTask = (ImageView) activity.findViewById(R.id.ivDailyTask);
        ivPrivateCoach = (ImageView)activity.findViewById(R.id.ivPrivateCoach);
        ivFootballMall = (ImageView) activity.findViewById(R.id.ivFootballMall);
        ivSetting = (ImageView) activity.findViewById(R.id.ivSetting);
        sdvMoreUser = (SimpleDraweeView) activity.findViewById(R.id.sdvMoreUser);
        tvMoreChineseName = (TextView) activity.findViewById(R.id.tvMoreChineseName);
        tvMoreEnglishName = (TextView) activity.findViewById(R.id.tvMoreEnglishName);
        tvMoreAccount = (TextView) activity.findViewById(R.id.tvMoreAccount);
        tvVip = (TextView) activity.findViewById(R.id.tvVip);
        tvMoney = (TextView) activity.findViewById(R.id.tvMoney);
    }
}
