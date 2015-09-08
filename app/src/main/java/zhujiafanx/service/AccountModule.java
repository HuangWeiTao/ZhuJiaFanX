package zhujiafanx.service;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.mock.MockUserService;

/**
 * Created by Administrator on 2015/8/11.
 */
@Module(library = true)
public class AccountModule {

    @Provides
    AccountService provideAccountService()
    {
        return new MockUserService();
    }
}
