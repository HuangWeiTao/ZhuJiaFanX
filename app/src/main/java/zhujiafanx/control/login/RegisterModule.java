package zhujiafanx.control.login;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.service.AccountModule;
import zhujiafanx.service.AccountService;

/**
 * Created by Administrator on 2015/8/13.
 */
@Module(includes = {AccountModule.class},library = true, injects = {RegisterFragment.class})
public class RegisterModule {

    private RegisterView registerView;

    public RegisterModule(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Provides
    RegisterView provideRegisterView()
    {
        return registerView;
    }

    @Provides
    RegisterPresenter provideRegisterPresenter(RegisterView registerView, AccountService accountService) {
        return new RegisterPresenterImpl(registerView, accountService);
    }
}
