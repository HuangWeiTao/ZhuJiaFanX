package zhujiafanx.control.login;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import zhujiafanx.control.stepControl.PasswordStepPart;
import zhujiafanx.control.stepControl.PhoneNumStepPart;
import zhujiafanx.control.stepControl.StepContainer;
import zhujiafanx.control.stepControl.VerifyCodeStepPart;
import zhujiafanx.demo.R;

public class PhoneRegisterFragment extends Fragment {

    private StepContainer registerContainer;

    public static PhoneRegisterFragment newInstance() {

        PhoneRegisterFragment fragment = new PhoneRegisterFragment();

        return fragment;
    }

    public PhoneRegisterFragment() {
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity().getBaseContext();

        registerContainer = new StepContainer(context);
        registerContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        registerContainer.SetSelectedHeaderItem(getResources().getColor(R.color.ColorPrimary));
        registerContainer.SetUnSelectHeaderItem(getResources().getColor(android.R.color.darker_gray));

        registerContainer.Add(new PhoneNumStepPart(context, registerContainer));
        registerContainer.Add(new VerifyCodeStepPart(context, registerContainer));
        registerContainer.Add(new PasswordStepPart(context, registerContainer));

        registerContainer.Build();

        return registerContainer;
    }

    @Override
    public void onResume() {
        super.onResume();

        registerContainer.onNext(null);
    }
}
