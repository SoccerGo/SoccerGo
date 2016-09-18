package heracles.soccergo.more;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import heracles.soccergo.MainActivity;
import heracles.soccergo.R;
import heracles.soccergo.community.PublishActivity;
import heracles.soccergo.race.HoldRaceActivity;
import heracles.soccergo.race.JoinRaceActivity;

public class DailyTaskActivity extends AppCompatActivity {
    private ImageView ivBack;
    private Button btnHold,btnJoin,btnPublish,btnComment,btnMall,btnClub;
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
    }

    private void getWidget() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnHold = (Button) findViewById(R.id.btnHold);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnComment = (Button) findViewById(R.id.btnComment);
        btnMall = (Button) findViewById(R.id.btnMall);
        btnClub = (Button) findViewById(R.id.btnClub);
    }
}
