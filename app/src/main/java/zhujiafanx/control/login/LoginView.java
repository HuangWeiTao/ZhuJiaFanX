package zhujiafanx.control.login;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void switchToRegister();

    void onLoginError(String error);

    void onLoginSuccess();
}
