package zhujiafanx.app;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.activity.LoginActivity;
import zhujiafanx.activity.RegisterActivity;
import zhujiafanx.fragment.DishFragment;
import zhujiafanx.fragment.DishTabFragment;
import zhujiafanx.model.RESTDishClient;
import zhujiafanx.model.RestUserClient;
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.rest.IDishClient;

/**
 * Created by Administrator on 2015/6/5.
 */
@Module(library = true,
        injects = { RegisterActivity.class, LoginActivity.class, DishTabFragment.class, DishFragment.class},
        complete = false
        )
public class DefaultModule {

    private App app;

    public DefaultModule(App app) {
        this.app = app;
    }

    @Provides
    public IUserClient provideUserClient()
    {
        return new RestUserClient();
    }

//    @Provides
//    public IDishClient provideDishClient() {return new MockDishClient(this.app.getResources());}

    @Provides
    public IDishClient provideDishClient()
    {
        return new RESTDishClient();
    }

}
