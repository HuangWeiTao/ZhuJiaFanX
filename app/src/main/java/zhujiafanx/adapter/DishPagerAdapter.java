package zhujiafanx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import zhujiafanx.fragment.DishTabFragment;


public class DishPagerAdapter extends FragmentPagerAdapter {

    private static final int pageCountConstant = 8;

    public DishPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return pageCountConstant;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page "+(position+1);
    }

    @Override
    public Fragment getItem(int position) {
        return DishTabFragment.newInstance((position + 1), "Page # " + (position + 1));
    }
}
