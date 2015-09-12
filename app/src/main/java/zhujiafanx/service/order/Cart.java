package zhujiafanx.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/6.
 */
public class Cart {
    private ArrayList<CartItem> cartItems;

    public Cart(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getItemsCount()
    {
        if(cartItems!=null && cartItems.size()!=0)
        {
            return cartItems.size();
        }
        else
        {
            return 0;
        }
    }

    public BigDecimal getTotal()
    {
        BigDecimal sum=new BigDecimal(0);

        if(cartItems!=null && cartItems.size()!=0)
        {
            for(CartItem item : cartItems)
            {
                sum=sum.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }

            return sum;
        }

        return sum;
    }

    public ArrayList<CartItem> getItems()
    {
        return cartItems;
    }
}
