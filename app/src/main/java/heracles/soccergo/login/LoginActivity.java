package heracles.soccergo.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
                Dialog dialog = new Dialog(LoginActivity.this,R.style.ProgBarTheme_AlphaBackground);
                dialog.setContentView(R.layout.dialog_progbar);
                dialog.setCancelable(true);
                dialog.show();

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
