package heracles.soccergo.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

/**
 * Created by 10539 on 2016/9/5.
 */
public class ClubFragment extends Fragment
{
    private TextView tvCityLocate;
    private RecyclerView rvClub;
    private List<Map<String,Object>> clubDatas;
    private RVClubAdapter clubAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_club, container, false);
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
        initClubDatas();

        //设置rvClub
        clubAdapter = new RVClubAdapter(getContext(),clubDatas);
        rvClub.setAdapter(clubAdapter);
        rvClub.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvClub.setItemAnimator(new DefaultItemAnimator());//添加默认动画效果
        clubAdapter.setOnItemClickListener(new RVClubAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(getContext(),(String)clubDatas.get(position).get("name"),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position)
            {

            }
        });
    }

    private void initClubDatas()
    {
        clubDatas = new ArrayList<>();
        Map<String,Object> club = new HashMap<>();
        club.put("name","大连ASIA俱乐部");
        club.put("img",R.drawable.club_asia);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","哈诺伊俱乐部");
        club.put("img",R.drawable.club_hanoi);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","菲戈俱乐部");
        club.put("img",R.drawable.club_feige);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","大连娃娃头俱乐部");
        club.put("img",R.drawable.club_wawatou);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","特刃加努俱乐部");
        club.put("img",R.drawable.club_asia);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","擦枪走火俱乐部");
        club.put("img",R.drawable.club_caqiangzouhuo);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","足球小将俱乐部");
        club.put("img",R.drawable.club_zuqiuxiaojiang);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","讷马足球俱乐部");
        club.put("img",R.drawable.club_nama);
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","大连甘井子区俱乐部");
        club.put("img",R.drawable.club_ganjingzi);
        clubDatas.add(club);
    }

    private void getWidget()
    {
        tvCityLocate = (TextView) getActivity().findViewById(R.id.tvCityLocate);
        rvClub = (RecyclerView) getActivity().findViewById(R.id.rvClub);
    }
}
