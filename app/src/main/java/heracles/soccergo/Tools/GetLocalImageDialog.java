package heracles.soccergo.Tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import heracles.soccergo.MainActivity;
import heracles.soccergo.R;
import heracles.soccergo.home.HomeFragment;

/**
 * Created by Kinst on 2016/9/15.
 */
public class GetLocalImageDialog{
    private Context context;
    private Dialog getLocalImageDialog;

    public GetLocalImageDialog(Context context) {
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        getLocalImageDialog = new Dialog(context, R.style.Dialog_Notitle);
        getLocalImageDialog.setContentView(R.layout.dialog_get_local_image);
        TextView getImageByCamara = (TextView) getLocalImageDialog.findViewById(R.id.tvGetImageByCamara);
        TextView getImageFromGallery = (TextView) getLocalImageDialog.findViewById(R.id.tvGetImageFromGallery);
        getImageByCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GetLocalImage.class);
                intent.putExtra("type","byCamara");
                context.startActivity(intent);
                getLocalImageDialog.dismiss();
            }
        });
        getImageFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GetLocalImage.class);
                intent.putExtra("type","fromGallery");
                context.startActivity(intent);
                getLocalImageDialog.dismiss();
            }
        });
        getLocalImageDialog.show();
    }
}
