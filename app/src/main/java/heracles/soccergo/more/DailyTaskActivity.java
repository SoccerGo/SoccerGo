package heracles.soccergo.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.User;
import heracles.soccergo.Tools.Utils;
import heracles.soccergo.community.PublishActivity;
import heracles.soccergo.race.HoldRaceActivity;
import heracles.soccergo.race.JoinRaceActivity;

public class DailyTaskActivity extends AppCompatActivity {
    private ImageView ivBack;
    private Button btnHold,btnJoin,btnPublish,btnComment,btnMall,btnClub;
    private SimpleDraweeView sdvDailyUser;
    private TextView tvChineseName;
    private TextView tvEnglishName;
    private TextView tvMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_daily_task);
        super.onCreate(savedInstanceState);

        initWidget();
    }

    private void initWidget() {
        getWidget();
        setWidget();
    }

    private void setWidget() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyTaskActivity.this, HoldRaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyTaskActivity.this, JoinRaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyTaskActivity.this, PublishActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        btnMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyTaskActivity.this, FootballMallActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sdvDailyUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+ User.mUserInfo.getHead_link()));
        tvChineseName.setText(Utils.strOrNull(User.mUserInfo.getChinese_name()));
        tvEnglishName.setText(Utils.strOrNull(User.mUserInfo.getEnglish_name()));
        tvMoney.setText(String.valueOf(User.mUserInfo.getHelab()));

    }

    private void getWidget() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnHold = (Button) findViewById(R.id.btnHold);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnComment = (Button) findViewById(R.id.btnComment);
        btnMall = (Button) findViewById(R.id.btnMall);
        btnClub = (Button) findViewById(R.id.btnClub);
        sdvDailyUser = (SimpleDraweeView) findViewById(R.id.sdvDailyUser);
        tvChineseName = (TextView) findViewById(R.id.tvChineseName);
        tvEnglishName = (TextView) findViewById(R.id.tvEnglishName);
        tvMoney = (TextView) findViewById(R.id.tvMoney);
    }
}
