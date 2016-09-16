package heracles.soccergo.community;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import heracles.soccergo.R;
import heracles.soccergo.Tools.GetLocalImageDialog;

public class PublishActivity extends AppCompatActivity {
    private EditText etTitle,etContent;
    private ImageView ivAddImage;
    private CheckBox cbShowClub;
    private RelativeLayout layoutVisable;
    private Button btnPublish;
    private GetLocalImageDialog getLocalImageDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initWidget();
    }

    private void initWidget() {
        getWidget();
        setWidget();
    }

    private void setWidget() {
        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalImageDialog = new GetLocalImageDialog(PublishActivity.this);
            }
        });
    }

    private void getWidget() {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        ivAddImage = (ImageView) findViewById(R.id.ivAddImage);
        cbShowClub = (CheckBox) findViewById(R.id.cbShowClub);
        layoutVisable = (RelativeLayout) findViewById(R.id.layoutVisable);
        btnPublish = (Button) findViewById(R.id.btnPublish);
    }

}
