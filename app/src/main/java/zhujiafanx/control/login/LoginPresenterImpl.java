package zhujiafanx.control.login;

import android.os.AsyncTask;
import android.util.Log;

import zhujiafanx.service.AccountService;

/**
 * Created by Administrator on 2015/8/10.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private AccountService userService;

    private LoginAsyncTask loginTask;

    public LoginPresenterImpl(LoginView loginView, AccountService userService) {
        Log.d("test", "constuct loginView " + loginView.hashCode());
        Log.d("test", "userService "+userService.hashCode());
        this.loginView = loginView;
        this.userService = userService;
    }


    public void Login(AccountService.LoginRequest request) {

        loginView.showProgress();

        loginTask = new LoginAsyncTask();

        loginTask.execute(request);
    }

    @Override
    public void NavigateToRegister() {

        this.loginView.switchToRegister();
    }

    @Override
    public void NavigateToForgetPassword() {

    }

    private class LoginAsyncTask extends AsyncTask<AccountService.LoginRequest,Void,AccountService.LoginResponse>
    {
        @Override
        protected AccountService.LoginResponse doInBackground(AccountService.LoginRequest... params) {
            return userService.Login(params[0]);
        }

        @Override
        protected void onPostExecute(AccountService.LoginResponse loginResponse) {

            Log.d("test", "onPostExecute loginView " + loginView.hashCode());

            loginView.hideProgress();

            if(loginResponse.getResult())
            {
                loginView.onLoginSuccess();
            }
            else
            {
                loginView.onLoginError(loginResponse.getErrorMessage());
            }
        }
    }
}
