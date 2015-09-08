package zhujiafanx.activity.cart;

import java.util.UUID;

import zhujiafanx.common.ErrorMessage;
import zhujiafanx.service.order.CartService;

/**
 * Created by Administrator on 2015/9/7.
 */
public class CartPresenterImpl implements CartPresenter {

    private CartView cartView;

    private CartService cartService;

    public CartPresenterImpl(CartView cartView, CartService cartService) {
        this.cartView = cartView;
        this.cartService = cartService;
    }

    @Override
    public boolean AddItem(UUID productId) {

        boolean result = true;
        try {
            cartService.AddItem(productId);
            cartView.AddItemSuccess(productId);
        } catch (Exception e) {
            //模拟错误的信息
            ErrorMessage error = new ErrorMessage("", "");
            cartView.AddItemFail(error);

            result = false;
        }

        return result;
    }

    @Override
    public void Checkout() {
        //检查每一行中的可编辑字段的改动,然后再更新shopping cart字段和总计字段

        boolean result = cartService.Checkout();
        if(result)
        {
            cartView.CheckoutSuccess();
        }
        else
        {
            //模拟错误的信息
            ErrorMessage error=new ErrorMessage("","");
            cartView.CheckoutFail(error);
        }

    }

    @Override
    public boolean RemoveItem(UUID productId) {
        boolean result=cartService.RemoveItem(productId);
        if(result)
        {
            cartView.RemoveItemSuccess(productId);
        }
        else
        {
            //模拟错误的信息
            ErrorMessage error=new ErrorMessage("","");
            cartView.RemoveItemFail(error);
        }

        return result;
    }

    @Override
    public boolean UpdateItem(UUID productId, int quantity) {
        try {
            cartService.UpdateItem(productId, quantity);
            cartView.UpdateQuantitySuccess(productId,quantity);

            return true;
        }
        catch (Exception e)
        {
            //模拟错误的信息
            ErrorMessage error=new ErrorMessage("","");
            cartView.UpdateQuantityFail(error);

            return false;
        }
    }

    @Override
    public boolean ClearCart() {
        boolean result = cartService.ClearCart();
        if(result)
        {
            cartView.ClearCartSuccess();
        }
        else
        {
            //模拟错误的信息
            ErrorMessage error=new ErrorMessage("","");
            cartView.ClearCartFail(error);
        }

        return result;
    }
}
