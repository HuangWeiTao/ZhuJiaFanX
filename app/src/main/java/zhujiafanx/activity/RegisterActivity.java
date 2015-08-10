package zhujiafanx.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.model.contract.IOnRegisterCallback;
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.model.contract.RegisterInfo;
import zhujiafanx.model.contract.RegisterResult;

public class RegisterActivity extends Activity implements IOnRegisterCallback {

    @InjectView(R.id.et_username)
    EditText et_username;

    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @InjectView(R.id.btn_register)
    Button btn_register;

    @Inject
    IUserClient userClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.inject(this);

        Injector.INSTANCE.inject(this);
    }

    @OnClick(R.id.btn_register)
    public void OnRegisterButtonClick(View view) {

        String email = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        RegisterInfo registerInfo = new RegisterInfo(email, password, password);

        if (registerInfo.IsValid()) {
            new RegisterTask(this).execute(registerInfo);
        } else {
            Toast.makeText(getBaseContext(), registerInfo.GetErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnFinishRegister(RegisterResult result) {
        if (result.Success) {
            Toast.makeText(getBaseContext(), "注册成功！", Toast.LENGTH_SHORT).show();
        } else {
            String message = "注册出错：" + result.ErrorMessage;
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    class RegisterTask extends AsyncTask<RegisterInfo, Integer, Void> {

        private IOnRegisterCallback callback;

        public RegisterTask(IOnRegisterCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(RegisterInfo... params) {
            userClient.Register(params[0], callback);

            return null;
        }
    }
}
