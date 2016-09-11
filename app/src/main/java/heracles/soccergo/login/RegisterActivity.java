package heracles.soccergo.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
}
