package zhujiafanx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import javax.inject.Inject;

import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.fragment.LoginFragment.OnLoginListener;
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.model.contract.LoginInfo;

//为什么直接继承activity时会出错，因为fragment的版不同(support.v4.app.fragment与fragment)?
public class LoginActivity extends FragmentActivity implements OnLoginListener {

    @Inject
    IUserClient userClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Injector.INSTANCE.inject(this);
    }

    @Override
    public void OnLoginClick(LoginInfo login) {
        String token = userClient.Login(login);

        if(token.isEmpty())
        {
            Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"登录成功,token: "+token,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnForgetPasswordClick(LoginInfo login) {

    }

    @Override
    public void OnRegisterClick() {
        Intent intent = new Intent(this,RegisterActivity.class);

        startActivity(intent);
    }
}