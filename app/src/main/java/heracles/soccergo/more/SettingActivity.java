package heracles.soccergo.more;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import heracles.soccergo.MainActivity;
import heracles.soccergo.R;
import heracles.soccergo.login.LoginActivity;

public class SettingActivity extends AppCompatActivity {
    private TextView tvExitLogin;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initWidget();
    }

    private void initWidget() {
        getWidget();
        setWidget();
    }

    private void setWidget() {
        tvExitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("autoflag","0");
                editor.commit();

                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.instance.finish();
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getWidget() {
        tvExitLogin = (TextView) findViewById(R.id.tvExitLogin);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

}
