package zhujiafanx.dish;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.fragment.DishBrowseFragment;
import zhujiafanx.rest.IDishClient;

/**
 * Created by Administrator on 2015/8/31.
 */
@Module(library = true,injects = {DishBrowseFragment.class}, complete = false)//为什么要加complete=false
public class DishBrowseModule {

    private IDishBrowseView browseView;

    public DishBrowseModule(IDishBrowseView browseView) {
        this.browseView = browseView;
    }

    @Provides
    public IDishBrowseView provideDishBrowseView() {
        return this.browseView;
    }

    @Provides
    public IDishBrowsePresenter provideDishBrowsePresenter(IDishBrowseView browseView, IDishClient dishClient) {
        return new DishBrowsePresenter(browseView, dishClient);
    }
}
