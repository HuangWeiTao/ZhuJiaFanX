package zhujiafanx.activity.cart;

import java.util.UUID;

import zhujiafanx.common.ErrorMessage;

/**
 * Created by Administrator on 2015/9/7.
 */
public interface CartView {
    void AddItemSuccess(UUID productId);
    void AddItemFail(ErrorMessage error);
    void UpdateQuantitySuccess(UUID productId, int quantity);
    void UpdateQuantityFail(ErrorMessage error);
    void RemoveItemSuccess(UUID productId);
    void RemoveItemFail(ErrorMessage error);
    void CheckoutSuccess();
    void CheckoutFail(ErrorMessage error);
    void ClearCartSuccess();
    void ClearCartFail(ErrorMessage error);
}
