package zhujiafanx.activity.cart;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.common.BaseActivity;
import zhujiafanx.common.ErrorMessage;
import zhujiafanx.demo.R;
import zhujiafanx.service.order.Cart;
import zhujiafanx.service.order.CartItem;
import zhujiafanx.service.order.CartServiceMock;

public class CartActivity extends BaseActivity implements CartView {

    @InjectView(R.id.cartItem_listView)
    ListView cartItem_listView;

    @Optional
    @InjectView(R.id.cart_totalAll)
    TextView cart_totalAll;

    @Inject
    CartPresenter cartPresenter;

    private ArrayList<CartItem> cartItems;
    private CartItemAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.inject(this);

        InitCartListView();

        //再次注入新加入的子视图(header和footer)
        ButterKnife.inject(this);

        new CartLoadingAsyncTask().execute();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CartModule(this));
    }

    @Override
    public void CheckoutFail(ErrorMessage error) {
        Toast.makeText(this, "下单失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void UpdateQuantitySuccess(UUID productId, int quantity) {
        CartItem updatedItem=FindCartItem(productId);

        updatedItem.setQuantity(quantity);
        BigDecimal newTotal=updatedItem.getCart().getTotal();

       ((CartItemAdapter) cartItem_listView.getAdapter()).notifyDataSetChanged();

        //更新总价
        cart_totalAll.setText(newTotal.toString());
    }

    @Override
    public void UpdateQuantityFail(ErrorMessage error) {
        Toast.makeText(this, "下单失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RemoveItemSuccess(UUID productId) {
        //找出要删除的cartItem
        CartItem removedItem=FindCartItem(productId);

        cartItems.remove(removedItem);
        BigDecimal newTotal=new BigDecimal(cart_totalAll.getText().toString()).subtract(removedItem.getSubTotal());
        cartAdapter.notifyDataSetChanged();

        //更新总价
        cart_totalAll.setText(newTotal.toString());
    }

    @Override
    public void RemoveItemFail(ErrorMessage error) {
        Toast.makeText(this, "删除失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CheckoutSuccess() {
        Toast.makeText(this, "下单成功!", Toast.LENGTH_SHORT).show();
        RemoveAllCartItem();
    }

    @Override
    public void ClearCartSuccess() {
        Toast.makeText(this, "清空购物车成功!", Toast.LENGTH_SHORT).show();
        RemoveAllCartItem();
    }

    @Override
    public void ClearCartFail(ErrorMessage error) {
        Toast.makeText(this, "清空购物车失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddItemFail(ErrorMessage error) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void AddItemSuccess(UUID productId) {
        throw new UnsupportedOperationException();
    }

    private void InitCartListView()
    {
        View cartHeader = getLayoutInflater().inflate(R.layout.cart_header, null);
        cartHeader.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        View cartFooter = getLayoutInflater().inflate(R.layout.cart_footer, null);
        cartFooter.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        cartItem_listView.addHeaderView(cartHeader);
        cartItem_listView.addFooterView(cartFooter);
    }

    private void RemoveAllCartItem()
    {
        cartAdapter.clear();
        cartAdapter.notifyDataSetChanged();

        cart_totalAll.setText((new BigDecimal("0")).toString());
    }

    private CartItem FindCartItem(UUID productId) {
        CartItem result = null;

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);

            if (productId == item.getProductId()) {
                result = item;
                break;
            }
        }

        return result;
    }

    private void onDeleteClick(View v, CartItem cartItem, int position)
    {
        cartPresenter.RemoveItem(cartItem.getProductId());
    }

    @Optional
    @OnClick(R.id.cart_checkout)
    public void onCartCheckout(View v)
    {
        cartPresenter.Checkout();
    }

    public class CartLoadingAsyncTask extends AsyncTask<Void,Void,Cart>
    {
        @Override
        protected Cart doInBackground(Void... params) {
            return new CartServiceMock().GetCart();
        }

        @Override
        protected void onPostExecute(Cart cart) {

            cartItems=cart.getItems();

            cartAdapter = new CartItemAdapter(getBaseContext(),R.layout.cart_item,cartItems);
            cartItem_listView.setAdapter(cartAdapter);

            cart_totalAll.setText(cart.getTotal().toString());
        }
    }

    public class CartItemAdapter extends ArrayAdapter<CartItem>
    {
        private Context context;

        private int resource;

        private ArrayList<CartItem> dataset;

        public CartItemAdapter(Context context, int resource, ArrayList<CartItem> dataset) {
            super(context, resource, dataset);

            this.context=context;
            this.resource=resource;
            this.dataset=dataset;
        }


        @Override
        public int getCount() {
            return dataset.size();
        }

        @Override
        public CartItem getItem(int position) {
            return dataset.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CartItem item = dataset.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resource, null);

                ViewHolder holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.name.setText(item.getName());
            holder.price.setText(item.getPrice().toString());
            holder.quantity.setText(Integer.toString(item.getQuantity()));

            holder.delete.setTag(new CartItemRow(item,position));
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItemRow row=(CartItemRow)v.getTag();
                    onDeleteClick(v,row.getCartItem(),row.getPosition());
                }
            });

            return convertView;
        }

        public class ViewHolder
        {
            public ViewHolder(View v) {
                ButterKnife.inject(this, v);
            }

            @InjectView(R.id.ib_productDelete)
            ImageButton delete;

            @InjectView(R.id.tv_productName)
            TextView name;

            @InjectView(R.id.tv_productPrice)
            TextView price;

            @InjectView(R.id.et_productQuantity)
            EditText quantity;
        }
    }

    public class CartItemRow
    {
        private CartItem cartItem;
        private int position;

        public CartItemRow(CartItem cartItem, int position) {
            this.cartItem = cartItem;
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public CartItem getCartItem() {
            return cartItem;
        }
    }
}
