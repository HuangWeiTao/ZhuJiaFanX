package zhujiafanx.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.demo.R;
import zhujiafanx.helper.SessionManager;
import zhujiafanx.model.contract.LoginResult;

/**
 * Created by Administrator on 2015/7/20.
 */
public class LogoutFragment extends Fragment {

    @Optional
    @InjectView(R.id.btn_logout)
    Button btn_logout;

    @Optional
    @InjectView(R.id.tv_userName)
    TextView tv_userName;

    private OnLogoutListener logoutListener;

    public static LogoutFragment newInstance() {
        LogoutFragment fragment = new LogoutFragment();
        return fragment;
    }

    public LogoutFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnLogoutListener) {
            logoutListener = (OnLogoutListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement " + logoutListener.getClass().getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        ButterKnife.inject(this, view);

        SessionManager sessionManager=new SessionManager(getActivity());
        this.tv_userName.setText(sessionManager.getUserName());

        return view;
    }

    @Optional
    @OnClick(R.id.btn_logout)
    public void onLogoutButtonClick(View v)
    {
            //检验规则

        SessionManager manager=new SessionManager(getActivity());
        {
            LoginResult result = new LoginResult();
            result.Success=false;
            result.UserName=manager.getUserName();

            logoutListener.OnLoginout(result);
        }
    }

    public interface OnLogoutListener
    {
        public void OnLoginout(LoginResult result);
    }

    public void SetUserInfo(String userName)
    {
        tv_userName.setText(userName);
    }
}
