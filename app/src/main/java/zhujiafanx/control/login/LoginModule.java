package zhujiafanx.control.login;

import dagger.Module;
import dagger.Provides;
import zhujiafanx.service.AccountModule;
import zhujiafanx.service.AccountService;

/**
 * Created by Administrator on 2015/8/11.
 */
@Module(includes = {AccountModule.class},library = true,injects = {LoginFragment.class},complete = false
/*addsTo = DefaultModule.class//这个的用途是？ */
)
public class LoginModule {

    LoginView loginView;


    public LoginModule(LoginView loginView) {
        this.loginView = loginView;
    }


    @Provides
    public LoginView provideLoginView() {
        return this.loginView;
    }

    @Provides
    public LoginPresenter provideLoginPresenter(LoginView loginView, AccountService accountService) {
        return new LoginPresenterImpl(loginView, accountService);
    }
}
