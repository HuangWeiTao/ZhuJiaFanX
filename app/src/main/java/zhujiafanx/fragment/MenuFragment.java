package zhujiafanx.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pingplusplus.android.PaymentActivity;

/**
 * Created by Administrator on 2015/5/27.
 */
public class MenuFragment extends Fragment {

    private final int REQUEST_CODE_PAYMENT=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button button=new Button(getActivity().getBaseContext());
        button.setText("启动推送");

        //PushManager.getInstance().initialize(getActivity().getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          Intent intent = new Intent();
                                          String packageName = getActivity().getPackageName();
                                          ComponentName componentName = new ComponentName(packageName, "zhujiafanx.demo" + ".wxapi.WXPayEntryActivity");
                                          intent.setComponent(componentName);

                                          intent.putExtra(PaymentActivity.EXTRA_CHARGE, Integer.toString(250));
                                          startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                                      }
                                  }
        );

        return button;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             */
                Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();

                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                //showMsg(result, errorMsg, extraMsg);
            }
        }
    }
}
