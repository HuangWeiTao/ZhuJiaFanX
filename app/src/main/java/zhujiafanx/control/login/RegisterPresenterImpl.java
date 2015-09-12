package zhujiafanx.control.login;

import android.os.AsyncTask;

import zhujiafanx.service.AccountService;

/**
 * Created by Administrator on 2015/8/13.
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView registerView;
    private AccountService accountService;

    private RegisterAsyncTask registerAsyncTask;

    public RegisterPresenterImpl(RegisterView registerView, AccountService accountService) {
        this.registerView = registerView;
        this.accountService = accountService;
    }

    @Override
    public void Register(AccountService.RegisterRequest reqeust) {
        registerView.showProgress();

        registerAsyncTask = new RegisterAsyncTask();
        registerAsyncTask.execute(reqeust);
    }

    class RegisterAsyncTask extends AsyncTask<AccountService.RegisterRequest,Void,AccountService.RegisterResponse>
    {
        @Override
        protected AccountService.RegisterResponse doInBackground(AccountService.RegisterRequest... params) {
            return accountService.Register(params[0]);
        }

        @Override
        protected void onPostExecute(AccountService.RegisterResponse registerResponse) {

            registerView.hideProgress();

            if(registerResponse.getResult())
            {
                registerView.onRegisterSuccess();
            }
            else
            {
                registerView.onRegisterError(registerResponse.getErrorMessage());
            }
        }
    }
}
