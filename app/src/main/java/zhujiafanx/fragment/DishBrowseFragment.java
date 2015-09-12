package zhujiafanx.fragment;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.activity.DishDetailActivity;
import zhujiafanx.adapter.DishItemListAdapter;
import zhujiafanx.common.BaseFragment;
import zhujiafanx.demo.R;
import zhujiafanx.dish.DishBrowseModule;
import zhujiafanx.dish.IDishBrowsePresenter;
import zhujiafanx.dish.IDishBrowseView;
import zhujiafanx.rest.RestDishItem;

/**
 * Created by Administrator on 2015/8/21.
 */
public class DishBrowseFragment extends BaseFragment implements IDishBrowseView, DishItemListAdapter.IItemRowSelectLisenter {

    private static final int pageSize = 4;
    public final static String dishItemNameConstant = "dishItem";

    @InjectView(R.id.rv_dishList)
    RecyclerView dishListView;

    @InjectView(R.id.btn_refresh)
    FloatingActionButton btn_refresh;

    @InjectView(R.id.cdl_tip)
    CoordinatorLayout cdl_tip;

    private DishItemListAdapter dishItemListAdapter;

    @Inject
    IDishBrowsePresenter dishBrowsePresenter;

    ObjectAnimator refreshAnimator;

    private ArrayList<RestDishItem> dishItemList;

    public DishBrowseFragment() {

    }

    public static DishBrowseFragment newInstance() {
        return new DishBrowseFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dish_browse, container, false);
        ButterKnife.inject(this, view);

        InitRefreshAnimator();
        InitDishListView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("test", "onActivityCreated");

        dishBrowsePresenter.LoadingMoreNewerItems(0, pageSize);
    }

    private void InitDishListView() {
        dishListView.setHasFixedSize(true);
        //dishListView.setOnClickListener();对这个方法的设置无效


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        dishListView.setLayoutManager(layoutManager);

        dishItemList = new ArrayList<>();
        dishItemListAdapter = new DishItemListAdapter(this, dishItemList);
        dishListView.setAdapter(dishItemListAdapter);

        dishListView.setOnScrollListener(new RecyclerViewScrollChangeListener());
    }

    private void InitRefreshAnimator() {
        refreshAnimator = ObjectAnimator.ofFloat(btn_refresh, "rotation", 0f, 360f);
        refreshAnimator.setInterpolator(new LinearInterpolator());
        refreshAnimator.setRepeatCount(-1);
        refreshAnimator.setDuration(1000);
    }


    @OnClick(R.id.btn_refresh)
    public void onRefreshClick(View v) {
        dishBrowsePresenter.LoadingMoreNewerItems(0, pageSize);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DishBrowseModule(this));
    }

    @Override
    public void StopRotateButton() {
        if (refreshAnimator.isRunning()) {
            refreshAnimator.end();
        }

        btn_refresh.setEnabled(true);
    }

    @Override
    public void ShowNetworkError() {
        Log.i("test", "show network error1");
//        if (getView() != null) {
//            Log.i("test", "show network error2");
//            Snackbar.make(getView(), R.string.network_error_tip, Snackbar.LENGTH_SHORT).show();
//        }
        //Snackbar.make(getActivity(),R.string.network_error_tip,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void StartRotateButton() {
        StopRotateButton();

        dishListView.scrollToPosition(0);
        refreshAnimator.start();
        btn_refresh.setEnabled(false);
    }

    @Override
    public void HideLoadingHeader() {

        if (dishItemList.size() != 0 && dishItemList.get(0) == null) {
            dishItemList.remove(0);
            dishListView.getAdapter().notifyItemRemoved(0);
        }
    }

    @Override
    public void ShowLoadingHeader() {

        if (dishItemList.size() == 0 || dishItemList.get(0) != null) {
            dishItemList.add(0, null);
            dishListView.getAdapter().notifyItemInserted(0);
        }
    }

    @Override
    public void ShowLoadingFooter() {
        //是否应用先调用HideLoadingHeader,反之也一样？
        if ( dishItemList.size()==0 || dishItemList.get(dishItemList.size() - 1) != null) {
            dishItemList.add(null);
            dishListView.getAdapter().notifyItemInserted(dishItemList.size() - 1);
        }
    }

    @Override
    public void HideLoadingFooter() {
        if ( dishItemList.size()!=0 && dishItemList.get(dishItemList.size() - 1) == null) {
            dishItemList.remove(dishItemList.size() - 1);
            dishListView.getAdapter().notifyItemRemoved(dishItemList.size());
        }
    }

    @Override
    public void AddItemsToEnd(ArrayList<RestDishItem> items) {

        int lastPos = dishItemList.size() - 1;

        dishItemList.addAll(dishItemList.size() - 1, items);

        dishListView.getAdapter().notifyItemRangeChanged(lastPos, items.size());
    }

    @Override
    public void AddItemsToTop(ArrayList<RestDishItem> items) {

        Collections.reverse(items);

        dishItemList.addAll(0, items);

        dishListView.getAdapter().notifyItemRangeInserted(0, items.size());

        dishListView.smoothScrollToPosition(0);

        ShowNewLoadingItemsTip(items.size());
    }

    @Override
    public void onItemSelect(RestDishItem dishItem, int position) {
        Intent intent = new Intent(getActivity(), DishDetailActivity.class);
        intent.putExtra(dishItemNameConstant, dishItemList.get(position));
        startActivity(intent);
    }

    private void ShowNewLoadingItemsTip(int count) {

        if(count>0) {
            Snackbar.make(cdl_tip, String.format(getString(R.string.refresh_data_number), count), Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            Snackbar.make(cdl_tip, String.format(getString(R.string.no_more_items), count), Snackbar.LENGTH_SHORT).show();
        }
    }

    class RecyclerViewScrollChangeListener extends RecyclerView.OnScrollListener {
        private int previousTotal = 0;
        private boolean loading = true;
        private int visibleThreshold = 5;
        private int firstVisibleItem;
        private int visibleItemCount;
        private int totalItemCount;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

//            LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//            visibleItemCount = recyclerView.getChildCount();
//            totalItemCount = mLayoutManager.getItemCount();
//            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//
//            if (loading) {
//                if (totalItemCount > previousTotal) {
//                    loading = false;
//                    previousTotal = totalItemCount;
//                }
//            }
//            if (!loading && (totalItemCount - visibleItemCount)
//                    <= (firstVisibleItem + visibleThreshold)) {
//
//                int currentPage = totalItemCount / pageSize;
//                dishBrowsePresenter.LoadingMoreOlderItems(currentPage, pageSize);
//
//                loading = true;
//            }
        }
    }
}
