package zhujiafanx.service.order;

import java.util.UUID;

/**
 * Created by Administrator on 2015/9/6.
 */
public interface OrderService {
    void Pay(UUID orderId);
}
