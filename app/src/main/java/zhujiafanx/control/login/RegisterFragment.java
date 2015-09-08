package zhujiafanx.control.login;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.service.AccountService;

public class RegisterFragment extends Fragment implements RegisterView {

    public static final String TAG="RegisterFragment_TAG";

    @Inject
    RegisterPresenter registerPresenter;

    @Optional
    @InjectView(R.id.et_username)
    EditText et_username;

    @Optional
    @InjectView(R.id.et_password)
    EditText et_password;

    @Optional
    @InjectView(R.id.et_confirm_password)
    EditText et_confirm_password;

    AlertDialog progressDialog;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        ButterKnife.inject(this, view);
        Injector.INSTANCE.init(new RegisterModule(this), this);

        return view;
    }

    @Optional
    @OnClick(R.id.btn_register)
    public void onRegisterClick(View v) {
        AccountService.RegisterRequest registerRequest = new AccountService.RegisterRequest(et_username.getText().toString(), et_password.getText().toString());
        registerPresenter.Register(registerRequest);
    }

    private void createProgressDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());


        ProgressBar progressBar=new ProgressBar(getActivity());
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.MULTIPLY);
        progressBar.setBackground(new ColorDrawable((Color.BLACK)));

        builder.setView(progressBar);


        this.progressDialog=builder.create();
        this.progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        //this.progressDialog.setCancelable(false);
        this.progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void showProgress() {

        if(progressDialog==null)
        {
            createProgressDialog();
        }

        progressDialog.show();
    }

    @Override
    public void hideProgress() {

        if(progressDialog!=null)
        {
            progressDialog.hide();
        }
    }

    @Override
    public void onRegisterError(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getActivity(),getString(R.string.register_succcess_text),Toast.LENGTH_LONG).show();
    }
}
