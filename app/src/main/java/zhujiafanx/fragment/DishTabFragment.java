package zhujiafanx.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.activity.DishDetailActivity;
import zhujiafanx.adapter.DishItemAdapter;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.rest.IDishClient;
import zhujiafanx.rest.RestDishItem;

public class DishTabFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final static String titleConstant = "tabTitle";
    private final static String pageConstant = "tabPage";
    private final static int defaultLoadingItemsCountConstant = 3;
    public final static String dishItemNameConstant = "dishItem";

    private String tabTitle;
    private int tabPage;
    private int currentItemPage = 1;

    private ArrayList<RestDishItem> dishItemList;
    private DishItemAdapter adapter;

    @InjectView(R.id.srl_swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @InjectView(R.id.btn_refresh)
    FloatingActionButton btn_refresh;

    @Inject
    IDishClient dishClient;

    public static DishTabFragment newInstance(int page, String title) {
        DishTabFragment fragment = new DishTabFragment();

        Bundle args = new Bundle();
        args.putInt(pageConstant, page);
        args.putString(titleConstant, title);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.INSTANCE.inject(this);

        tabPage = getArguments().getInt(pageConstant);
        tabTitle = getArguments().getString(titleConstant);

        //load data as soon as possible
        dishItemList = LoadingDishItems();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dish_tab, container, false);
        ButterKnife.inject(this, view);

        //data bind
        ListView dishItemListView = (ListView) view.findViewById(R.id.lv_dishList_container);
        adapter = new DishItemAdapter(getActivity(), R.layout.dish_list_item, dishItemList);
        dishItemListView.setOnItemClickListener(this);
        dishItemListView.setAdapter(adapter);

        //setup swipe container
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    private ArrayList<RestDishItem> LoadingDishItems() {
        return dishClient.GetDishItems(currentItemPage, defaultLoadingItemsCountConstant);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DishDetailActivity.class);
        intent.putExtra(dishItemNameConstant, dishItemList.get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        FetchFollowingItems();
    }

    @OnClick(R.id.btn_refresh)
    public void onButtonRefresh(View view)
    {
        swipeContainer.scrollTo(0, 0);
        new LoadAsyncTask().execute();
    }

    private void FetchFollowingItems() {
        ArrayList<RestDishItem> newItems = dishClient.GetDishItems(++currentItemPage, defaultLoadingItemsCountConstant);

        try {
            Thread.sleep(1500);
        }
        catch(Exception ex)
        {

        }

        int index=0;
        for(RestDishItem item : newItems)
        {
            adapter.insert(item,index);
            index++;
        }


        swipeContainer.setRefreshing(false);
    }

    private ArrayList<RestDishItem> FetchNewerItems()
    {
        try {
            Thread.sleep(1500);
        }
        catch (Exception e)
        {

        }

        ArrayList<RestDishItem> newItems = dishClient.GetDishItems(1, defaultLoadingItemsCountConstant);

        return newItems;
    }


    class LoadAsyncTask extends AsyncTask<Void,Void,Void>
    {
        Animation animation;

        ArrayList<RestDishItem> newItems;

        boolean hasNewItems=false;
        int index=0;

        @Override
        protected void onPreExecute() {
            animation= AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_indefinitely);

            btn_refresh.startAnimation(animation);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
            }
            catch (Exception e)
            {

            }

            newItems = FetchNewerItems();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //当前最新item的id
            RestDishItem firstItem= adapter.getItem(0);
            if(firstItem!=null) {


                if(newItems!=null && newItems.size()!=0)
                {
                    if(firstItem.Id!=newItems.get(0).Id)
                    {

                        hasNewItems=true;

                        //将其余重复的项去掉

                        for (RestDishItem i:newItems) {
                            if(i.Id!=firstItem.Id)
                            {
                                adapter.insert(i,index++);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                }
            }

            if(!hasNewItems)
            {
                Toast.makeText(getActivity().getApplicationContext(), R.string.no_more_items, Toast.LENGTH_SHORT).show();
            }
            else
            {
                //MessageFormat.format("{0}"),String.format("%s")两种格式化字符串的区别
                Toast.makeText(getActivity().getApplicationContext(), String.format(getString(R.string.refresh_data_number), index),Toast.LENGTH_SHORT).show();
            }

            animation.cancel();
            animation.reset();
        }
    }
}
