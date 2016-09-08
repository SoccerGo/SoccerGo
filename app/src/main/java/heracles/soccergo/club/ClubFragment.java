package heracles.soccergo.club;

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
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;

/**
 * Created by 10539 on 2016/9/5.
 */
public class ClubFragment extends Fragment
{
    private TextView tvCityLocate;
    private RecyclerView rvClub;
    private List<Map<String,Object>> clubDatas;
    private RVClubAdapter clubAdapter;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

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

        startLocation();
    }

    private void startLocation()
    {
        mLocationClient = new LocationClient(getContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();
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

    // 百度定位回调
    public class MyLocationListener implements BDLocationListener
    {
        // 解析定位结果
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            tvCityLocate.setText(location.getCity());
            if(location.getLocType() == 161 || location.getLocType() == 61)
            {
                mLocationClient.stop();
            }
            if(Test.flag)
            {
                //Receive Location
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation)
                {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
                {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation)
                {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError)
                {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException)
                {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException)
                {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null)
                {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list)
                    {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.i("city",location.getCity());
                Log.i("BaiduLocationApiDem", sb.toString());
            }
        }
    }


    // 初始化定位
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span=1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
