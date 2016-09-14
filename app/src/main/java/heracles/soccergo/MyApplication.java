package heracles.soccergo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 10539 on 2016/9/14.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Fresco.initialize(this);
    }
}
