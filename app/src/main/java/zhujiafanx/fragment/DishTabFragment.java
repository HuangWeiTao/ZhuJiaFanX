package zhujiafanx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import zhujiafanx.adapter.DishItemAdapter;
import zhujiafanx.demo.R;
import zhujiafanx.model.DishClient;
import zhujiafanx.model.DishItem;
import zhujiafanx.model.contract.IDishOperation;

public class DishTabFragment extends Fragment {

    private final static String titleConstant = "title";
    private final static String pageConstant = "page";
    private final  static int defaultLoadingItemsCountConstant=20;

    private String title;

    private int page;

    private DishItem[] dishItemList;

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

        page = getArguments().getInt(pageConstant);
        title = getArguments().getString(titleConstant);

        //load data as soon as possible
        ArrayList<DishItem> arrayList = LoadingDishItems();
        dishItemList = new DishItem[arrayList.size()];
        arrayList.toArray(dishItemList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dish_tab, container, false);

        //data bind
        ListView dishItemListView=(ListView) view.findViewById(R.id.lv_dishList_container);

        DishItemAdapter adapter = new DishItemAdapter(getActivity(),R.layout.dish_list_item,dishItemList);

        dishItemListView.setAdapter(adapter);

        return view;
    }

    private ArrayList<DishItem> LoadingDishItems()
    {
        IDishOperation dishClient = new DishClient();
        return dishClient.GetDishItems(page,defaultLoadingItemsCountConstant);
    }
}
