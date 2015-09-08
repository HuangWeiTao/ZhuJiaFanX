package zhujiafanx.control.stepControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/9/8.
 */
public class PasswordStepPart extends StepPart {

    @InjectView(R.id.et_password)
    EditText et_password;

    public PasswordStepPart(Context context, IStepNotify listener)
    {
        super(context, listener);
    }

    @Override
    public View GetContentView() {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.step_part_setting_password,null);
        ButterKnife.inject(this,view);

        return view;
    }


    @Override
    public String GetTitle() {
        return getContext().getString(R.string.setting_password_tip);
    }

    @Override
    public int GetIconRes() {
        return 0;
    }

    @OnClick(R.id.btn_password_complete)
    public void onPasswordSettingComplete(View v)
    {
        //自定义逻辑

        listener.onNext(null);
    }
}
