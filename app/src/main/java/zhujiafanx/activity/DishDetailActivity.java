package zhujiafanx.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.activity.cart.CartModule;
import zhujiafanx.activity.cart.CartPresenter;
import zhujiafanx.activity.cart.CartView;
import zhujiafanx.adapter.ImageItemAdapter;
import zhujiafanx.common.BaseActivity;
import zhujiafanx.common.ErrorMessage;
import zhujiafanx.demo.R;
import zhujiafanx.fragment.DishTabFragment;
import zhujiafanx.rest.RestDishItem;

public class DishDetailActivity extends BaseActivity implements CartView{

    private RestDishItem dishItem;

    @Inject
    CartPresenter cartPresenter;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        ButterKnife.inject(this);

        dishItem = (RestDishItem) getIntent().getSerializableExtra(DishTabFragment.dishItemNameConstant);
        tv_publisher.setText(dishItem.From);

        if (dishItem.PublishedDate != null) {
            String date;

            SimpleDateFormat format = new SimpleDateFormat("yy-mm-dd hh:mm:ss");
            date = format.format(dishItem.PublishedDate);

            tv_publishTime.setText(date);
        }
        tv_title.setText(dishItem.Title);

        tv_address.setText(dishItem.Location.Address);

        ImageItemAdapter adapter = new ImageItemAdapter(this, dishItem.ImageList,new AbsListView.LayoutParams(400,400));
        lv_imageList.setAdapter(adapter);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CartModule(this));
    }

    @Override
    public void CheckoutFail(ErrorMessage error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void UpdateQuantitySuccess(UUID productId, int quantity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void UpdateQuantityFail(ErrorMessage error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void RemoveItemSuccess(UUID productId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void RemoveItemFail(ErrorMessage error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void CheckoutSuccess() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void ClearCartSuccess() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void ClearCartFail(ErrorMessage error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void AddItemFail(ErrorMessage error) {
        Toast.makeText(this,getString(R.string.add_to_cart_fail_tip),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddItemSuccess(UUID productId) {
        Toast.makeText(this,getString(R.string.add_to_cart_success_tip),Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_addToCart)
    public void onAddToCartClick(View v)
    {
        cartPresenter.AddItem(dishItem.Id);
    }
}
