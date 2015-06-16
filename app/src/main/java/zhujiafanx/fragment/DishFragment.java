package zhujiafanx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import zhujiafanx.adapter.DishPagerAdapter;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.rest.IDishClient;


public class DishFragment extends Fragment {

    private ViewPager vpPager;
    private DishPagerAdapter adapter;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish, container, false);

        vpPager=(ViewPager)view.findViewById(R.id.vp_pager);
        adapter=new DishPagerAdapter(getActivity().getSupportFragmentManager(),dishClient.GetDishCatagory());//getFragmentManager();
        vpPager.setAdapter(adapter);

        return view;
    }
}
