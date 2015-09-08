package zhujiafanx.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zhujiafanx.adapter.PageContainerAdapter;
import zhujiafanx.demo.R;


public class PageContainerFragment extends Fragment {

    public static final String TAG="PageContainerFragment";

    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;

    @InjectView(R.id.dish_pager)
    ViewPager dishPager;

    boolean success;

    private OnFragmentInteractionListener mListener;

    public static PageContainerFragment newInstance() {
        PageContainerFragment fragment = new PageContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PageContainerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("xxxx","OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("xxxx","OnCreateView");

        View view = inflater.inflate(R.layout.fragment_page_container, container, false);
        ButterKnife.inject(this, view);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("xxxx", "onResume");

        ((View)tabLayout.getParent()).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("xxxx","OnGlobalLayout");

                if (!success) {
                    success=true;
                    tabLayout.getHeight(); //height is ready
                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单1"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单2"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单3"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单4"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单5"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单6"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单7"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单8"));
//                    tabLayout.addTab(createFixedSizeTab(5, tabLayout, "菜单9"));

                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


                    PagerAdapter pagerAdapter = new PageContainerAdapter(getFragmentManager(), tabLayout.getTabCount());
                    dishPager.setAdapter(pagerAdapter);
                    //tabLayout.setupWithViewPager(dishPager);
                    dishPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            dishPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                }
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("xxxx","OnActivityCreated");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private TabLayout.Tab createFixedSizeTab(int tabCountInParentRegion, TabLayout tabLayout,String tabName) {
        int parentWidth = tabLayout.getWidth();
        int tabSize = parentWidth / tabCountInParentRegion;

        TextView textView = new TextView(getActivity());
        textView.setText(tabName);
        textView.setWidth(tabSize);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);

        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setCustomView(textView);

        return newTab;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
