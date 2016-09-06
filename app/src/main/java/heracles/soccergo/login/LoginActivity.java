package heracles.soccergo.login;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import heracles.soccergo.R;

public class LoginActivity extends AppCompatActivity
{
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.ProgBarTheme_AlphaBackground);
                builder.setView(LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_progbar,null));
                AlertDialog progBarDialog = builder.create();
                progBarDialog.show();
//                WindowManager.LayoutParams lp = progBarDialog.getWindow().getAttributes();
//                lp.width=200;
//                lp.height=200;
//                progBarDialog.getWindow().setAttributes(lp);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void getWidget()
    {
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }
}
