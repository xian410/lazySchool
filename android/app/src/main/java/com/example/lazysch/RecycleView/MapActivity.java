package com.example.lazysch.RecycleView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.lazysch.R;
import com.example.lazysch.utils.MyActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends MyActivity implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.tvBaiduJuli)
    TextView tvBaiduJuli;
    @BindView(R.id.seach_cancel)
    TextView seachCancel;
    @BindView(R.id.seach_sure)
    TextView seachSure;
    @BindView(R.id.seach_name)
    AutoCompleteTextView seachName;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recy_name)
    RecyclerView recyName;

    private AMap mAmap;
    /**
     * 地图对象
     */
    private AMapLocationClient mlocationClient;
    private Marker mStartMarker;
    private Marker mEndMarker;
    private Context context = MapActivity.this;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private PoiResult poiResult;//POI搜索结果


/**
 * 途径点坐标集合
 */
    /**
     * 终点坐标集合［建议就一个终点］
     */
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private boolean useThemestatusBarColor = false;
    private Double startLat, startLon;
    private TextView tvJuLi;
    private MapAdapter mMapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        AMapLocationClient.updatePrivacyShow(context,true,true);
        AMapLocationClient.updatePrivacyAgree(context,true);
        ButterKnife.bind(this);
        setStatusBar();
        initData();
        init();
        mapview.onCreate(savedInstanceState);
        mAmap = mapview.getMap();
        // 初始化Marker添加到地图
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        mAmap.setMyLocationStyle(myLocationStyle);
        mAmap.setMyLocationEnabled(true);
        mStartMarker = mAmap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pick))));
        mEndMarker = mAmap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pick))));
        mMapAdapter = new MapAdapter(this,MapActivity.this);
        mMapAdapter.setOnItemClickListener(new MapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, String content) {
                Intent intent = new Intent();
                //把需要返回的数据存放在intent
                intent.putExtra("address", name);
                setResult(1,intent);
                finish();
                EventBus.getDefault().post(new LocationEventBean("", name + content));
            }
        });
    }

    private void initData() {
        //软键盘回车变搜索按钮
        seachName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String input = seachName.getText().toString();
                    if (!TextUtils.isEmpty(input)) {
                        PoiSearch.Query query = new PoiSearch.Query(input, "", "北辰区");
                        query.setPageSize(20);
                        query.setPageNum(0);
                        PoiSearch poiSearch = null;
                        try {
                            poiSearch = new PoiSearch(MapActivity.this, query);
                        } catch (AMapException e) {
                            e.printStackTrace();
                        }
                        poiSearch.setOnPoiSearchListener(MapActivity.this);
                        poiSearch.searchPOIAsyn();
                        //关闭软键盘
                        closeKeybord(MapActivity.this);

                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mRouteMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }


    /**
     * 初始化
     */
    private void init() {
        if (mAmap == null) {
            mAmap = mapview.getMap();
            setUpMap();
        } else {
            setUpMap();
        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mAmap.getUiSettings().setLogoBottomMargin(-50);
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.setLocationSource(this);// 设置定位监听
        mAmap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        mAmap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAmap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            try {
                mlocationClient = new AMapLocationClient(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
                startLat = amapLocation.getLatitude();
                startLon = amapLocation.getLongitude();
                mStartMarker.setPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @OnClick({R.id.seach_sure, R.id.seach_cancel})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.seach_sure://确认

                String keyword = seachName.getText().toString().trim();
                query = new PoiSearch.Query(keyword, "", "北辰区");
                query.setPageSize(20);
                query.setPageNum(0);

                try {
                    poiSearch = new PoiSearch(this, query);
                } catch (AMapException e) {
                    e.printStackTrace();
                }
                poiSearch.setOnPoiSearchListener(MapActivity.this);
                closeKeybord(MapActivity.this);
                poiSearch.searchPOIAsyn();
                break;
            case R.id.seach_cancel://返回
                finish();
                break;
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int i) {

        poiResult = result;
        if (i == 1000) {
            PoiSearch.Query query = poiResult.getQuery();
            ArrayList<LocationBean> data = new ArrayList<>();
            ArrayList<PoiItem> pois = poiResult.getPois();

            for(PoiItem poi : pois){
                //获取经纬度对象
                LatLonPoint llp = poi.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();
                //获取标题
                String title = poi.getTitle();
                //获取内容
                String text = poi.getSnippet();
                data.add(new LocationBean(lon, lat, title, text));
            }
            recyName.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
            recyName.setAdapter(mMapAdapter);
            mMapAdapter.setData(data);
            mMapAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onPoiItemSearched(com.amap.api.services.core.PoiItem poiItem, int i) {

    }

    /**
     * 自动关闭软键盘
     *
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}