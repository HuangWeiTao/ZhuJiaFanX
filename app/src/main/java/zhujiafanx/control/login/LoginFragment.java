package zhujiafanx.control.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.app.App;
import zhujiafanx.common.BaseFragment;
import zhujiafanx.control.AlertDialogHelper;
import zhujiafanx.control.ClearableEditText;
import zhujiafanx.demo.R;
import zhujiafanx.service.AccountService;
import zhujiafanx.utils.Util;

public class LoginFragment extends BaseFragment implements LoginView {

    public static final String TAG = "LoginFragment_TAG";

    private static final String username_constant = "username_constant";
    private static final String password_constant = "password_constant";

    @Optional
    @InjectView(R.id.et_username)
    ClearableEditText et_username;

    @Optional
    @InjectView(R.id.et_password)
    ClearableEditText et_password;

    @Inject
    LoginPresenter loginPresenter;

    private AlertDialog progressDialog;

    private LoginNofityParentListener callbackParentListener;

    private Tencent tencent;

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d("test", "fragment onAttach "+hashCode()+" activity: "+activity.hashCode());
        super.onAttach(activity);

        if (activity instanceof LoginNofityParentListener) {
            callbackParentListener = (LoginNofityParentListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implements " + LoginNofityParentListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("test", "fragment onCreate "+hashCode());
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("test", "fragment onCreateView " + hashCode());
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        InitialControl();

        tencent = Tencent.createInstance(App.getAppIdOfQQ(), getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("test", "fragment onActivityCreated " + hashCode());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        RetriveEdittedValues(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        SaveEdittedValues(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.RESULT_LOGIN) {
                Tencent.handleResultData(data, loginListener);
                Log.d(TAG, "-->onActivityResult handle logindata");
            }
        } else if (requestCode == Constants.REQUEST_APPBAR) { //app内应用吧登录
            if (resultCode == Constants.RESULT_LOGIN) {
                //updateUserInfo();
                //updateLoginButton();
                Util.showResultDialog(getActivity(), data.getStringExtra(Constants.LOGIN_INFO), "登录成功");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Optional
    @OnClick(R.id.btn_login)
    public void onLoginButtonClick(View v) {
        AccountService.LoginRequest request = new AccountService.LoginRequest(et_username.getText().toString(), et_password.getText().toString());

        this.loginPresenter.Login(request);
    }

    @Optional
    @OnClick(R.id.tv_register)
    public void onRegisterClick(View v) {
        this.loginPresenter.NavigateToRegister();
    }

    @Optional
    @OnClick(R.id.btn_QQlogin)
    public void onQQloginButtonClick(View v)
    {
        if (!tencent.isSessionValid())
        {
            tencent.login(getActivity(), "all", loginListener);
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());

            Toast.makeText(getActivity(),values.toString(),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void showProgress() {

        progressDialog.show();
    }

    @Override
    public void hideProgress() {

        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    public void onLoginError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        callbackParentListener.NotifyLoginSuccess();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getActivity(), getString(R.string.login_success_text), Toast.LENGTH_SHORT).show();
        callbackParentListener.NotifyLoginSuccess();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new LoginModule(this));
    }

    @Override
    public void switchToRegister() {
        callbackParentListener.SwitchToRegister();
    }

    private void SaveEdittedValues(Bundle state) {
        state.putString(username_constant, et_username.getText().toString());
        state.putString(password_constant, et_password.getText().toString());
    }

    private void RetriveEdittedValues(Bundle state) {
        if (state != null) {
            et_username.setText(state.getString(username_constant, ""));
            et_password.setText(state.getString(password_constant, ""));
        }
    }

    private void InitialControl() {
        if (this.progressDialog == null) {
            this.progressDialog = AlertDialogHelper.CreateProgressDialog(getActivity());
        }
    }

    interface LoginNofityParentListener {

        public void SwitchToRegister();

        public void NotifyLoginSuccess();

        public void NofityLoginCancel();

        public void NofityLoginFail();
    }

    @Override
    public void onPause() {
        Log.d("test", "fragment onPause "+hashCode());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("test", "fragment onStop "+hashCode());

        progressDialog.dismiss();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("test", "fragment onDestroy "+hashCode());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("test", "fragment onDetach "+hashCode());
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.d("test", "fragment onDestroyView "+hashCode());
        super.onDestroyView();
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(getActivity().getApplicationContext(), "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(getActivity().getApplicationContext(), "返回为空", "登录失败");
                return;
            }
            Util.showResultDialog(getActivity().getApplicationContext(), response.toString(), "登录成功");
            // 有奖分享处理

            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {
            Log.i("xxxx", "fuck 腾讯");
        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(getActivity(), "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(getActivity(), "onCancel: ");
            Util.dismissDialog();
//            if (isServerSideLogin) {
//                isServerSideLogin = false;
//            }
        }
    }
}
