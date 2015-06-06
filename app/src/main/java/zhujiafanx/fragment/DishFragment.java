package zhujiafanx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zhujiafanx.adapter.DishPagerAdapter;
import zhujiafanx.demo.R;


public class DishFragment extends Fragment {

    private ViewPager vpPager;
    private DishPagerAdapter adapter;

    public static DishFragment newInstance() {
       return new DishFragment();
    }

    public DishFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish, container, false);

        vpPager=(ViewPager)view.findViewById(R.id.vp_pager);
        adapter=new DishPagerAdapter(getActivity().getSupportFragmentManager());//getFragmentManager();
        vpPager.setAdapter(adapter);

        return view;
    }
}
