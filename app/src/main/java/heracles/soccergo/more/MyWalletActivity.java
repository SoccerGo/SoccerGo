package heracles.soccergo.more;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.User;

public class MyWalletActivity extends AppCompatActivity {
    private ImageView ivBack;
    private SimpleDraweeView sdvWalletUser;
    private TextView tvMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        sdvWalletUser = (SimpleDraweeView) findViewById(R.id.sdvWalletUser);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sdvWalletUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+ User.mUserInfo.getHead_link()));
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvMoney.setText(String.valueOf(User.mUserInfo.getHelab()));
    }
}
