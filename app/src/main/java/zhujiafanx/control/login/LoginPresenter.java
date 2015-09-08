package zhujiafanx.control.login;

import zhujiafanx.service.AccountService;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface LoginPresenter {

    public void Login(AccountService.LoginRequest request);

    public void NavigateToForgetPassword();

    public void NavigateToRegister();
}
