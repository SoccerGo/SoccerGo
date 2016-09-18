package heracles.soccergo.race;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;

public class HoldRaceActivity extends AppCompatActivity
{
    private Button btnJoinRace;
    private TextView tvFootballMap;
    private EditText etAddress;
    private EditText etName;
    private EditText etSize;
    private TextView tvDate;
    private TextView tvTime;
    private EditText etPrice;
    private Button btnSend;
    private ImageView ivBack;

    private String mAddress;

    public static final int GETADDRESS = 100;
    public static final int FINISH = 100000;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CONSTANT.SUCCESS:
                    Intent intent = new Intent(HoldRaceActivity.this,JoinRaceActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case CONSTANT.ERROR:
                    Toast.makeText(HoldRaceActivity.this,String.valueOf(msg.obj),Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
                startActivityForResult(intent, GETADDRESS);
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(HoldRaceActivity.this, new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {
                        // TODO Auto-generated method stub
                        Calendar c2 = Calendar.getInstance();
                        c2.set(year, monthOfYear, dayOfMonth);

                        tvDate.setText(year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener()
        {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v)
            {
                new TimePickerDialog(HoldRaceActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        tvTime.setText(hourOfDay + ":" + minute);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = etName.getText().toString();
                String size = etSize.getText().toString();
                String date = tvDate.getText().toString();
                String time = tvTime.getText().toString();
                String price = etPrice.getText().toString();
                String address = etAddress.getText().toString();

                if (name.isEmpty())
                    etName.setError("请输入比赛名字");
                else if (size.isEmpty())
                    etSize.setError("请输入比赛规模");
                else if (date.isEmpty())
                    tvDate.setError("请选择日期");
                else if (time.isEmpty())
                    tvTime.setError("请选择时间");
                else if (address.isEmpty())
                    tvFootballMap.setError("请选择地点");
                else if (price.isEmpty())
                    etPrice.setError("请输入报名费用");
                else
                {
                    new SendThread(HoldRaceActivity.this, handler, User.mUserInfo.getUser_id(), name, size, date + " " + time, mAddress, price).start();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getWidget()
    {
        btnJoinRace = (Button) findViewById(R.id.btnJoinRace);
        tvFootballMap = (TextView) findViewById(R.id.tvFootballMap);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etName = (EditText) findViewById(R.id.etName);
        etSize = (EditText) findViewById(R.id.etSize);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        etPrice = (EditText) findViewById(R.id.etPrice);
        btnSend = (Button) findViewById(R.id.btnSend);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            String address = data.getStringExtra("address");
            if (address != null)
            {
                mAddress = data.getStringExtra("province") + data.getStringExtra("city") + address;
            }
            etAddress.setText(address);
            if (Test.flag)
            {
                Log.d("city", data.getStringExtra("city"));
                Log.d("province", data.getStringExtra("province"));
            }
        }
    }
}
