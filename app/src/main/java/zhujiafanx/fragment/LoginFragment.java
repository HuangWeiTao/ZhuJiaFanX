package zhujiafanx.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.demo.R;
import zhujiafanx.model.contract.LoginInfo;


public class LoginFragment extends Fragment {

    @Optional
    @InjectView(R.id.et_username)
    EditText et_username;

    @Optional
    @InjectView(R.id.et_password)
    EditText et_password;

    @Optional
    @InjectView(R.id.btn_login)
    Button btn_login;

    @Optional
    @InjectView(R.id.tv_forget_password)
    TextView tv_forget_password;

    @Optional
    @InjectView(R.id.tv_register)
    TextView tv_register;

    private OnLoginListener loginListener;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnLoginListener) {
            loginListener = (OnLoginListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement " + loginListener.getClass().getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Optional
    @OnClick(R.id.tv_forget_password)
    public void onForgetPasswordButtonClick(View v)
    {
        loginListener.OnForgetPasswordClick(null);
    }

    @Optional
    @OnClick(R.id.tv_register)
    public void onRegisterButtonClick(View v)
    {
        loginListener.OnRegisterClick();
    }

    @Optional
    @OnClick(R.id.btn_login)
    public void onLoginButtonClick(View v) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        {
            LoginInfo loginInfo = new LoginInfo(username.trim(), password.trim());
            loginListener.OnLoginClick(loginInfo);
        }
    }

    public interface OnLoginListener
    {
        public void OnLoginClick(LoginInfo login);

        public void OnForgetPasswordClick(LoginInfo login);

        public void OnRegisterClick();
    }
}




