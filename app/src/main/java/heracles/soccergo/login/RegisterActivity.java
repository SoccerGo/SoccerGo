package heracles.soccergo.login;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import heracles.soccergo.R;

public class RegisterActivity extends AppCompatActivity {
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initWeight();
    }

    private void initWeight() {
        getWeight();
        setWeight();
    }

    private void setWeight() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getWeight() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    // 异步获取
    class GetMsg extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            URL httpUrl = null;
            try
            {
                httpUrl = new URL("https://106.ihuyi.com/webservice/sms.php?method=Submit&account=cf_lierabbit&password=c126706fe3ffe556a586f0b2710adc57&mobile=13367719632&content=您的验证码是：8888。请不要把验证码泄露给其他人。");
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try
            {
                connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                StringBuffer result = new StringBuffer();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str;
                while ((str = bufferedReader.readLine()) != null)
                    result.append(str);
                Log.d("result",result.toString());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
