package zhujiafanx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import zhujiafanx.fragment.DishBrowseFragment;

/**
 * Created by Administrator on 2015/8/21.
 */
public class PageContainerAdapter extends FragmentStatePagerAdapter {

    private int pageCount;

    public PageContainerAdapter(FragmentManager manager, int pageCount) {
        super(manager);
        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                DishBrowseFragment fragment=DishBrowseFragment.newInstance();
                Log.i("test","get item 0, tag "+fragment.getTag());
                return fragment;
                default:
                     fragment=DishBrowseFragment.newInstance();
                    return fragment;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("test", "instantiateItem");
        Fragment fragment =(Fragment) super.instantiateItem(container, position);

        return fragment;
    }

    @Override
    public float getPageWidth(int position) {
        //是否可以在这里重写tab的宽度，而不用通过自定义tab来达到固定tab长度的效果？
        return super.getPageWidth(position);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
