package heracles.soccergo.race;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import heracles.soccergo.R;

/**
 * Created by 10539 on 2016/9/5.
 */
public class RaceFragment extends Fragment
{
    private Button btnHoldRace,btnJoinRace;
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
        btnHoldRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),HoldRaceActivity.class);
                startActivity(intent);
            }
        });
        btnJoinRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),JoinRaceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getWidget()
    {
        btnHoldRace = (Button) getActivity().findViewById(R.id.btnHoldRace);
        btnJoinRace = (Button) getActivity().findViewById(R.id.btnJoinRace);
    }
}
