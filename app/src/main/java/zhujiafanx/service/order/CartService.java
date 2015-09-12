package zhujiafanx.service.order;

import java.util.UUID;

/**
 * Created by Administrator on 2015/9/6.
 */
public interface CartService {
    Cart GetCart();
    boolean ClearCart();
    CartItem AddItem(UUID productId);
    boolean RemoveItem(UUID productId);
    CartItem UpdateItem(UUID productId, int count);
    boolean Checkout();
}
