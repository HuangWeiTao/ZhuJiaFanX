package zhujiafanx.control.stepControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/9/8.
 */
public class PhoneNumStepPart extends StepPart {

    @InjectView(R.id.et_phoneNum)
    EditText et_phoneNum;

    @InjectView(R.id.btn_send_verifyCode)
    Button btn_send_verifyCode;

    public PhoneNumStepPart(Context context, IStepNotify listener) {
        super(context, listener);
    }

    @Override
    public View GetContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.step_part_phone_num, null);
        ButterKnife.inject(this,view);

        return view;
    }

    @Override
    public String GetTitle() {
        return getContext().getString(R.string.enter_phone_num_header_tip);
    }

    @Override
    public int GetIconRes() {
        return 0;
    }


    @OnClick(R.id.btn_send_verifyCode)
    public void onSendVerifyCodeClick(View v) {
        //自定义逻辑

        listener.onNext(null);
    }
}
