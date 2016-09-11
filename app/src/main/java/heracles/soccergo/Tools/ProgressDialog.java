package heracles.soccergo.Tools;

import android.app.Dialog;
import android.content.Context;

import heracles.soccergo.R;

/**
 * Created by Kinst on 2016/9/11.
 */
public class ProgressDialog {
    private Context context;
    private Dialog progressDialog;

    public ProgressDialog(Context context) {
        this.context = context;
    }

    public void show()
    {
        progressDialog = new Dialog(context, R.style.ProgBarTheme_AlphaBackground);
        progressDialog.setContentView(R.layout.dialog_progbar);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void close()
    {
        progressDialog.dismiss();
    }
}
