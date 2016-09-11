package heracles.soccergo.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import heracles.soccergo.R;

/**
 * Created by 10539 on 2016/9/5.
 */
public class MoreFragment extends Fragment
{
    ImageView ivPurse,ivDailyTask,ivPrivateCoach,ivFootballMall;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_more, container, false);
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

    private void setWeight() {
        ivPurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MyWalletActivity.class);
                startActivity(intent);
            }
        });
        ivDailyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DailyTaskActivity.class);
                startActivity(intent);
            }
        });
        ivPrivateCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PrivateCoachActivity.class);
                startActivity(intent);
            }
        });
        ivFootballMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FootballMallActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getWeight() {
        ivPurse = (ImageView) getActivity().findViewById(R.id.ivPurse);
        ivDailyTask = (ImageView) getActivity().findViewById(R.id.ivDailyTask);
        ivPrivateCoach = (ImageView) getActivity().findViewById(R.id.ivPrivateCoach);
        ivFootballMall = (ImageView) getActivity().findViewById(R.id.ivFootballMall);
    }
}
