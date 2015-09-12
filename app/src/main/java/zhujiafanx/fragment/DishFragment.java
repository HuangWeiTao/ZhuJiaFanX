package zhujiafanx.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import javax.inject.Inject;

import zhujiafanx.adapter.DishPagerAdapter;
import zhujiafanx.app.App;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.rest.IDishClient;
import zhujiafanx.rest.RestLocation;


public class DishFragment extends Fragment implements BDLocationListener {

    private ViewPager vpPager;
    private DishPagerAdapter adapter;

    private ImageButton ib_locationButton;

    private TextView tv_locationText;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = this;

    @Inject
    IDishClient dishClient;

    public static DishFragment newInstance() {
        return new DishFragment();
    }

    public DishFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.INSTANCE.inject(this);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish, container, false);

        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);
        adapter = new DishPagerAdapter(getActivity().getSupportFragmentManager(), dishClient.GetDishCategories());//getFragmentManager();
        vpPager.setAdapter(adapter);

        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        //View actionBarView = setCustomActionBar(actionBar);

        //ib_locationButton = (ImageButton) actionBarView.findViewById(R.id.ib_location);
        //tv_locationText = (TextView) actionBarView.findViewById(R.id.tv_location_text);

        //ib_locationButton.setOnClickListener(new LocationButtonClickListener(this.mLocationClient, tv_locationText));
        //ib_locationButton.performClick();

        return view;
    }


    private View setCustomActionBar(ActionBar actionBar) {
        final ViewGroup actionBarLayout = (ViewGroup) getActivity().getLayoutInflater().inflate(
                R.layout.actionbar_home,
                null);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_bg)));

        return actionBarLayout;
    }


    @Override
    public void onReceiveLocation(BDLocation location) {

        RestLocation l = new RestLocation();
        l.Latitude = location.getLatitude();
        l.Lontitude = location.getLongitude();
        l.Address = location.getAddrStr();

        App.setItem(App.APP_KEY_LOCATION, l);

        tv_locationText.setText(l.Address);
    }
}

class LocationButtonClickListener implements View.OnClickListener
{
    private LocationClient mLocationClient;

    private TextView m_locationText;

    public LocationButtonClickListener(LocationClient locationClient,TextView locationText)
    {
        mLocationClient=locationClient;
        m_locationText=locationText;

    }

    @Override
    public void onClick(View v) {
        if(!mLocationClient.isStarted()) {
            InitLocation();
            mLocationClient.start();
            m_locationText.setText("定位中...");

        }
        else {
            mLocationClient.stop();
            m_locationText.setText("取消定位!");
        }
    }

    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("gcj02");
        int span=1000;
        try {
            span = Integer.valueOf("15000");
        } catch (Exception e) {
            // TODO: handle exception
        }
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
}
