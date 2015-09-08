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
public class VerifyCodeStepPart extends StepPart {

    @InjectView(R.id.et_verifyCode)
    EditText et_verifyCode;

    public VerifyCodeStepPart(Context context, IStepNotify listener)
    {
        super(context, listener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    @Override
    public View GetContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.step_part_verify_code, null);
        ButterKnife.inject(this,view);

        return view;
    }

    @Override
    public String GetTitle() {
        return getContext().getString(R.string.enter_verify_code_tip);
    }

    @Override
    public int GetIconRes() {
        return 0;
    }


    @OnClick(R.id.btn_confirm_verifyCode)
    public void onConfirmVerifyCodeClick(View v)
    {
        //自定义逻辑

        listener.onNext(null);
    }
}
