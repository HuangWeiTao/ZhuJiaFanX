package zhujiafanx.activity.cart;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.activity.DishDetailActivity;
import zhujiafanx.service.order.CartService;
import zhujiafanx.service.order.CartServiceMock;

/**
 * Created by Administrator on 2015/9/7.
 */
@Module(injects = {CartActivity.class, DishDetailActivity.class})
public class CartModule {

    private CartView cartView;

    public CartModule(CartView cartView) {
        this.cartView = cartView;
    }

    @Provides
    CartView provideCartView() {
        return this.cartView;
    }

    @Provides
    CartPresenter provideCartPresenter(CartView cartView, CartService cartService) {
        return new CartPresenterImpl(cartView, cartService);
    }

    @Provides
    CartService provideCartService() {
        return new CartServiceMock();
    }
}
