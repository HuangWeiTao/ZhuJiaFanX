package zhujiafanx.control.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import zhujiafanx.app.App;
import zhujiafanx.demo.R;
import zhujiafanx.helper.BaseUIListener;
import zhujiafanx.utils.Util;

public class LoginRegisterActivity extends AppCompatActivity implements LoginFragment.LoginNofityParentListener {

    private Button btntest;

    private Tencent tencent;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("xxxx", "activity onCreate " + hashCode());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        btntest=(Button)findViewById(R.id.btn_test);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tencent = Tencent.createInstance(App.getAppIdOfQQ(), LoginRegisterActivity.this);

                if (!tencent.isSessionValid())
                {
                    Log.d("xxxx","login now.");
                    tencent.login(LoginRegisterActivity.this, "all", loginListener);
                }
            }
        });

        SwitchToFragment(LoginFragment.TAG);
    }

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                tencent.setAccessToken(token, expires);
                tencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("xxxx", "no fuck now:" + SystemClock.elapsedRealtime()+" "+values.toString());

            initOpenidAndToken(values);
            getUserInfo();
        }
    };

    public void getUserInfo()
    {
        if(ready(this))
        {
            userInfo = new UserInfo(this, tencent.getQQToken());
            userInfo.getUserInfo(new BaseUIListener(this,"get_simple_userinfo"));
        }
    }

    public boolean ready(Context context) {
        if (tencent == null) {
            return false;
        }
        boolean ready = tencent.isSessionValid()
                && tencent.getQQToken().getOpenId() != null;
        if (!ready) {
            Toast.makeText(context, "login and get openId first, please!",
                    Toast.LENGTH_SHORT).show();
        }
        return ready;
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {

                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {

                return;
            }

            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {
            Log.i("xxxx", "fuck 腾讯");
        }

        @Override
        public void onError(UiError e) {

            Log.i("xxxx", "ri 腾讯");
        }

        @Override
        public void onCancel() {
            Log.i("xxxx", "ko 腾讯");
        }
    }

    @Override
    protected void onStart() {
        Log.d("test","activity onStart "+hashCode());
        super.onStart();
    }

    @Override
    public void SwitchToRegister() {
        SwitchToFragment(RegisterFragment.TAG);
    }

    @Override
    public void NofityLoginCancel() {

    }

    @Override
    public void NotifyLoginSuccess() {
        Toast.makeText(this,"XXXXXXX",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NofityLoginFail() {

    }

    private void SwitchToFragment(String fragmentTAG) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTAG);

        if (fragment == null) {

            switch (fragmentTAG) {
                case LoginFragment.TAG:
                    fragment = LoginFragment.newInstance();
                    break;

                case RegisterFragment.TAG:
                    fragment = RegisterFragment.newInstance();
                    break;

                default:
                    break;
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_login_register_container, fragment, fragmentTAG);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("xxxx", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.RESULT_LOGIN) {
                Tencent.handleResultData(data, loginListener);
                Log.d("xxxx", "-->onActivityResult handle logindata");
            }
        } else if (requestCode == Constants.REQUEST_APPBAR) { //app内应用吧登录
            if (resultCode == Constants.RESULT_LOGIN) {
                //updateUserInfo();
                //updateLoginButton();
                Util.showResultDialog(this, data.getStringExtra(Constants.LOGIN_INFO), "登录成功");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("xxxx", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
//        tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
//
////            if(resultCode == Constants.RESULT_LOGIN) {
////                Tencent.handleResultData(data, loginListener);
//                Log.d("xxxx", "-->onActivityResult handle logindata");
////            }
//
//        //super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onDestroy() {
        Log.d("test","activity onDestroy "+hashCode());
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("test","activity onStop "+hashCode());
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d("test","activity onPause "+hashCode());
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("test","activity onResume "+hashCode());
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        Log.d("test","activity onResumeFragments "+hashCode());
        super.onResumeFragments();
    }
}
