package zhujiafanx.activity.cart;

import java.util.UUID;

/**
 * Created by Administrator on 2015/9/7.
 */
public interface CartPresenter {
    boolean AddItem(UUID productId);
    boolean RemoveItem(UUID prdouctId);
    boolean UpdateItem(UUID productId, int quantity);
    boolean ClearCart();
    void Checkout();
}
