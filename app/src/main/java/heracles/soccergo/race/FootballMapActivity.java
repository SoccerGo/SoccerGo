package heracles.soccergo.race;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import heracles.soccergo.R;

public class FootballMapActivity extends FragmentActivity
{
    private UiSettings mapUiSettings;

    private Handler handler = new Handler();
    private DemoLocationSource locationSource;
    private TencentMap tencentMap;

    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_map);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) fm.findFragmentById(R.id.map_frag);
        tencentMap = mapFragment.getMap();
        locationSource = new DemoLocationSource(this);
        tencentMap.setLocationSource(locationSource);
        mapUiSettings = tencentMap.getUiSettings();
        tencentMap.setOnMapLoadedCallback(new TencentMap.OnMapLoadedCallback()
        {
            @Override
            public void onMapLoaded()
            {
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mapUiSettings.setMyLocationButtonEnabled(true);
                    }
                });
            }
        });
        TencentMap.OnInfoWindowClickListener listener = new TencentMap.OnInfoWindowClickListener()
        {

            @Override
            public void onInfoWindowClick(Marker marker)
            {
                // TODO Auto-generated method stub
                Log.d("maker", marker.getTitle());
                finish();
            }
        };

//绑定信息窗点击事件
        tencentMap.setOnInfoWindowClickListener(listener);

    }


    class DemoLocationSource implements LocationSource, TencentLocationListener
    {

        private Context mContext;
        private OnLocationChangedListener mChangedListener;
        private TencentLocationManager locationManager;
        private TencentLocationRequest locationRequest;

        public DemoLocationSource(Context context)
        {
            // TODO Auto-generated constructor stub
            mContext = context;
            locationManager = TencentLocationManager.getInstance(mContext);
            locationRequest = TencentLocationRequest.create()
                    .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA)// 设置定位level
                    .setInterval(5000); // 设置定位周期
        }

        @Override
        public void onLocationChanged(TencentLocation arg0, int arg1,
                                      String arg2)
        {
            Log.d("error", String.valueOf(arg1));
            // TODO Auto-generated method stub
            if (arg1 == TencentLocation.ERROR_OK && mChangedListener != null)
            {
                Log.d("maplocation", "location: " + arg0.getCity() + " " + arg0.getProvider());
                Location location = new Location(arg0.getProvider());
                location.setLatitude(arg0.getLatitude());
                location.setLongitude(arg0.getLongitude());
                location.setAccuracy(arg0.getAccuracy());
                mChangedListener.onLocationChanged(location);
                if (first)
                {
                    CameraUpdate cameraSigma = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(arg0.getLatitude(), arg0.getLongitude()), //新的中心点坐标
                            15,  //新的缩放级别
                            0f, //俯仰角 0~45° (垂直地图时为0)
                            0f)); //偏航角 0~360° (正北方为0)
//移动地图
                    tencentMap.moveCamera(cameraSigma);
                    TencentSearch tencentSearch = new TencentSearch(FootballMapActivity.this);
                    SearchParam.Region r = new SearchParam.Region().poi("大连");
                    SearchParam param = new SearchParam().keyword("足球场").boundary(r);
                    tencentSearch.search(param, new HttpResponseListener()
                    {
                        @Override
                        public void onSuccess(int i, BaseObject baseObject)
                        {
                            if (baseObject != null)
                            {
                                //这里的object是所有检索结果的父类
                                //用户需要将其转换为其实际类型以获取检索内容
                                //这里将其转换为Address2GeoResultObject
                                SearchResultObject oj = (SearchResultObject) baseObject;
                                if (oj.data != null)
                                {
                                    String result = "搜索大连地区含有足球场的poi\n\n";
                                    for (SearchResultObject.SearchResultData data : oj.data)
                                    {
                                        Log.v("demo", "title:" + data.title + "  坐标:" + data.location.lat + " " + data.location.lng);
                                        result += data.address + "\n";
                                        LatLng latLng = new LatLng(data.location.lat, data.location.lng);
                                        tencentMap.addMarker(new MarkerOptions().position(latLng).title(data.title));
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(int i, String s, Throwable throwable)
                        {

                        }
                    });
                    first = false;
                }
            }
        }

        @Override
        public void onStatusUpdate(String arg0, int arg1, String arg2)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void activate(OnLocationChangedListener arg0)
        {
            // TODO Auto-generated method stub
            mChangedListener = arg0;
            int err = locationManager.requestLocationUpdates(locationRequest, this);
            Log.d("err", String.valueOf(err));
            switch (err)
            {
                case 1:
                    setTitle("设备缺少使用腾讯定位服务需要的基本条件");
                    break;
                case 2:
                    setTitle("manifest 中配置的 key 不正确");
                    break;
                case 3:
                    setTitle("自动加载libtencentloc.so失败");
                    break;

                default:
                    break;
            }
        }

        @Override
        public void deactivate()
        {
            // TODO Auto-generated method stub
            locationManager.removeUpdates(this);
            mContext = null;
            locationManager = null;
            locationRequest = null;
            mChangedListener = null;
        }

        public void onPause()
        {
            locationManager.removeUpdates(this);
        }

        public void onResume()
        {
            locationManager.requestLocationUpdates(locationRequest, this);
        }

    }
}
