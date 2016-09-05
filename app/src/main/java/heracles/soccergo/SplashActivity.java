package heracles.soccergo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import heracles.soccergo.login.LoginActivity;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 跳转到登入界面
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
