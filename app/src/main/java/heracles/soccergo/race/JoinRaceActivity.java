package heracles.soccergo.race;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import heracles.soccergo.R;

public class JoinRaceActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnUnjoined;
    private Button btnJoined;
    private LinearLayout layoutJoin;

    private Fragment unJoinedFragment;
    private Fragment joinedFragment;
    private int currentFragmentID = -1;

    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_race);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();

        unJoinedFragment = new UnjoinedFragment();
        joinedFragment = new JoinedFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.layoutJoin, unJoinedFragment, "unJoinedFragment").add(R.id.layoutJoin, joinedFragment, "joinedFragment");
        ft.show(unJoinedFragment).hide(joinedFragment).commit();
        ft.show(joinedFragment);
        currentFragmentID = R.id.btnUnjoined;
    }

    private void setWidget()
    {
        btnUnjoined.setOnClickListener(this);
        btnJoined.setOnClickListener(this);
    }

    private void getWidget()
    {
        btnUnjoined = (Button) findViewById(R.id.btnUnjoined);
        btnJoined = (Button) findViewById(R.id.btnJoined);
        layoutJoin = (LinearLayout) findViewById(R.id.layoutJoin);
    }

    @Override
    public void onClick(View v)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (currentFragmentID)
        {
            case R.id.btnUnjoined:
                ft.hide(unJoinedFragment);
                btnUnjoined.setBackgroundResource(R.color.AlphaWhite);
                break;
            case R.id.btnJoined:
                ft.hide(joinedFragment);
                btnJoined.setBackgroundResource(R.color.AlphaWhite);
                break;
        }
        switch (v.getId())
        {
            case R.id.btnUnjoined:
                ft.show(unJoinedFragment);
                currentFragmentID = R.id.btnUnjoined;
                btnUnjoined.setBackgroundResource(R.color.LightWhiteGreen);
                break;
            case R.id.btnJoined:
                ft.show(joinedFragment);
                ((JoinedFragment)joinedFragment).update();
                currentFragmentID = R.id.btnJoined;
                btnJoined.setBackgroundResource(R.color.LightWhiteGreen);
                break;
        }
        ft.commit();
    }
}
