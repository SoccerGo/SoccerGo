package heracles.soccergo.race;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import heracles.soccergo.R;

/**
 * Created by 10539 on 2016/9/5.
 */
public class RaceFragment extends Fragment
{
    private Toolbar tbTitleBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_race, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        tbTitleBar.setNavigationIcon(R.drawable.left_arrow);
        tbTitleBar.setTitle("约赛");
    }

    private void getWidget()
    {
        tbTitleBar = (Toolbar) getActivity().findViewById(R.id.tbTitleBar);
    }
}
