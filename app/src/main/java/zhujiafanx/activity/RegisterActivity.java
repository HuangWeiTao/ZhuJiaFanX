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
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.model.contract.RegisterInfo;

public class RegisterActivity extends Activity {

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

//        ObjectGraph graph = ObjectGraph.create(new DefaultModule());
//        graph.inject(this);

        //userClient = graph.get(IUserClient.class);
        Injector.INSTANCE.inject(this);
    }

    @OnClick(R.id.btn_register)
    public void OnRegisterButtonClick(View view) {
        //verify data
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        RegisterInfo registerInfo = new RegisterInfo(username, password);

        new DatabaseTask().execute(registerInfo);
    }

    class DatabaseTask extends AsyncTask<RegisterInfo,Integer,Void>
    {
        @Override
        protected Void doInBackground(RegisterInfo... params) {
            userClient.Create(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getBaseContext(), "注册成功!", Toast.LENGTH_SHORT).show();
        }
    }

}
