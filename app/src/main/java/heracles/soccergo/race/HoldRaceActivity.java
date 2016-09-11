package heracles.soccergo.race;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;

public class HoldRaceActivity extends AppCompatActivity
{
    private Button btnJoinRace;
    private TextView tvFootballMap;
    private EditText etAddress;

    public static final int GETADDRESS = 100;

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
                startActivityForResult(intent,GETADDRESS);
            }
        });
    }

    private void getWidget()
    {
        btnJoinRace = (Button) findViewById(R.id.btnJoinRace);
        tvFootballMap = (TextView) findViewById(R.id.tvFootballMap);
        etAddress = (EditText) findViewById(R.id.etAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            String address = data.getStringExtra("address");
            if(address!=null)
                etAddress.setText(address);
            if(Test.flag)
            {
                Log.d("city",data.getStringExtra("city"));
                Log.d("province", data.getStringExtra("province"));
            }
        }
    }
}
