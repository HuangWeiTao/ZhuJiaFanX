package zhujiafanx.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.fragment.LoginFragment.OnLoginListener;
import zhujiafanx.fragment.LogoutFragment;
import zhujiafanx.helper.SessionManager;
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.model.contract.LoginInfo;
import zhujiafanx.model.contract.LoginResult;

//为什么直接继承activity时会出错，因为fragment的版不同(support.v4.app.fragment与fragment)?
public class LoginActivity extends FragmentActivity implements OnLoginListener,LogoutFragment.OnLogoutListener {

    @Inject
    IUserClient userClient;

    SessionManager sessionManager;

    Fragment loginFragment;

    Fragment logoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Injector.INSTANCE.inject(this);
        ButterKnife.inject(this);

        sessionManager = new SessionManager(getBaseContext());

        FragmentManager fragmentManager=getSupportFragmentManager();
        loginFragment = fragmentManager.findFragmentById(R.id.fr_loginFragment);
        logoutFragment=fragmentManager.findFragmentById(R.id.fr_logoutFragment);

        new LoadLocalUserInfoTask().execute();
    }

    @Override
    public void OnLoginClick(LoginInfo login) {

        new LoginTask().execute(login);
    }

    @Override
    public void OnForgetPasswordClick(LoginInfo login) {

    }

    @Override
    public void OnRegisterClick() {
        Intent intent = new Intent(this,RegisterActivity.class);

        startActivity(intent);
    }

    @Override
    public void OnLoginout(LoginResult result) {
        SessionManager manager=new SessionManager(getBaseContext());
        manager.setLogin(result);

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().show(loginFragment).hide(logoutFragment).commit();
    }

    class LoginTask extends AsyncTask<LoginInfo, Integer, LoginResult>
    {
        private LoginResult loginResult;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();

        }

        @Override
        protected LoginResult doInBackground(LoginInfo... params) {
            loginResult = userClient.Login(params[0]);

            SessionManager sessionManager = new SessionManager(getBaseContext());
            sessionManager.setLogin(loginResult);

//            try {
//                Thread.sleep(1000);
//            }
//            catch (Exception e)
//            {
//
//            }

            return loginResult;
        }

        @Override
        protected void onPostExecute(LoginResult loginResult) {
            if (!loginResult.Success) {
                Toast.makeText(getBaseContext(), "用户名或密码错误!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "登录成功!", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().show(logoutFragment).hide(loginFragment).commit();
            }
        }
    }

    class LoadLocalUserInfoTask extends AsyncTask<Void,Void,Void>
    {
        private LoginResult result=new LoginResult();

        @Override
        protected Void doInBackground(Void... params) {

            if(sessionManager.isLoggedIn())
            {
                result.Success=true;
                result.UserName=sessionManager.getUserName();
            }
            else
            {
                result.Success=false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            FragmentManager fragmentManager=getSupportFragmentManager();

            if(result.Success)
            {
                fragmentManager.beginTransaction().hide(loginFragment).show(logoutFragment).commit();
                ((LogoutFragment)logoutFragment).SetUserInfo(result.UserName);
            }
            else
            {
                fragmentManager.beginTransaction().hide(logoutFragment).show(loginFragment).commit();
            }
        }
    }
}