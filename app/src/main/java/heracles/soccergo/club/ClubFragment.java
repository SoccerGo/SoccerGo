package heracles.soccergo.club;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;

/**
 * Created by 10539 on 2016/9/5.
 */
public class ClubFragment extends Fragment
{
    private TextView tvCityLocate;
    private RecyclerView rvClub;
    private List<Map<String,Object>> clubDatas;
    private RVClubAdapter clubAdapter;
    private Button btnBrowseMoreClub;

    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private TencentLocationListener locationListener;

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

        initLocation();
    }

    private void initLocation()
    {
        locationManager = TencentLocationManager.getInstance(getContext());
        locationRequest = TencentLocationRequest.create()
                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA)// 设置定位level
                .setInterval(600000); // 设置定位周期
        locationListener = new MyLocation();
        int err = locationManager.requestLocationUpdates(locationRequest, locationListener);
        Log.d("err", String.valueOf(err));
        switch (err)
        {
            case 1:
                Toast.makeText(getContext(),"设备缺少使用腾讯定位服务需要的基本条件",Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getContext(),"manifest 中配置的 key 不正确",Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getContext(),"自动加载libtencentloc",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
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
        btnBrowseMoreClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ClubMoreActivity.class);
                startActivity(intent);
            }
        });
//        Uri uri = Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.more_img);
//        sdvTest.setImageURI(uri);
    }

    private void initClubDatas()
    {
        clubDatas = new ArrayList<>();
        Map<String,Object> club = new HashMap<>();
        club.put("name","大连ASIA俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_asia));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","哈诺伊俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_hanoi));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","菲戈俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_feige));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","大连娃娃头俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_wawatou));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","特刃加努俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_terengganu));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","擦枪走火俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_caqiangzouhuo));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","足球小将俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_zuqiuxiaojiang));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","讷马足球俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_nama));
        clubDatas.add(club);

        club = new HashMap<>();
        club.put("name","大连甘井子区俱乐部");
        club.put("img", Uri.parse("res://" +getContext().getPackageName()+"/"+ R.drawable.club_ganjingzi));
        clubDatas.add(club);
    }

    private void getWidget()
    {
        tvCityLocate = (TextView) getActivity().findViewById(R.id.tvCityLocate);
        rvClub = (RecyclerView) getActivity().findViewById(R.id.rvClub);
        btnBrowseMoreClub = (Button) getActivity().findViewById(R.id.btnBrowseMoreClub);
    }

    private class MyLocation implements TencentLocationListener
    {

        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int i, String s)
        {
            if(Test.flag)
                Log.d("city",tencentLocation.getCity());
            tvCityLocate.setText(tencentLocation.getCity());
            User.setCity(tencentLocation.getCity());
            User.setmProvince(tencentLocation.getProvince());
        }

        @Override
        public void onStatusUpdate(String s, int i, String s1)
        {

        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        locationRequest = null;
        locationListener = null;
    }
}
