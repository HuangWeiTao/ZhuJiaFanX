package zhujiafanx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import zhujiafanx.fragment.DishTabFragment;
import zhujiafanx.rest.RestDishCategory;


public class DishPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<RestDishCategory> dishCatagories;

    public DishPagerAdapter(FragmentManager fm,ArrayList<RestDishCategory> dishCatagories) {
        super(fm);

        this.dishCatagories = dishCatagories;
    }

    @Override
    public int getCount() {
        return dishCatagories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dishCatagories.get(position).toString();
    }

    @Override
    public Fragment getItem(int position) {
        return DishTabFragment.newInstance((position + 1), dishCatagories.get(position).toString());
    }
}
