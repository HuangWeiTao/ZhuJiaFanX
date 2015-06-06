package zhujiafanx.app;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.activity.LoginActivity;
import zhujiafanx.activity.RegisterActivity;
import zhujiafanx.model.SugarImplementation.SugarUserClient;
import zhujiafanx.model.contract.IUserClient;

/**
 * Created by Administrator on 2015/6/5.
 */
@Module(library = true,injects = { RegisterActivity.class, LoginActivity.class},complete = false)
public class DefaultModule {
    @Provides
    public IUserClient provideUserClient()
    {
        return new SugarUserClient();
    }
}
