package zhujiafanx.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/7.
 */
public class CartServiceMock implements CartService {

    @Override
    public Cart GetCart() {
        int count=5;
        UUID ownerId=UUID.randomUUID();

        ArrayList<CartItem> items=new ArrayList<>(count);
        Cart cart=new Cart(items);

        for(int i=0;i<count;i++)
        {
            CartItem item =new CartItem(cart,UUID.randomUUID(),new Date(),"商品 "+i, ownerId,new BigDecimal(i+2),UUID.randomUUID(),i+1);
            items.add(item);
        }

        return new Cart(items);
    }

    @Override
    public boolean ClearCart() {
        return false;
    }

    @Override
    public CartItem UpdateItem(UUID productId, int count) {
        return null;
    }

    @Override
    public CartItem AddItem(UUID productId) {
        return null;
    }

    @Override
    public boolean RemoveItem(UUID productId) {
        return true;
    }

    @Override
    public boolean Checkout() {
        return true;
    }
}
