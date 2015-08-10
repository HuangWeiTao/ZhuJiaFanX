package zhujiafanx.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zhujiafanx.adapter.ImageItemAdapter;
import zhujiafanx.demo.R;
import zhujiafanx.fragment.DishTabFragment;
import zhujiafanx.rest.RestDishItem;

public class DishDetailActivity extends ActionBarActivity {

    private RestDishItem dishItem;

    @InjectView(R.id.tv_detail_publisher)
    TextView tv_publisher;

    @InjectView(R.id.tv_detail_publishTime)
    TextView tv_publishTime;

    @InjectView(R.id.tv_detail_title)
    TextView tv_title;

    @InjectView(R.id.lv_detail_images)
    ListView lv_imageList;

    @InjectView(R.id.tv_address)
    TextView tv_address;

    @InjectView(R.id.btn_addToCart)
    Button btn_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        ButterKnife.inject(this);

        dishItem = (RestDishItem)getIntent().getSerializableExtra(DishTabFragment.dishItemNameConstant);
        tv_publisher.setText(dishItem.From);

    if(dishItem.PublishedDate!=null) {
        String date;

        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        date=format.format(dishItem.PublishedDate);

        tv_publishTime.setText(date);
    }
        tv_title.setText(dishItem.Title);

        tv_address.setText(dishItem.Location.Address);

        ImageItemAdapter adapter = new ImageItemAdapter(this, dishItem.ImageList);
        lv_imageList.setAdapter(adapter);
    }
}
