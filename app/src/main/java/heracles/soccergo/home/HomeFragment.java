package heracles.soccergo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import heracles.soccergo.R;
import heracles.soccergo.Tools.RadarView;

/**
 * Created by 10539 on 2016/9/5.
 */
public class HomeFragment extends Fragment
{
    RadarView radarView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initWeight();
    }

    private void initWeight() {
        getWeight();
        setWeight();
    }

    private void getWeight() {
        radarView = (RadarView) getActivity().findViewById(R.id.radarView);
    }

    private void setWeight() {
        radarView.setData(new double[]{50,50,60,80,100,40});
    }

}
