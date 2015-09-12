package zhujiafanx.control.stepControl;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2015/9/8.
 */
public abstract class StepPart extends View {

    protected IStepNotify listener;

    public StepPart(Context context,IStepNotify listener) {
        super(context);
        this.listener = listener;
    }

    public abstract String GetTitle();
    public abstract int GetIconRes();
    public abstract View GetContentView();
}
