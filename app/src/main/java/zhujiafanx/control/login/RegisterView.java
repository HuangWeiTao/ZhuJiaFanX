package zhujiafanx.control.login;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface RegisterView {

    void showProgress();

    void hideProgress();

    void onRegisterError(String error);

    void onRegisterSuccess();
}
