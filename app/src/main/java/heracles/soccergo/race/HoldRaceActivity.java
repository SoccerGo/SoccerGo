package heracles.soccergo.race;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import heracles.soccergo.R;

public class HoldRaceActivity extends AppCompatActivity
{
    private Button btnJoinRace;
    private TextView tvFootballMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hold_race);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        btnJoinRace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HoldRaceActivity.this, JoinRaceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvFootballMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HoldRaceActivity.this, FootballMapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getWidget()
    {
        btnJoinRace = (Button) findViewById(R.id.btnJoinRace);
        tvFootballMap = (TextView) findViewById(R.id.tvFootballMap);
    }
}
